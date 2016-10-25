package com.xht.android.serverhelp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xht.android.serverhelp.net.APIListener;
import com.xht.android.serverhelp.net.VolleyHelpApi;
import com.xht.android.serverhelp.util.IntentUtils;
import com.xht.android.serverhelp.util.LogHelper;

import org.json.JSONObject;

public class MainFragment extends Fragment implements View.OnClickListener{
	private static final String TAG = "MainFragment";
	private MainActivity mActivity;
	private int uid=0;
	private int mBZNum;
	private int mRWNum;
	private int mYJNum;
	private TextView bzzNum;
	private LinearLayout lLayout01;
	private TextView rwcNum;
	private LinearLayout lLayout02;
	private TextView yjNum;
	private LinearLayout lLayout03;
	private TextView textView;
	private LinearLayout lLayout04;
	private LinearLayout lLayout05;
	private LinearLayout lLayout06;
	private View view;
	private int[] mCompIds;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getMainData();


	}

	//访问数据显示数量
	private void getMainData() {

			VolleyHelpApi.getInstance().getMainData(new APIListener() {

				@Override
				public void onResult(Object result) {
					LogHelper.i(TAG, "----11首页的所有信息--" + result.toString());
					//{"yw":0,"bz":0,"yj":3}
					//JSONObject JSONOB= (JSONObject) result;
					JSONObject JSONOB = ((JSONObject) result).optJSONObject("entity");
					 mBZNum = JSONOB.optInt("bz");
					 mRWNum = JSONOB.optInt("yw");
					mYJNum= JSONOB.optInt("yj");

					LogHelper.i(TAG, "----所有信息--" + mBZNum+mRWNum+mYJNum);

					uDpateDataMethod();
				}
				@Override
				public void onError(Object e) {
					App.getInstance().showToast(e.toString());
				}
			});
		}
	private void uDpateDataMethod() {
		bzzNum.setText(mBZNum+"");
		rwcNum.setText(mRWNum+"");
		yjNum.setText(mYJNum+"");
	}

	@Override
	public void onResume() {
		super.onResume();

		getMainData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		initialize(view);

		lLayout01.setOnClickListener(this);
		lLayout02.setOnClickListener(this);
		lLayout03.setOnClickListener(this);
		lLayout04.setOnClickListener(this);
		lLayout05.setOnClickListener(this);

		return view;		
	}

	@Override
	public void onClick(View v) {

		Bundle bundle=new Bundle();

		bundle.putInt("uid",2);// TODO 传用户id

		switch (v.getId()) {
			case R.id.lLayout01:
				mActivity.switchToActivity(BZListActivity.class, null, 0, false, false);
				break;
			case R.id.lLayout02:
				LogHelper.i(TAG,"------+任务池");

				IntentUtils.startActivityNumber(mActivity,bundle,CertificateActivity.class);

				break;
			case R.id.lLayout03:
				LogHelper.i(TAG,"------+预警");
				IntentUtils.startActivityNumber(mActivity,bundle,WarningActivity.class);
				break;
			case R.id.lLayout04:
				LogHelper.i(TAG,"------+个人绩效");
				IntentUtils.startActivityNumber(mActivity,bundle,PersonalActivity.class);
				break;
			case R.id.lLayout05:
				LogHelper.i(TAG,"------+办证指南");
				break;
		}
	}

	private void initialize(View view) {

		bzzNum = (TextView) view.findViewById(R.id.bzzNum);
		lLayout01 = (LinearLayout)view.findViewById(R.id.lLayout01);
		rwcNum = (TextView)view.findViewById(R.id.rwcNum);
		lLayout02 = (LinearLayout)view.findViewById(R.id.lLayout02);
		yjNum = (TextView) view.findViewById(R.id.yjNum);
		lLayout03 = (LinearLayout) view.findViewById(R.id.lLayout03);
		textView = (TextView) view.findViewById(R.id.textView);
		lLayout04 = (LinearLayout) view.findViewById(R.id.lLayout04);
		lLayout05 = (LinearLayout) view.findViewById(R.id.lLayout05);
		lLayout06 = (LinearLayout) view.findViewById(R.id.lLayout06);
	}
}
