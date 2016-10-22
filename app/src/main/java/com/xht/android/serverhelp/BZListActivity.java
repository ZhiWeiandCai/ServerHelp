package com.xht.android.serverhelp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xht.android.serverhelp.model.BZItem;
import com.xht.android.serverhelp.net.APIListener;
import com.xht.android.serverhelp.net.VolleyHelpApi;
import com.xht.android.serverhelp.util.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 2016-10-13 办证中列表
 * @author czw
 */
public class BZListActivity extends Activity {
    private static final String TAG = "BZListActivity";

    private ProgressDialog mProgDoal;
    private ListView mListView;
    private ArrayList<BZItem> mBZItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bz_list);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setPadding(0, 0, 24, 0);
        TextView textViewL = new TextView(this);
        textViewL.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        textViewL.setGravity(Gravity.CENTER_VERTICAL);
        textViewL.setText("返回");
        textViewL.setTextSize(18);
        TextView textViewR = new TextView(this);
        textViewR.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        textViewR.setGravity(Gravity.CENTER_VERTICAL);
        textViewR.setText("刷新");
        textViewR.setTextSize(18);
        linearLayout.addView(textViewL);
        View view = new View(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        linearLayout.addView(view);
        linearLayout.addView(textViewR);
        final ActionBar aBar = getActionBar();
        aBar.setCustomView(linearLayout,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ActionBar.LayoutParams lp = (ActionBar.LayoutParams) linearLayout.getLayoutParams();
        lp.gravity = lp.gravity & ~Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK | Gravity.CENTER_HORIZONTAL;
        aBar.setCustomView(linearLayout, lp);
        int change = ActionBar.DISPLAY_SHOW_CUSTOM;
        aBar.setDisplayOptions(change);

        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BZListActivity.this, RwxqActivity.class);
                int orderId = mBZItems.get(position).getOrdId();
                intent.putExtra("ordId", orderId);
                startActivity(intent);
            }
        });
        fetchItemTask(2);
    }

    /**
     * 加载列表数据
     * @param id 客服id
     */
    private void fetchItemTask(int id) {
        createProgressDialog("数据加载中...");
        VolleyHelpApi.getInstance().getBZItems(id, new APIListener() {
            @Override
            public void onResult(Object result) {
                LogHelper.i(TAG, result.toString());
                parseBZItems((JSONArray) result);
                setupAdapter();
                dismissProgressDialog();
            }

            @Override
            public void onError(Object e) {
                dismissProgressDialog();
                App.getInstance().showToast(e.toString());
            }
        });
    }

    private void parseBZItems(JSONArray result) {
        BZItem bZItem;
        String name;
        JSONObject jO;
        int length = result.length();
        for (int i = 0; i < length; i++) {
            try {
                jO = result.getJSONObject(i);
                name = "" + jO.getInt("flowId");
                bZItem = new BZItem(name);
                bZItem.setTime(jO.getString("robOrdTime"));
                bZItem.setKehu(jO.getString("contactName"));
                bZItem.setComp(jO.getString("compName"));
                bZItem.setCompArea(jO.getString("countyName"));
                bZItem.setOrdId(jO.getInt("orderId"));
                mBZItems.add(bZItem);
                mBZItems.add(bZItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupAdapter() {
        if (mListView == null) {
            return;
        }
        if (mBZItems != null) {
            mListView.setAdapter(new BanZAdapter(mBZItems));
        } else {
            mListView.setAdapter(null);
        }
    }

    private class BanZAdapter extends ArrayAdapter<BZItem> {

        public BanZAdapter(ArrayList<BZItem> items) {
            super(BZListActivity.this, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = BZListActivity.this.getLayoutInflater().inflate(R.layout.bzmx_item, parent, false);
                holder = new ViewHolder();
                holder.nameTV = (TextView) convertView.findViewById(R.id.nameTV);
                holder.timeTV = (TextView) convertView.findViewById(R.id.timeTV);
                holder.kehuTV = (TextView) convertView.findViewById(R.id.khTV);
                holder.compTV = (TextView) convertView.findViewById(R.id.compTV);
                holder.areaTV = (TextView) convertView.findViewById(R.id.quyuTV);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            BZItem item = getItem(position);
            holder.nameTV.setText("数据待完善-->" + item.getName());
            holder.timeTV.setText(item.getTime());
            holder.kehuTV.setText(item.getKehu());
            holder.compTV.setText(item.getComp());
            holder.areaTV.setText(item.getCompArea());

            return convertView;
        }

    }

    private class ViewHolder {
        TextView nameTV;
        TextView timeTV;
        TextView kehuTV;
        TextView compTV;
        TextView areaTV;
    }

    private void createProgressDialog(String title) {
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
    private void dismissProgressDialog()
    {
        if(mProgDoal != null)
        {
            mProgDoal.dismiss();
            mProgDoal = null;
        }
    }
}
