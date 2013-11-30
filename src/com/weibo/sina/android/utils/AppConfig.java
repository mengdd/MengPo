package com.weibo.sina.android.utils;

import android.content.Context;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.weibo.sina.android.api.SinaWeiboAPI;
import com.weibo.sina.android.data.UserInfo;

public class AppConfig {

    // AccessToken
    public static Oauth2AccessToken accessToken = null;

    // uid
    public static long currentUid = 0;
    public static final String KEY_UID_PREF = "uid";

    // user info
    public static UserInfo currentUserInfo = null;

    public static void loadConfig(Context context) {
        currentUid = SharedPrefUtil.getLong(context, KEY_UID_PREF, 0);
        accessToken = AccessTokenKeeper.readAccessToken(context);
        if (null != accessToken) {
            SinaWeiboAPI.setOAuth2accessToken(accessToken);
        }

        currentUserInfo = new UserInfo(context);

    }

    public static void saveConfig(Context context) {
        SharedPrefUtil.saveLong(context, KEY_UID_PREF, currentUid);
        AccessTokenKeeper.keepAccessToken(context, accessToken);

        if (null != currentUserInfo) {
            currentUserInfo.saveToPref(context);
        }

    }

    public static void saveUid(Context context, long uid) {
        currentUid = uid;
        SharedPrefUtil.saveLong(context, KEY_UID_PREF, currentUid);
    }

    public static void saveUserInfo(Context context, UserInfo userInfo) {
        currentUserInfo = userInfo;
        currentUserInfo.saveToPref(context);
    }
}
