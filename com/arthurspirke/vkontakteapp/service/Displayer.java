package com.arthurspirke.vkontakteapp.service;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ProgressBar;

import com.arthurspirke.vkontakteapp.R;
import com.arthurspirke.vkontakteapp.entity.UserAlbum;
import com.arthurspirke.vkontakteapp.entity.UserPhoto;

public class Displayer implements Runnable{

		private Bitmap bitmap;
		private UserPhoto photo;
		private UserAlbum userAlbum;
		
		private ProgressBar progressBar;
		
		Displayer(Bitmap bitmap, UserPhoto photo, ProgressBar progressBar){
		this.bitmap = bitmap;
		this.photo = photo;
		this.progressBar = progressBar;
		}
		
		Displayer(Bitmap bitmap, UserAlbum userAlbum, ProgressBar progressBar){
		this.bitmap = bitmap;
		this.userAlbum = userAlbum;
		this.progressBar = progressBar;
		}
		
		
		@Override
		public void run() {
			
			progressBar.setVisibility(View.GONE);
			
			if(photo != null){
				
				photo.getImageView().setVisibility(View.VISIBLE);
				
				if(bitmap != null){
					photo.getImageView().setImageBitmap(bitmap);
				} else {
					photo.getImageView().setImageResource(R.drawable.image_not_load);
				}
			}
			
			

			if(userAlbum != null){
				userAlbum.getImageView().setVisibility(View.VISIBLE);
				userAlbum.getTextView().setVisibility(View.VISIBLE);
				
				if(bitmap != null){
					userAlbum.getImageView().setImageBitmap(bitmap);
				} else {
					userAlbum.getImageView().setImageResource(R.drawable.image_not_load);
				}
			}
				
          
			
		}
		
	}

