package com.arthurspirke.vkontakteapp.entity;


import android.widget.ImageView;
import android.widget.TextView;

public class UserAlbum extends BaseImage {

	private final String title;
	private final TextView textView;

	public UserAlbum(String url, ImageView imageView, String title, TextView textView) {
		super(url, imageView);
		this.title = title;
		this.textView = textView;
		textView.setText(title);
	}

	public String getTitle() {
		return title;
	}

	public TextView getTextView() {
		return textView;
	}
	
	

}
