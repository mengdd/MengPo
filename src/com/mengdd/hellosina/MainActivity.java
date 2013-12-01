package com.mengdd.hellosina;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mengdd.hellosina.components.FrameHeaderViewModel;
import com.mengdd.hellosina.components.FrameHeaderViewModel.OnSettingListener;
import com.mengdd.sina.weibo.data.AppConfig;
import com.mengdd.sina.weibo.read.LoadStatusesActivity;
import com.mengdd.sina.weibo.write.SendStatusActivity;
import com.mengdd.utils.sina.weibo.AppConstants;

public class MainActivity extends Activity {

    private Resources resources = null;
    private DrawerLayout mDrawerLayout = null;
    private FrameHeaderViewModel mHeaderViewModel = null;
    private ListView mDrawerMenuList = null;
    private static Sample[] mSamples;

    private ListView mContentListView = null;
    private TextView mEmptyTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        resources = getResources();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mContentListView = (ListView) findViewById(R.id.mainList);
        mEmptyTextView = (TextView) findViewById(R.id.emptyText);
        mContentListView.setEmptyView(mEmptyTextView);
        // header
        mHeaderViewModel = new FrameHeaderViewModel(this);
        mHeaderViewModel.onCreate(null);
        mHeaderViewModel.setBackVisibility(View.GONE);
        mHeaderViewModel.setTitle(resources.getString(R.string.main_title));
        ViewGroup headerGourp = (ViewGroup) findViewById(R.id.main_title);
        headerGourp.addView(mHeaderViewModel.getView(), 0);

        // header control drawer
        mHeaderViewModel.setOnSettingListener(new OnSettingListener() {

            @Override
            public void onSetting() {
                mDrawerLayout.openDrawer(Gravity.RIGHT);

            }
        });

        initDrawerList();
        initWeiboStates();

        if (null == AppConfig.accessToken) {

        }
        else {

        }

    }

    private void initDrawerList() {

        mDrawerMenuList = (ListView) findViewById(R.id.drawer_list);
        // Instantiate the list of samples.
        mSamples = new Sample[] {
                new Sample(R.string.user_login, UserAuthorizeActivity.class),
                new Sample(R.string.user_login_sso, SsoAuthorActivity.class),

                new Sample(R.string.load, LoadStatusesActivity.class),
                new Sample(R.string.write, SendStatusActivity.class),
                new Sample(R.string.drawer, DrawerActivity.class),

        };

        mDrawerMenuList.setAdapter(new ArrayAdapter<Sample>(this,
                R.layout.drawer_item, R.id.text, mSamples));

        mDrawerMenuList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                Log.i(AppConstants.LOG_TAG, "onItemClick: " + view
                        + ",position: " + position + ",id: " + id);

                startActivity(new Intent(MainActivity.this,
                        mSamples[position].activityClass));
            }
        });

    }

    private void initWeiboStates() {
        AppConfig.loadConfig(MainActivity.this);

    }

    // 私有类，List中的每一个例子
    private class Sample {
        private final CharSequence title;
        private final Class<? extends Activity> activityClass;

        public Sample(int titleResId, Class<? extends Activity> activityClass) {
            this.activityClass = activityClass;
            this.title = getResources().getString(titleResId);
        }

        @Override
        public String toString() {
            return title.toString();
        }
    }

}
