package com.mengdd.hellosina;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mengdd.sina.weibo.api.AuthorizeSSOHelper;
import com.mengdd.sina.weibo.data.AppConfig;
import com.mengdd.sina.weibo.login.UserInfo;
import com.mengdd.utils.sina.weibo.BitmapUtils;

public class SsoAuthorActivity extends Activity {

    private AuthorizeSSOHelper mAuthSSOHelper = null;

    private ImageView mUserIcon = null;
    private TextView mUserName = null;
    private TextView mSignature = null;
    private TextView mFollowerCount = null;
    private TextView mWatchCount = null;
    private TextView mStatusCount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_sso_activity);

        mUserIcon = (ImageView) findViewById(R.id.user_icon);
        mUserName = (TextView) findViewById(R.id.user_name);

        mSignature = (TextView) findViewById(R.id.signature);
        mFollowerCount = (TextView) findViewById(R.id.follower_count);
        mWatchCount = (TextView) findViewById(R.id.watch_count);
        mStatusCount = (TextView) findViewById(R.id.status_count);

        mAuthSSOHelper = new AuthorizeSSOHelper(this);

        doAuthorize();
    }

    private void doAuthorize() {
        mAuthSSOHelper.getToken();

    }

    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     * 
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mAuthSSOHelper.onActivityResult(requestCode, resultCode, data);

        setUpViews();

    }

    private void setUpViews() {
        if (null == AppConfig.currentUserInfo) {

            Toast.makeText(this, "Sorry null userinfo", Toast.LENGTH_SHORT)
                    .show();

            return;

        }
        UserInfo userInfo = AppConfig.currentUserInfo;
        Bitmap drawable = BitmapUtils.getBitmap(userInfo.getIcon_url());
        mUserIcon.setImageBitmap(drawable);

        mUserName.setText(userInfo.getName());

        mSignature.setText(userInfo.getDescription());

        mFollowerCount.setText("Followers: " + userInfo.getFollowersCount());
        mWatchCount.setText("Friends: " + userInfo.getFriendsCount());
        mStatusCount.setText("Status: " + userInfo.getStatusesCount());

    }

}
