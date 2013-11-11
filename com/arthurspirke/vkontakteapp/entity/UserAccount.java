package com.arthurspirke.vkontakteapp.entity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class UserAccount {

	public final static String ACCESS_TOKEN = "accessToken";
	public final static String USER_ID = "userId";
	public final static String APP_ID = "appId";
	private String accessToken;
	private int userId;

	
	public void saveUserSessionInfo(Context context, String accessToken, int userId){
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putString(ACCESS_TOKEN, accessToken);
		editor.putInt(USER_ID, userId);
		editor.commit();
	}
	
	public void loadUserSessionInfo(Context context){
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		accessToken = sharedPreferences.getString(ACCESS_TOKEN, null);
		userId = sharedPreferences.getInt(USER_ID, 0);
	}

	public String getAccessToken() {
		return accessToken;
	}

	public int getUserId() {
		return userId;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
