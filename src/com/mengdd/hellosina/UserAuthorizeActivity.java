package com.mengdd.hellosina;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sina.android.api.RequestListenerAdapter;
import com.weibo.sina.android.api.SinaWeiboAPI;
import com.weibo.sina.android.api.UsersAPI;
import com.weibo.sina.android.data.UserInfo;
import com.weibo.sina.android.utils.AccessTokenKeeper;
import com.weibo.sina.android.utils.AppConfig;
import com.weibo.sina.android.utils.AppConstants;
import com.weibo.sina.android.utils.BitmapUtils;

public class UserAuthorizeActivity extends Activity {

	private static final String LOG_TAG = "user_login";
	private Weibo mWeibo = null;
	private TextView mUserInfoTextView = null;
	private Activity mInstance = null;
	private UserInfo mUserInfo = null;

	private Button mForceAuthorButton = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_authorize_activity);

		mInstance = UserAuthorizeActivity.this;
		mUserInfoTextView = (TextView) findViewById(R.id.userInfo);
		// 实现TextView中文字可滚动
		mUserInfoTextView.setMovementMethod(new ScrollingMovementMethod());

		mForceAuthorButton = (Button) findViewById(R.id.force_authorize);
		mForceAuthorButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				doAuthorize();
			}
		});

		if (null != AppConfig.currentUserInfo.getName()) {
			mUserInfo = AppConfig.currentUserInfo;
			try {

				final Bitmap drawable = BitmapUtils.getBitmap(mUserInfo
						.getIcon_url());
				updateViews(drawable);

			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			doAuthorize();
		}

	}

	private void doAuthorize() {
		mWeibo = Weibo.getInstance(AppConstants.APP_KEY,
				AppConstants.REDIRECT_URL, AppConstants.SCOPE);
		mWeibo.anthorize(mInstance, new AuthDialogListener());
	}

	class AuthDialogListener implements WeiboAuthListener {

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth cancel",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onComplete(Bundle values) {
			Log.i(LOG_TAG, "onComplete! + values: " + values);

			String token = values.getString("access_token");
			String expiresIn = values.getString("expires_in");
			String uid = values.getString("uid");
			// save to pref and other class
			saveAccessToken(token, expiresIn);

			// 返回值
			/*
			 * values: Bundle[{uid=1791147585, expires_in=103344,
			 * scope=follow_app_official_microblog, remind_in=103344,
			 * access_token=2.00L_TNxBuvTvKC53456b5a3cAJvG2C}]
			 */

			// get user information
			getUserInfo(Long.parseLong(uid));

		}

		@Override
		public void onError(WeiboDialogError e) {
			Toast.makeText(getApplicationContext(),
					"Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();

		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(),
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

	}

	private void saveAccessToken(String token, String expiresIn) {

		Oauth2AccessToken accessToken = new Oauth2AccessToken(token, expiresIn);

		if (accessToken.isSessionValid()) {
			// 存进Preferences
			AccessTokenKeeper.keepAccessToken(mInstance, accessToken);
			// 设置进基类
			AppConfig.accessToken = accessToken;
			SinaWeiboAPI.setOAuth2accessToken(accessToken);

			Toast.makeText(mInstance, "认证成功!!!", Toast.LENGTH_SHORT).show();

		}
		else {
			Toast.makeText(mInstance, "accessToken is not valid!",
					Toast.LENGTH_LONG).show();
		}

	}

	private String mUserJson = null;

	private void getUserInfo(final long uid) {

		Log.i(LOG_TAG, "getUserInfo");

		UsersAPI.show(uid, new RequestListenerAdapter() {
			@Override
			public void onComplete(String json) {
				super.onComplete(json);
				mUserJson = json;

				mUserInfo = new UserInfo(json);

				AppConfig.saveUserInfo(mInstance, mUserInfo);

				final Bitmap drawable = BitmapUtils.getBitmap(mUserInfo
						.getIcon_url());

				mInstance.runOnUiThread(new Runnable() {

					@Override
					public void run() {

						updateViews(drawable);

					}
				});

			}
		});

	}

	private void updateViews(final Bitmap icon) {
		mUserInfoTextView.setText(mUserJson);
		TextView userNameTextView = (TextView) findViewById(R.id.userName);
		ImageView userImageView = (ImageView) findViewById(R.id.userIcon);

		userNameTextView.setText(mUserInfo.getName());

		userImageView.setImageBitmap(icon);

	}
}
