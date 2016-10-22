package com.xht.android.serverhelp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 任务详情
 * @author czw 2016-10-13
 */
public class RwxqActivity extends Activity {
    private static final String TAG = "RwxqActivity";
    private Fragment mFragment1, mFragment2, mFragment3;
    private RadioButton mRBtn1, mRBtn2, mRBtn3;
    private RadioGroup rg;
    public int mOrderId;
    private ProgressDialog mProgDoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderId = getIntent().getIntExtra("ordId", 0);
        setContentView(R.layout.activity_rwxq);
        rg = (RadioGroup) findViewById(R.id.switch_tabs);
        mRBtn1 = (RadioButton) findViewById(R.id.tab1);
        mRBtn2 = (RadioButton) findViewById(R.id.tab2);
        mRBtn3 = (RadioButton) findViewById(R.id.tab3);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        mFragment1 = fm.findFragmentByTag("f1");
        if (mFragment1 == null) {
            mFragment1 = JinDuFragment.newInstance("", null);
        }
        ft.add(R.id.fragment_contain, mFragment1, "f1");
        mFragment2 = fm.findFragmentByTag("f2");
        if (mFragment2 == null) {
            mFragment2 = GenJinFragment.newInstance("", null);
        }
        ft.add(R.id.fragment_contain, mFragment2, "f2");
        mFragment3 = fm.findFragmentByTag("f3");
        if (mFragment3 == null) {
            mFragment3 = DetailFragment.newInstance("", null);
        }
        ft.add(R.id.fragment_contain, mFragment3, "f3");
        ft.commit();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(RadioGroup group, int checkedId) {
                  updateFragmentVisibility();
                  switch (checkedId) {
                      case R.id.tab1:
                          break;
                      case R.id.tab2:
                          break;
                      case R.id.tab3:
                          break;

                      default:
                          break;
                  }
              }
        });

        updateFragmentVisibility();
    }

    // Update fragment visibility based on current check box state.
    void updateFragmentVisibility() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (mRBtn1.isChecked()) {
            ft.show(mFragment1);
        } else {
            ft.hide(mFragment1);
        }
        if (mRBtn2.isChecked()) {
            ft.show(mFragment2);
        } else {
            ft.hide(mFragment2);
        }
        if (mRBtn3.isChecked()) {
            ft.show(mFragment3);
        } else {
            ft.hide(mFragment3);
        }
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                Fragment fragment = getFragmentManager().findFragmentByTag("f1");
                if (fragment != null && fragment.isVisible()) {
                    fragment.onActivityResult(requestCode, resultCode, intent);
                }
            }
        }
    }

    public void createProgressDialog(String title) {
        if (mProgDoal == null) {
            mProgDoal = new ProgressDialog(this);
        }
        mProgDoal.setTitle(title);
        mProgDoal.setIndeterminate(true);
        mProgDoal.setCancelable(false);
        mProgDoal.show();
    }

    /**
     * 隐藏mProgressDialog
     */
    public void dismissProgressDialog()
    {
        if(mProgDoal != null)
        {
            mProgDoal.dismiss();
            mProgDoal = null;
        }
    }

}
