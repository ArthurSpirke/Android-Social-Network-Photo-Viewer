package com.arthurspirke.vkontakteapp.service;

import com.arthurspirke.vkontakteapp.utils.ParseUtils;

public class VkontakteAuthorized {

	private static String REDIRECT_URL = "https://oauth.vk.com/blank.html";
	private static String ERROR_WORD = "error=";
	
	private String accessToken;
	private String userId;


	public void parseUrl(String url) {
		if (url == null || url.contains(ERROR_WORD)) {
			accessToken = "";
			userId = "";
		} else {
			if (url.startsWith(REDIRECT_URL)) {
				accessToken = ParseUtils.parseByPattern(url, "access_token=(.*?)&");
				userId = ParseUtils.parseByPattern(url, "user_id=(\\d*)");
			}
		}
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getUserId() {
		return userId;
	}

	public String redirectUrl() {
		return REDIRECT_URL;
	}
}
