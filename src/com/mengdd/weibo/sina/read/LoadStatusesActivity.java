package com.mengdd.weibo.sina.read;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mengdd.hellosina.R;
import com.mengdd.hellosina.R.id;
import com.mengdd.hellosina.R.layout;
import com.weibo.sina.android.api.RequestListenerAdapter;
import com.weibo.sina.android.api.SinaWeiboAPI.FEATURE;
import com.weibo.sina.android.api.StatusesAPI;
import com.weibo.sina.android.data.StatusItem;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class LoadStatusesActivity extends Activity {
	private static final String TAG = "Load";
	private Button mRefreshBtn = null;
	private ListView mListView = null;
	private HomeStatusAdapter mAdapter = null;
	private List<StatusItem> mStatusItems = new ArrayList<StatusItem>();

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
	}

	private void refreshStatuses() {

		StatusesAPI
				.friendsTimeline(30, 1, false, FEATURE.ALL, mRefreshListener);
	}

	private RequestListenerAdapter mRefreshListener = new RequestListenerAdapter() {

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
