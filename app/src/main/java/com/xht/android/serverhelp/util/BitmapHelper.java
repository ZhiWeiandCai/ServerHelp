/*
 * Copyright (C) 2015 Federico Iosue (federico.iosue@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.xht.android.serverhelp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Log;

import java.io.InputStream;

public class BitmapHelper {
	private static String TAG = "BitmapHelper";
	
	public static final String MIME_TYPE_IMAGE = "image/jpeg";
	public static final String MIME_TYPE_AUDIO = "audio/amr";
	public static final String MIME_TYPE_VIDEO = "video/mp4";
	public static final String MIME_TYPE_SKETCH = "image/png";
	public static final String MIME_TYPE_FILES = "file/*";

	public static final String MIME_TYPE_IMAGE_EXT = ".jpeg";
	public static final String MIME_TYPE_AUDIO_EXT = ".amr";
	public static final String MIME_TYPE_VIDEO_EXT = ".mp4";
	public static final String MIME_TYPE_SKETCH_EXT = ".png";
	public static final String MIME_TYPE_CONTACT_EXT = ".vcf";


    /**
     * Creates a thumbnail of requested size by doing a first sampled decoding of the bitmap to optimize memory
     */
	public static Bitmap getThumbnail(Context mContext, Uri uri, int reqWidth, int reqHeight) {
		Bitmap srcBmp = BitmapUtils.decodeSampledFromUri(mContext, uri, reqWidth, reqHeight);

		// If picture is smaller than required thumbnail
		Bitmap dstBmp;
		if (srcBmp.getWidth() < reqWidth && srcBmp.getHeight() < reqHeight) {
			dstBmp = ThumbnailUtils.extractThumbnail(srcBmp, reqWidth, reqHeight);

			// Otherwise the ratio between measures is calculated to fit requested thumbnail's one
		} else {
			// Cropping
			int x = 0, y = 0, width = srcBmp.getWidth(), height = srcBmp.getHeight();
			float ratio = ((float) reqWidth / (float) reqHeight) * ((float) srcBmp.getHeight() / (float) srcBmp
					.getWidth());
			if (ratio < 1) {
				x = (int) (srcBmp.getWidth() - srcBmp.getWidth() * ratio) / 2;
				width = (int) (srcBmp.getWidth() * ratio);
			} else {
				y = (int) (srcBmp.getHeight() - srcBmp.getHeight() / ratio) / 2;
				height = (int) (srcBmp.getHeight() / ratio);
			}
			dstBmp = Bitmap.createBitmap(srcBmp, x, y, width, height);
		}
		return dstBmp;
	}

    /**
     * Checks if a bitmap is null and returns a placeholder in its place
     */
    private static int dpToPx(Context mContext, int dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }


    public static Bitmap decodeSampledBitmapFromResourceMemOpt(InputStream inputStream, int reqWidth, int reqHeight) {

        byte[] byteArr = new byte[0];
        byte[] buffer = new byte[1024];
        int len;
        int count = 0;

        try {
            while ((len = inputStream.read(buffer)) > -1) {
                if (len != 0) {
                    if (count + len > byteArr.length) {
                        byte[] newbuf = new byte[(count + len) * 2];
                        System.arraycopy(byteArr, 0, newbuf, 0, count);
                        byteArr = newbuf;
                    }

                    System.arraycopy(buffer, 0, byteArr, count, len);
                    count += len;
                }
            }

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(byteArr, 0, count, options);

            options.inSampleSize = BitmapUtils.calculateInSampleSize(options, reqWidth, reqHeight);
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            return BitmapFactory.decodeByteArray(byteArr, 0, count, options);

        } catch (Exception e) {
            Log.d(TAG, "Explosion processing upgrade!", e);
            return null;
        }
    }

}
