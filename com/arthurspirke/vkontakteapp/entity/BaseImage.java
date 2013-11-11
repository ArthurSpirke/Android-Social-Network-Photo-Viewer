package com.arthurspirke.vkontakteapp.entity;

import android.widget.ImageView;

public class BaseImage {

	private final String url;
	private final ImageView imageView;
	
	
	public BaseImage(String url, ImageView imageView) {
		this.url = url;
		this.imageView = imageView;
	}


	public String getUrl() {
		return url;
	}


	public ImageView getImageView() {
		return imageView;
	}
	
	
	
}
