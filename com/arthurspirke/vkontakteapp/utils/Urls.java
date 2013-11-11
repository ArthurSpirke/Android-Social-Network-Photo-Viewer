package com.arthurspirke.vkontakteapp.utils;

import java.net.URLEncoder;

import com.arthurspirke.vkontakteapp.settings.Settings;

public class Urls {

	
	public static String appAuthUrl(String appId, Settings... settings){
		int settingsSize = settings.length;
		
		StringBuilder sb = new StringBuilder();
		sb.append("https://oauth.vk.com/authorize?client_id=");
		sb.append(appId);
		sb.append("&display=mobile&scope=");
		
		

		for(int i = 0; i < settingsSize; i++){
			sb.append(settings[i]);
			if(i != settingsSize){
				sb.append(",");
			}	
		}
		
		sb.append("&redirect_uri=");
		sb.append(URLEncoder.encode("https://oauth.vk.com/blank.html"));
		sb.append("&response_type=token&v=");
		sb.append(URLEncoder.encode("1"));
		
		return sb.toString();
		
	}
}
