package com.arthurspirke.vkontakteapp.service;

import com.arthurspirke.vkontakteapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class PhotosAdapter extends BaseAdapter {

	private Context context;
	private String[] urls;
	private ImageLoadingManager imageLoaderManager;
	
	public PhotosAdapter(Context context, String[] urls){
		this.context = context;
		this.urls = urls;
		this.imageLoaderManager = new ImageLoadingManager(context);
	}

	
	@Override
	public int getCount() {
		return urls.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View converView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View gridView = null;
		gridView = new View(context);
		
		gridView = inflater.inflate(R.layout.photos, null);
		
		ImageView photoImageView = (ImageView) gridView.findViewById(R.id.grid_photo);
		ProgressBar photoProgressBar = (ProgressBar) gridView.findViewById(R.id.progress_bar_thumb_photo);
		
		imageLoaderManager.showPhotos(urls[position], photoImageView, photoProgressBar);
		
		return gridView;
	}
	

}
