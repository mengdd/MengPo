package com.mengdd.hellosina;

import com.weibo.sina.android.api.AuthorizeSSOHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SsoAuthorActivity extends Activity {

	private AuthorizeSSOHelper mAuthSSOHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.author_sso_activity);

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
	}

}
