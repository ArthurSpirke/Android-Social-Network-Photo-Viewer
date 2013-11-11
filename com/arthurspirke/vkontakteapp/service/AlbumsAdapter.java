package com.arthurspirke.vkontakteapp.service;

import com.arthurspirke.vkontakteapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AlbumsAdapter extends BaseAdapter {

	private String[] urls;
	private String[] titles;
	
	private static LayoutInflater layoutInflater;
	private ImageLoadingManager imageLoader;
	
	public AlbumsAdapter(Context activity, String[] urls, String[] titles){
		this.urls = urls;
		this.titles = titles;
		this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.imageLoader = new ImageLoadingManager(activity.getApplicationContext());
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
	public View getView(int position, View converterView, ViewGroup parent) {
		View view = converterView;
		if(converterView == null){
			view = layoutInflater.inflate(R.layout.item, null);
		}
		
		ImageView imageView = (ImageView) view.findViewById(R.id.special_image_view);
		TextView textView = (TextView) view.findViewById(R.id.album_title);
		ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_bar_albums);
		
		String url = urls[position];
		String title = titles[position];
		
		imageLoader.showAlbums(url, title, imageView, textView, progressBar);
		
		return view;
	}
	


}
