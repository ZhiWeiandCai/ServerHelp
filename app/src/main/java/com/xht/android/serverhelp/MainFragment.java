package com.xht.android.serverhelp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment implements View.OnClickListener{
	private static final String TAG = "MainFragment";
	private MainActivity mActivity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		view.findViewById(R.id.lLayout01).setOnClickListener(this);

		return view;		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.lLayout01:
				break;
			case R.id.lLayout02:
				break;
			case R.id.lLayout03:
				break;
			case R.id.lLayout04:
				break;
			case R.id.lLayout05:
				break;
		}
	}
}
