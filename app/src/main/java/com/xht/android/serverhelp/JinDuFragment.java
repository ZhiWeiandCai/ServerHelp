package com.xht.android.serverhelp;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.xht.android.serverhelp.model.Constants;
import com.xht.android.serverhelp.net.VolleyHelpApi;
import com.xht.android.serverhelp.util.BitmapHelper;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


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

    private TextView mShmzTV1, mScwjTV1, mScjgTV1, mZpLookTV1, mStartTimeTV1, mCompTimeTV1, mGJPeopleTV1,
            mRefProTV1, mCancelRwTV1, mJXRwTV1, mConvRwTV1, mShmzTV2, mScwjTV2, mScjgTV2, mZpLookTV2,
            mStartTimeTV2, mCompTimeTV2, mGJPeopleTV2, mRefProTV2, mCancelRwTV2, mJXRwTV2, mConvRwTV2;
    private NetworkImageView mWjZpIV11, mWjZpIV12, mWjZpIV13, mJgZpIV11, mJgZpIV12, mJgZpIV13;
    //区域块1,2
    private RelativeLayout mPro1, mPro2;
    //提交更新进度后，出现的对任务进行的三个操作的Layout
    private LinearLayout mRwZzSel1, mRwZzSel2;
    private ChoosePicDialog mChoosePicDialog;
    private int curIVSelPic;    //为哪一个IV选择照片
    private Bitmap mTempBitmap; //将要上传的图片
    private String mTempStr; //将要上传的图片的路径
    //由于相册返回的照片路径不是想要的路径，所以临时又声明一个路径
    private String mTempStrT;
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
        //名字的View
        mShmzTV1 = (TextView) view.findViewById(R.id.btmz1);
        //上传文件的按钮
        mScwjTV1 = (TextView) view.findViewById(R.id.scwj_btn1);
        //上传结果的按钮
        mScjgTV1 = (TextView) view.findViewById(R.id.scjg_btn1);
        //“工商局”那个文本View，暂时不知道用处
        mZpLookTV1 = (TextView) view.findViewById(R.id.zplook1);
        //开始时间
        mStartTimeTV1 = (TextView) view.findViewById(R.id.kssj1nnn);
        //结束时间
        mCompTimeTV1 = (TextView) view.findViewById(R.id.jssj1nnn);
        //跟进人员
        mGJPeopleTV1 = (TextView) view.findViewById(R.id.gjry1xxx);
        //提交更新
        mRefProTV1 = (TextView) view.findViewById(R.id.submit_gx1);
        //取消任务
        mCancelRwTV1 = (TextView) view.findViewById(R.id.cancel_rw1);
        //继续任务
        mJXRwTV1 = (TextView) view.findViewById(R.id.conti_rw1);
        //转交任务
        mConvRwTV1 = (TextView) view.findViewById(R.id.conver_rw1);

        mShmzTV2 = (TextView) view.findViewById(R.id.btmz2);
        mScwjTV2 = (TextView) view.findViewById(R.id.scwj_btn2);
        mScjgTV2 = (TextView) view.findViewById(R.id.scjg_btn2);
        mZpLookTV2 = (TextView) view.findViewById(R.id.zplook2);
        //开始时间
        mStartTimeTV2 = (TextView) view.findViewById(R.id.kssj2nnn);
        //结束时间
        mCompTimeTV2 = (TextView) view.findViewById(R.id.jssj2nnn);
        //跟进人员
        mGJPeopleTV2 = (TextView) view.findViewById(R.id.gjry2xxx);
        //提交更新
        mRefProTV2 = (TextView) view.findViewById(R.id.submit_gx2);
        //取消任务
        mCancelRwTV2 = (TextView) view.findViewById(R.id.cancel_rw2);
        //继续任务
        mJXRwTV2 = (TextView) view.findViewById(R.id.conti_rw2);
        //转交任务
        mConvRwTV2 = (TextView) view.findViewById(R.id.conver_rw2);
        mPro1 = (RelativeLayout) view.findViewById(R.id.shbz1);
        mPro2 = (RelativeLayout) view.findViewById(R.id.shbz2);
        mRwZzSel1 = (LinearLayout) view.findViewById(R.id.after_sub1);
        mRwZzSel2 = (LinearLayout) view.findViewById(R.id.after_sub2);
        /* 文件上传的3张照片，顺序是从右往左排的 */
        mWjZpIV11 = (NetworkImageView) view.findViewById(R.id.wjIV11);
        mWjZpIV12 = (NetworkImageView) view.findViewById(R.id.wjIV12);
        mWjZpIV13 = (NetworkImageView) view.findViewById(R.id.wjIV13);
        /* 结果上传的3张照片，顺序是从右往左排的 */
        mJgZpIV11 = (NetworkImageView) view.findViewById(R.id.jgIV11);
        mJgZpIV12 = (NetworkImageView) view.findViewById(R.id.jgIV12);
        mJgZpIV13 = (NetworkImageView) view.findViewById(R.id.jgIV13);

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
                mTempStr = fullPhotoUri.getPath();
                mTempStrT = "bzzbz_" + curIVSelPic + "id_" + ((RwxqActivity) getActivity()).mOrderId
                        + "time_" + System.currentTimeMillis() + ".jpg";
                switch (curIVSelPic) {
                    case 11:
                        //mTempBitmap = BitmapFactory.decodeFile(imgPath);
                        thumbnail = BitmapHelper.getThumbnail(getActivity(), fullPhotoUri,
                                60 * (int) Constants.DENSITY, 60 * (int) Constants.DENSITY);
                        mWjZpIV11.setBackgroundDrawable(new BitmapDrawable(thumbnail));
                        break;
                    case 12:
                        //mTempBitmap = BitmapFactory.decodeFile(imgPath);
                        thumbnail = BitmapHelper.getThumbnail(getActivity(), fullPhotoUri,
                                60 * (int) Constants.DENSITY, 60 * (int) Constants.DENSITY);
                        mWjZpIV12.setBackgroundDrawable(new BitmapDrawable(thumbnail));
                        break;
                    case 13:
                        //mTempBitmap = BitmapFactory.decodeFile(imgPath);
                        thumbnail = BitmapHelper.getThumbnail(getActivity(), fullPhotoUri,
                                60 * (int) Constants.DENSITY, 60 * (int) Constants.DENSITY);
                        mWjZpIV13.setBackgroundDrawable(new BitmapDrawable(thumbnail));
                        break;
                }
                mShiFouJiangSCFlag = true;
            }
            if (requestCode == 1) {
                Bitmap thumbnail;
                mTempStr = mCurFromCamare.getPath();
                switch (curIVSelPic) {
                    case 11:
                        //mTempBitmap = BitmapFactory.decodeFile(imgPath);
                        thumbnail = BitmapHelper.getThumbnail(getActivity(), mCurFromCamare,
                                60 * (int) Constants.DENSITY, 60 * (int) Constants.DENSITY);
                        mWjZpIV11.setBackgroundDrawable(new BitmapDrawable(thumbnail));
                        break;
                    case 12:
                        //mTempBitmap = BitmapFactory.decodeFile(imgPath);
                        thumbnail = BitmapHelper.getThumbnail(getActivity(), mCurFromCamare,
                                60 * (int) Constants.DENSITY, 60 * (int) Constants.DENSITY);
                        mWjZpIV12.setBackgroundDrawable(new BitmapDrawable(thumbnail));
                        break;
                    case 13:
                        //mTempBitmap = BitmapFactory.decodeFile(imgPath);
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
                    ((RwxqActivity) getActivity()).createProgressDialog("正在上传照片");
                    new UploadPicTask().execute();
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

    //上传照片至Server的方法
    private boolean uploadPicFile(String picPath)
    {
        String fName = picPath.trim();
        String newName = fName.substring(fName.lastIndexOf("/")+1);
        if (!newName.substring(0, 6).equals("bzzbz_")) {
            newName = mTempStrT;
        }

        String end ="\r\n";
        String twoHyphens ="--";
        String boundary ="*****";
        try
        {
            URL url =new URL(VolleyHelpApi.BZ_PIC_UPLOAD_Url);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
          /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
          /* 设置传送的method=POST */
            con.setRequestMethod("POST");
          /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary="+boundary);
          /* 设置DataOutputStream */
            DataOutputStream ds =
                    new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; "+
                    "name=\"file1\";filename=\""+
                    newName +"\""+ end);
            ds.writeBytes(end);
          /* 取得文件的FileInputStream */
            FileInputStream fStream =new FileInputStream(picPath);
          /* 设置每次写入1024bytes */
            int bufferSize =1024;
            byte[] buffer =new byte[bufferSize];
            int length =-1;
          /* 从文件读取数据至缓冲区 */
            while((length = fStream.read(buffer)) !=-1)
            {
            /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
          /* close streams */
            fStream.close();
            ds.flush();
          /* 取得Response内容 */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b =new StringBuffer();
            while( ( ch = is.read() ) !=-1 )
            {
                b.append( (char)ch );
            }
          /* 将Response显示于Dialog */
            //showDialog("上传成功"+b.toString().trim());
          /* 关闭DataOutputStream */
            ds.close();
            return true;
        }
        catch(Exception e)
        {
            //showDialog("上传失败"+e);
            return false;
        }
    }
    /* 显示Dialog的method */
    private void showDialog(String mess)
    {
        new AlertDialog.Builder(getActivity()).setTitle("Message").setMessage(mess)
                .setNegativeButton("确定",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private class UploadPicTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean temp = uploadPicFile(mTempStr);
            return temp;
        }

        @Override
        protected void onPostExecute(Boolean param) {
            ((RwxqActivity) getActivity()).dismissProgressDialog();
            if (param) {
                showDialog("上传成功");
            } else {
                showDialog("上传失败");
            }
        }
    }
}
