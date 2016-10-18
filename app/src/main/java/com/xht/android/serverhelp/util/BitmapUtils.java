package com.xht.android.serverhelp.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import com.xht.android.serverhelp.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtils {

	public static Bitmap decodeSampledFromUri(Context mContext, Uri uri,
			int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		InputStream inputStream = null;
		InputStream inputStreamSampled = null;
		try {
			inputStream = mContext.getContentResolver().openInputStream(uri);
			BitmapFactory.decodeStream(inputStream, null, options);

			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);
			options.inJustDecodeBounds = false;

			inputStreamSampled = mContext.getContentResolver().openInputStream(
					uri);
			Bitmap localBitmap = BitmapFactory.decodeStream(inputStreamSampled,
					null, options);
			return localBitmap;
		} catch (IOException e) {
			Log.e("BitmapUtils", "Error");
			Bitmap bitmap = BitmapFactory.decodeResource(
					mContext.getResources(), R.mipmap.p_head_fail);
			return bitmap;
		} finally {
			try {
				try {
					inputStream.close();
					inputStreamSampled.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (NullPointerException e) {
				Log.e("BitmapUtils", "Failed to close streams");
			}
		}
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) throws FileNotFoundException {
		int height = options.outHeight;
		int width = options.outWidth;
		int inSampleSize = 1;
		if ((height > reqHeight) || (width > reqWidth)) {
			int halfHeight = height / 2;
			int halfWidth = width / 2;

			while ((halfHeight / inSampleSize > reqHeight)
					&& (halfWidth / inSampleSize > reqWidth)) {
				inSampleSize *= 2;
			}

			while ((halfHeight / inSampleSize > reqHeight * 2)
					|| (halfWidth / inSampleSize > reqWidth * 2)) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}

	public static Bitmap drawTextToBitmap(Context mContext, Bitmap bitmap,
			String text, Integer offsetX, Integer offsetY, float textSize,
			Integer textColor) {
		Resources resources = mContext.getResources();
		float scale = resources.getDisplayMetrics().density;

		Bitmap.Config bitmapConfig = bitmap.getConfig();

		if (bitmapConfig == null) {
			bitmapConfig = Bitmap.Config.RGB_565;
		}

		if (!bitmap.isMutable()) {
			bitmap = bitmap.copy(bitmapConfig, true);
		}
		Canvas canvas = new Canvas(bitmap);

		Paint paint = new Paint(1);

		paint.setColor(textColor.intValue());

		textSize = (int) (textSize * scale * bitmap.getWidth() / 100.0F);

		textSize = textSize < 15.0F ? textSize : 15.0F;
		paint.setTextSize(textSize);

		paint.setShadowLayer(1.0F, 0.0F, 1.0F, -1);

		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
		int x;
		if (offsetX == null) {
			x = (bitmap.getWidth() - bounds.width()) / 2;
		} else {
			if (offsetX.intValue() >= 0) {
				x = offsetX.intValue();
			} else {
				x = bitmap.getWidth() - bounds.width() - offsetX.intValue();
			}
		}
		int y;
		if (offsetY == null) {
			y = (bitmap.getHeight() - bounds.height()) / 2;
		} else {
			if (offsetY.intValue() >= 0) {
				y = offsetY.intValue();
			} else {
				y = bitmap.getHeight() - bounds.height() + offsetY.intValue();
			}
		}

		canvas.drawText(text, x, y, paint);

		return bitmap;
	}

	public static Uri getUri(Context mContext, int resource_id) {
		Uri uri = Uri.parse("android.resource://" + mContext.getPackageName()
				+ "/" + resource_id);
		return uri;
	}

	private static Bitmap scaleImage(Context mContext, Bitmap bitmap,
			int reqWidth, int reqHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int boundingX = dpToPx(mContext, reqWidth);
		int boundingY = dpToPx(mContext, reqHeight);

		float xScale = boundingX / width;
		float yScale = boundingY / height;
		float scale = xScale >= yScale ? xScale : yScale;

		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);

		Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);

		return scaledBitmap;
	}

	public static Bitmap rotateImage(Bitmap bitmap, String filePath) {
		Bitmap resultBitmap = bitmap;
		try {
			ExifInterface exifInterface = new ExifInterface(filePath);
			int orientation = exifInterface.getAttributeInt("Orientation", 1);

			Matrix matrix = new Matrix();

			if (orientation == 6)
				matrix.postRotate(6.0F);
			else if (orientation == 3)
				matrix.postRotate(3.0F);
			else if (orientation == 8) {
				matrix.postRotate(8.0F);
			}

			resultBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, true);
		} catch (Exception exception) {
			Log.d("AndroidTouchGallery", "Could not rotate the image");
		}
		return resultBitmap;
	}

	public static InputStream getBitmapInputStream(Bitmap bitmap) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
		byte[] bitmapdata = bos.toByteArray();
		ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
		return bs;
	}

	private static int dpToPx(Context mContext, int dp) {
		float density = mContext.getResources().getDisplayMetrics().density;
		return Math.round(dp * density);
	}
}
