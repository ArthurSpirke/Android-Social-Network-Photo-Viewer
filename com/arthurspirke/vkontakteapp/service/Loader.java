package com.arthurspirke.vkontakteapp.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ProgressBar;

import com.arthurspirke.vkontakteapp.entity.ImageLoadSettings;
import com.arthurspirke.vkontakteapp.entity.UserAlbum;
import com.arthurspirke.vkontakteapp.entity.UserPhoto;

public class Loader implements Runnable{
	
    private UserPhoto photo;
    private UserAlbum userAlbum;
    private String operation;
    private ProgressBar progressBar;
    private CacheManager cacheManager;
    private Handler handler;
    private Context context;
    
	Loader(UserPhoto photo, String operation, ProgressBar progressBar, CacheManager cacheManager, Handler handler, Context context){
		this.photo = photo;
		this.operation = operation;
		this.progressBar = progressBar;
		this.cacheManager = cacheManager;
		this.handler = handler;
		this.context = context;
	}
	
	Loader(UserAlbum userAlbum, String operation, ProgressBar progressBar, CacheManager cacheManager, Handler handler, Context context){
		this.userAlbum = userAlbum;
		this.operation = operation;
		this.progressBar = progressBar;
		this.cacheManager = cacheManager;
		this.handler = handler;
		this.context = context;
	}
	
	@Override
	public void run() {
		
		if("loadAlbums".equals(operation)){
			albums();
		} else if("loadPhotos".equals(operation)){
			photos();
		} else if("loadSinglePhoto".equals(operation)){
			singlePhoto();
		} else {
			throw new IllegalStateException();
		}
	}
	
	private synchronized void albums(){
		
       if(cacheManager.contains(userAlbum.getUrl(), CacheManager.Type.ALBUMS)){
			   Displayer d = new Displayer(cacheManager.getAlbum(userAlbum.getUrl()), userAlbum, progressBar);
			   handler.post(d);
       } else {
			   ImageLoadSettings settings = new ImageLoadSettings(600, 700);
			   ImageOptimiserLoader optimizer = new ImageOptimiserLoader(userAlbum.getUrl(), context, settings);
    	   Bitmap bitmap = optimizer.getOptimizedBitmap();
    	   cacheManager.cacheAlbum(userAlbum.getUrl(), bitmap);
			   Displayer d = new Displayer(bitmap, userAlbum, progressBar);
			   handler.post(d);
       }	
	}
	
	private synchronized void photos(){
		
		if(cacheManager.contains(photo.getUrl(), CacheManager.Type.THUMB_PHOTO)){
            handle(cacheManager.getThumbPhoto(photo.getUrl()));
		} else {
			Bitmap bitmap = load(600, 700);
			cacheManager.cacheThumbPhoto(photo.getUrl(), bitmap);
			handle(bitmap);
		}
		
	}
	
	private synchronized void singlePhoto(){
		
		if(cacheManager.contains(photo.getUrl(), CacheManager.Type.BIG_PHOTO)){
			handle(cacheManager.getBigPhoto(photo.getUrl()));
		} else {
			Bitmap bitmap = load(50, 100);
			cacheManager.cacheBigPhoto(photo.getUrl(), bitmap);
			
			Displayer d = new Displayer(bitmap, photo, progressBar);
			handler.post(d);
		}

	}
	
	private synchronized Bitmap load(int optiHeight, int optiWidth){
		ImageLoadSettings settings = new ImageLoadSettings(optiHeight, optiWidth);
		ImageOptimiserLoader optimizer = new ImageOptimiserLoader(photo.getUrl(), context, settings);
		return optimizer.getOptimizedBitmap();
	}
	
	private synchronized void handle(Bitmap bitmap){
		Displayer d = new Displayer(bitmap, photo, progressBar);
		handler.post(d);
	}
	
}