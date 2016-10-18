package com.xht.android.serverhelp;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xht.android.serverhelp.model.Constants;
import com.xht.android.serverhelp.util.BitmapHelper;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JinDuFragment#newInstance} factory method to
 * create an instance of this fragment.
 * <br>
 * 办证中列表项的详细
 */
public class JinDuFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "JinDuFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private TextView mShmzTV1, mScwjTV1, mScjgTV1, mZpLookTV1,
        mShmzTV2, mScwjTV2, mScjgTV2, mZpLookTV2;
    private ImageView mWjZpIV11, mWjZpIV12, mWjZpIV13;
    private ChoosePicDialog mChoosePicDialog;
    private int curIVSelPic;    //为哪一个IV选择照片
    private Bitmap mTempBitmap; //将要上传的图片
    private boolean mShiFouJiangSCFlag; //是否必须要上传先
    private boolean mNotSelPic;  //不能选择图片吗？
    private int mWjNum11;   //文件照已经上传了多少张
    private Uri mCurFromCamare;

    public JinDuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JinDuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JinDuFragment newInstance(String param1, String param2) {
        JinDuFragment fragment = new JinDuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jin_du, container, false);
        mShmzTV1 = (TextView) view.findViewById(R.id.btmz1);
        mScwjTV1 = (TextView) view.findViewById(R.id.scwj_btn1);
        mScjgTV1 = (TextView) view.findViewById(R.id.scjg_btn1);
        mZpLookTV1 = (TextView) view.findViewById(R.id.zplook1);
        mShmzTV2 = (TextView) view.findViewById(R.id.btmz2);
        mScwjTV2 = (TextView) view.findViewById(R.id.scwj_btn2);
        mScjgTV2 = (TextView) view.findViewById(R.id.scjg_btn2);
        mZpLookTV2 = (TextView) view.findViewById(R.id.zplook2);
        mWjZpIV11 = (ImageView) view.findViewById(R.id.wjIV11);
        mWjZpIV12 = (ImageView) view.findViewById(R.id.wjIV12);
        mWjZpIV13 = (ImageView) view.findViewById(R.id.wjIV13);

        mScwjTV1.setOnClickListener(this);
        mScjgTV1.setOnClickListener(this);
        mZpLookTV1.setOnClickListener(this);
        mScwjTV2.setOnClickListener(this);
        mScjgTV2.setOnClickListener(this);
        mZpLookTV2.setOnClickListener(this);
        mWjZpIV11.setOnClickListener(this);
        mWjZpIV12.setOnClickListener(this);
        mWjZpIV13.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            if (requestCode == 0) {
                Bitmap thumbnail;
                Uri fullPhotoUri = data.getData();
                String imgPath = fullPhotoUri.getPath();
                switch (curIVSelPic) {
                    case 11:
                        mTempBitmap = BitmapFactory.decodeFile(imgPath);
                        thumbnail = BitmapHelper.getThumbnail(getActivity(), fullPhotoUri,
                                60 * (int) Constants.DENSITY, 60 * (int) Constants.DENSITY);
                        mWjZpIV11.setBackgroundDrawable(new BitmapDrawable(thumbnail));
                        break;
                    case 12:
                        mTempBitmap = BitmapFactory.decodeFile(imgPath);
                        thumbnail = BitmapHelper.getThumbnail(getActivity(), fullPhotoUri,
                                60 * (int) Constants.DENSITY, 60 * (int) Constants.DENSITY);
                        mWjZpIV12.setBackgroundDrawable(new BitmapDrawable(thumbnail));
                        break;
                    case 13:
                        mTempBitmap = BitmapFactory.decodeFile(imgPath);
                        thumbnail = BitmapHelper.getThumbnail(getActivity(), fullPhotoUri,
                                60 * (int) Constants.DENSITY, 60 * (int) Constants.DENSITY);
                        mWjZpIV13.setBackgroundDrawable(new BitmapDrawable(thumbnail));
                        break;
                }
                mShiFouJiangSCFlag = true;
            }
            if (requestCode == 1) {
                Bitmap thumbnail;
                String imgPath = mCurFromCamare.getPath();
                switch (curIVSelPic) {
                    case 11:
                        mTempBitmap = BitmapFactory.decodeFile(imgPath);
                        thumbnail = BitmapHelper.getThumbnail(getActivity(), mCurFromCamare,
                                60 * (int) Constants.DENSITY, 60 * (int) Constants.DENSITY);
                        mWjZpIV11.setBackgroundDrawable(new BitmapDrawable(thumbnail));
                        break;
                    case 12:
                        mTempBitmap = BitmapFactory.decodeFile(imgPath);
                        thumbnail = BitmapHelper.getThumbnail(getActivity(), mCurFromCamare,
                                60 * (int) Constants.DENSITY, 60 * (int) Constants.DENSITY);
                        mWjZpIV12.setBackgroundDrawable(new BitmapDrawable(thumbnail));
                        break;
                    case 13:
                        mTempBitmap = BitmapFactory.decodeFile(imgPath);
                        thumbnail = BitmapHelper.getThumbnail(getActivity(), mCurFromCamare,
                                60 * (int) Constants.DENSITY, 60 * (int) Constants.DENSITY);
                        mWjZpIV13.setBackgroundDrawable(new BitmapDrawable(thumbnail));
                        break;
                }
                mShiFouJiangSCFlag = true;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scwj_btn1:
                if (mShiFouJiangSCFlag) {

                } else {
                    App.getInstance().showToast("先选择照片");
                    return;
                }
                break;
            case R.id.wjIV11:
                if (mWjNum11 < 2) {
                    App.getInstance().showToast("请先上传前面的未上传的照片");
                    return;
                } else if (mWjNum11 > 2) {
                    App.getInstance().showToast("此照片已上传");
                    return;
                }

                if (mChoosePicDialog == null) {
                    mChoosePicDialog = new ChoosePicDialog(getActivity(), this);
                }
                mChoosePicDialog.show();
                curIVSelPic = 11;
                break;
            case R.id.wjIV12:
                if (mWjNum11 < 1) {
                    App.getInstance().showToast("请先上传上一张照片");
                    return;
                } else if (mWjNum11 > 1) {
                    App.getInstance().showToast("此照片已上传");
                    return;
                }

                if (mChoosePicDialog == null) {
                    mChoosePicDialog = new ChoosePicDialog(getActivity(), this);
                }
                mChoosePicDialog.show();
                curIVSelPic = 12;
                break;
            case R.id.wjIV13:
                if (mWjNum11 > 0) {
                    App.getInstance().showToast("此照片已上传");
                    return;
                }

                if (mChoosePicDialog == null) {
                    mChoosePicDialog = new ChoosePicDialog(getActivity(), this);
                }
                mChoosePicDialog.show();
                curIVSelPic = 13;
                break;
            //相册
            case R.id.goToAlbum:
                dismissmChooseIconDialog();
                selectPicFromAlbum();
                break;
            //相机
            case R.id.goToCamera:
                dismissmChooseIconDialog();
                mCurFromCamare = Uri.parse("file://" + Constants.BZ_PIC_PATH + "/"
                        + "bzzbz_" + curIVSelPic + "id_" + ((RwxqActivity) getActivity()).mOrderId
                        + "time_" + System.currentTimeMillis() + ".jpg");
                getPhotoFromCamera(mCurFromCamare, 1);
                break;
        }
    }

    /**
     * 调用相机拍照
     * @param uri
     * @param requestCode
     */
    private void getPhotoFromCamera(Uri uri,int requestCode){
        mkdirs(uri);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, requestCode);//or TAKE_SMALL_PICTURE
    }

    /**
     * 创建存储照片的文件夹
     * @param uri
     */
    private void mkdirs(Uri uri){
        String path = uri.getPath();
        File file = new File(path.substring(0, path.lastIndexOf("/")));
        if(!file.exists()){
            boolean success = file.mkdirs();
            System.out.println("创建存储照片的文件夹success = " + success);
        }
    }

    private void selectPicFromAlbum() {
        boolean isKitKatO = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        Intent getAlbum;
        if (isKitKatO) {
            getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        }
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, 0);
    }

    /**
     * 隐藏选择图片对话框
     */
    private void dismissmChooseIconDialog()
    {
        if(mChoosePicDialog != null)
        {
            mChoosePicDialog.dismiss();
        }
    }
}
