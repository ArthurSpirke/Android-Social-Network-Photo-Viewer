package com.arthurspirke.vkontakteapp.api;

public class API{

	
	private String accessToken;
	private String apiId;
	private int ownerId;
	
	public API(String accessToken, String apiId, int ownerId){
		this.accessToken = accessToken;
		this.apiId = apiId;
		this.ownerId = ownerId;
	}
	
	
	public PhotoApi getPhotoApi(){
		return new PhotoApi(accessToken, apiId, ownerId);
	}

}
