package com.xht.android.serverhelp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xht.android.serverhelp.model.PersonBean;
import com.xht.android.serverhelp.net.APIListener;
import com.xht.android.serverhelp.net.VolleyHelpApi;
import com.xht.android.serverhelp.util.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016-10-25.
 */

public class ClientFragment extends Fragment {

    private static final String TAG = "ClientFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_client, null);

        return  view;
    }

     //访问通讯录数据
    private void getTXLBarData() {
        VolleyHelpApi.getInstance().getTXLData(new APIListener() {
            @Override
            public void onResult(Object result) {
                LogHelper.i(TAG, "-----所有信息--" + result.toString());

                JSONArray JsonAy = null;

                try {
                    JsonAy = ((JSONObject) result).getJSONArray("entity");

                    int JsonArryLenth=JsonAy.length();
                    for (int i=0;i<JsonArryLenth;i++){
                        PersonBean item=new PersonBean();
                        JSONObject JsonItem = (JSONObject) JsonAy.get(i);
                        String contactName = JsonItem.optString("contactName");
                        String telephone = JsonItem.optString("telephone");//起始步骤
                        item.setmName(contactName);
                        item.setmPhone(telephone);


                        LogHelper.i(TAG, "-----任务池--" +i+"--"+contactName+telephone);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Object e) {
                App.getInstance().showToast(e.toString());
            }
        });

    }
}
