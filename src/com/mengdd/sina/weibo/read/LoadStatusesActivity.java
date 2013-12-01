package com.mengdd.sina.weibo.read;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.mengdd.hellosina.R;
import com.mengdd.sina.weibo.api.RequestListenerAdapter;
import com.mengdd.sina.weibo.api.SinaWeiboAPI.FEATURE;
import com.mengdd.sina.weibo.api.StatusesAPI;
import com.mengdd.sina.weibo.data.StatusItem;

public class LoadStatusesActivity extends Activity {
    private static final String TAG = "Load";
    private Button mRefreshBtn = null;
    private ListView mListView = null;
    private HomeStatusAdapter mAdapter = null;
    private final List<StatusItem> mStatusItems = new ArrayList<StatusItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_load);
        mListView = (ListView) findViewById(R.id.mainList);
        mAdapter = new HomeStatusAdapter(LoadStatusesActivity.this,
                mStatusItems);
        mListView.setAdapter(mAdapter);

        mRefreshBtn = (Button) findViewById(R.id.btn_refresh);
        mRefreshBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                refreshStatuses();
            }
        });

        refreshStatuses();
    }

    private void refreshStatuses() {

        StatusesAPI
                .friendsTimeline(30, 1, false, FEATURE.ALL, mRefreshListener);
    }

    private final RequestListenerAdapter mRefreshListener = new RequestListenerAdapter() {

        @Override
        public void onComplete(String result) {
            Log.i(TAG, "onComplete: " + result);

            mStatusItems.clear();
            try {
                JSONObject jsonObject = new JSONObject(result);

                JSONArray jsonArray = jsonObject.optJSONArray("statuses");

                for (int i = 0; i < jsonArray.length(); ++i) {
                    Log.i(TAG, "--" + i + "--" + jsonArray.get(i));
                    StatusItem item = new StatusItem(
                            (JSONObject) jsonArray.get(i));
                    mStatusItems.add(item);
                }

                LoadStatusesActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();

                    }
                });

            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        };
    };

}
