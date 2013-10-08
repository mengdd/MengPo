package com.weibo.sina.android.api;

import com.weibo.sdk.android.WeiboParameters;
import com.weibo.sdk.android.net.RequestListener;
import com.weibo.sina.android.utils.AppConstants;

public class AuthorizeAPI extends SinaWeiboAPI {

	private static final String REQUEST_URL = "https://api.weibo.com/oauth2/access_token";

	// 输入参数
	private static final String SINA_CLIENT_ID = "client_id";
	private static final String SINA_CLIENT_SECRET = "client_secret";
	private static final String SINA_GRANT_TYPE = "grant_type";
	private static final String SINA_GRANT_TYPE_VALUE = "authorization_code";
	private static final String SINA_REDIRECT_URI = "redirect_uri";
	public static final String SINA_CODE = "code";
	
	//返回结果
	public static final String SINA_ACCESS_TOKEN = "access_token";
	public static final String SINA_EXPIRES_IN = "expires_in";
	public static final String SINA_EXPIRES_UID = "uid";

	// 此处填上本应用相关信息

	/**
	 * 根据code获取AccessToken
	 */
	public static void getAccessTokenByCode(String code, RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		params.add(SINA_CLIENT_ID, AppConstants.APP_KEY);
		params.add(SINA_CLIENT_SECRET, AppConstants.APP_SECRET);
		params.add(SINA_GRANT_TYPE, SINA_GRANT_TYPE_VALUE);
		params.add(SINA_CODE, code);
		params.add(SINA_REDIRECT_URI, AppConstants.REDIRECT_URL);
		// 调用基类的方法发送请求，返回结果在listener的回调中查看
		request(REQUEST_URL, params, HTTPMETHOD_POST, listener);
	}
	/*
	 * 返回数据
	 * {
	 * "access_token": "ACCESS_TOKEN",
	 * "expires_in": 1234,
	 * "remind_in":"798114",
	 * "uid":"12341234"
	 * }
	 */
}
