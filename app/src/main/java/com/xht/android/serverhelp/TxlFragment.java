package com.xht.android.serverhelp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xht.android.serverhelp.model.PersonBean;
import com.xht.android.serverhelp.view.ClearEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * 通讯录界面
 */
public class TxlFragment extends Fragment {
    private static final String TAG = "TxlFragment";
    private MainActivity mActivity;
    private ListView listview;
    private List<PersonBean> mTXLContacts;

    private TextView mCollectContacts;
    private TextView mClientContacts;
    private TextView mInsideContacts;
    private FrameLayout mFrameList;

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
        View view = inflater.inflate(R.layout.fragment_txl, container, false);


        mCollectContacts = (TextView)view.findViewById(R.id.mCollectContacts);
        mClientContacts = (TextView)view.findViewById(R.id.mClientContacts);
        mInsideContacts = (TextView) view.findViewById(R.id.mInsideContacts);
        mFrameList = (FrameLayout) view.findViewById(R.id.mFrameList);

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }






}
