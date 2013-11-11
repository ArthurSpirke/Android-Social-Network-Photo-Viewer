package com.arthurspirke.vkontakteapp;

import com.arthurspirke.vkontakteapp.service.ImageLoadingManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;


public class PhotosSlideFragment extends Fragment {

	
    private static final String PHOTO = "photo";
    private static final String URL = "url";
    
    private int pageNumber;
    private String url;
    
    private ImageLoadingManager imageLoader;
	
	public static PhotosSlideFragment create(int position, String url) {
		PhotosSlideFragment fragment = new PhotosSlideFragment();
		Bundle args = new Bundle();
		args.putInt(PHOTO, position);
		args.putString(URL, url);
		fragment.setArguments(args);
		return fragment;
	}

	public PhotosSlideFragment() {}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(PHOTO);
        url = getArguments().getString(URL);
        imageLoader = new ImageLoadingManager(getActivity().getApplicationContext());
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		getActivity().setTitle("Photo - " + pageNumber);

		ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_photos_slide, container, false);
		ImageView imageView = (ImageView) viewGroup.findViewById(R.id.single_photo);
		ProgressBar progressBar = (ProgressBar) viewGroup.findViewById(R.id.progress_bar_big_photo);
		
		imageLoader.showSinglePhoto(url, imageView, progressBar);
		
		return viewGroup;
	}
	
	
	public int getPageNumber(){
		return pageNumber;
	}


}
