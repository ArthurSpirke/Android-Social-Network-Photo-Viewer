package com.arthurspirke.vkontakteapp.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.arthurspirke.vkontakteapp.entity.ImageLoadSettings;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.WindowManager;

public class ImageOptimiserLoader {

	private final String url;
	private final ImageLoadSettings settings;
	private int displayHeight;
	private int displayWidth;
	private int bitmapHeight;
	private int bitmapWidth;
	
	public ImageOptimiserLoader(String url, Context context, ImageLoadSettings settings){
		this.url = url;
		this.settings = settings;
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display d = wm.getDefaultDisplay();
		displayHeight = d.getHeight();
		displayWidth = d.getWidth();
	}
	
	public Bitmap getOptimizedBitmap(){
		initProp();
		int scale = getScale();
		
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inSampleSize = scale;
		
		InputStream in = getInputStream();
		
		Bitmap bitmap = BitmapFactory.decodeStream(in, null, o);

		closeInputStream(in);
		
		System.out.println("Height - " + bitmap.getHeight() + ", width -  " + bitmap.getWidth());
		return bitmap;
	}
	
	public Bitmap getNotOptimizedBitmap(){
		InputStream in = getInputStream();
		
		Bitmap bitmap = BitmapFactory.decodeStream(in);

		closeInputStream(in);
		
		return bitmap;
	}
	
	private void initProp(){
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		
		InputStream in = getInputStream();
				
		
		BitmapFactory.decodeStream(in, null, o);
		bitmapHeight = o.outHeight;
		bitmapWidth = o.outWidth;
		
		closeInputStream(in);
	}

	private int getScale(){
		int scale = 0;
		
		int dPosHeight = displayHeight - settings.getHeightGrowUpLimit();
		int dPosWidth = displayWidth - settings.getWidthGrowUpLimit();
			
		if(dPosHeight < 0 || dPosWidth < 0){
			return 1;
		}
		
		while(true){
			if(bitmapHeight < dPosHeight && bitmapWidth < dPosWidth){
				break;
			}
			
			bitmapHeight /= 2;
			bitmapWidth /= 2;
			scale++;
		}
		
		return scale;
	}
	
	
	private InputStream getInputStream(){
		InputStream in = null;
		
		try{
			URL streamUrl = new URL(url);
			in = streamUrl.openStream();
			
		} catch(IOException ex){
			ex.printStackTrace();
		}
		
		return in;
	}
	
	private void closeInputStream(InputStream in){
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
