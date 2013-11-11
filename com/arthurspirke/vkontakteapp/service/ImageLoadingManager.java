package com.arthurspirke.vkontakteapp.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.arthurspirke.vkontakteapp.entity.UserAlbum;
import com.arthurspirke.vkontakteapp.entity.UserPhoto;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ImageLoadingManager {
	
   private Handler handler = new Handler();
   private Context context;
   
   private ExecutorService executorService;
   private static CacheManager cacheManager = CacheManager.getInstance();
   
   
   public ImageLoadingManager(Context context){
	   int cores = Runtime.getRuntime().availableProcessors();
	   this.executorService = Executors.newFixedThreadPool(cores);
	   this.context = context;
   }
   
   public void showAlbums(String url, String title, ImageView imageView, TextView textView, ProgressBar progressBar){
		imageView.setVisibility(View.GONE);
		textView.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);
	   loadAlbums(url, title, imageView, textView, progressBar);
   }
   
   public void showPhotos(String url, ImageView imageView, ProgressBar progressBar){
		imageView.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);
	   loadPhotos(url, imageView, progressBar);
   }
   
   public void showSinglePhoto(String url, ImageView imageView, ProgressBar progressBar){
		imageView.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);
	   loadSinglePhoto(url, imageView, progressBar);
   }

   private void loadAlbums(String url, String title, ImageView imageView, TextView textView, ProgressBar progressBar){
	   UserAlbum album = new UserAlbum(url, imageView, title, textView);
	   executorService.execute(new Loader(album, "loadAlbums", progressBar, cacheManager, handler, context));
   }
   
   private void loadPhotos(String url, ImageView imageView, ProgressBar progressBar){
	   UserPhoto photo = new UserPhoto(url, imageView);
	   executorService.execute(new Loader(photo, "loadPhotos", progressBar, cacheManager, handler, context));
   }
   
   private void loadSinglePhoto(String url, ImageView imageView, ProgressBar progressBar){
	   UserPhoto photo = new UserPhoto(url, imageView);
	   executorService.execute(new Loader(photo, "loadSinglePhoto", progressBar, cacheManager, handler, context));
   }
   
   

	
	
}
