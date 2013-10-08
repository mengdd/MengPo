package com.weibo.sina.android.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.util.Log;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;

public class RequestListenerAdapter implements RequestListener {

	private static final String LOG_TAG = "Request Listener";

	@Override
	public void onComplete(String result) {
		Log.i(LOG_TAG, "onComplete=======================" + result);
	}

	@Override
	public void onComplete4binary(ByteArrayOutputStream arg0) {
		Log.i(LOG_TAG, "onComplete4binary=======================");
	}

	@Override
	public void onError(WeiboException exception) {
		Log.e(LOG_TAG, "onError=======================");
		exception.printStackTrace();
	}

	@Override
	public void onIOException(IOException ioException) {
		Log.e(LOG_TAG, "onIOException=======================");
		ioException.printStackTrace();
	}

}