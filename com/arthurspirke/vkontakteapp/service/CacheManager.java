package com.arthurspirke.vkontakteapp.service;


import android.graphics.Bitmap;

public class CacheManager {
    
	public enum Type{
		ALBUMS, THUMB_PHOTO, BIG_PHOTO;
	}
	
	private MemoryCache<String, Bitmap> albumsCache;
	private MemoryCache<String, Bitmap> thumbPhotosCache;
	private MemoryCache<String, Bitmap> bigPhotosCache;
	
	private int albumsCacheSizeLimit;
	private int thumbPhotoCacheSizeLimit;
	private int bigPhotosCacheSizeLimit;
	
	private int albumsCacheSize;
	private int thumbPhotoCacheSize;
	private int bigPhotosCacheSize;
	
	private int allCacheSize;

	private CacheManager(){
		albumsCache = new MemoryCache<String, Bitmap>();
		thumbPhotosCache = new MemoryCache<String, Bitmap>();
		bigPhotosCache = new MemoryCache<String, Bitmap>();
		
		int allMemory = (int) (Runtime.getRuntime().maxMemory() / 3);
		albumsCacheSizeLimit = (int) (allMemory * 0.20);
		thumbPhotoCacheSizeLimit = (int) (allMemory * 0.15);
		bigPhotosCacheSizeLimit = (int) (allMemory * 0.65);
		
	}
	
	
	private static class Holder{
		private static final CacheManager INSTANCE = new CacheManager();
	}
	
	public static CacheManager getInstance(){
		return Holder.INSTANCE;
	}
	
	public void cacheAlbum(String url, Bitmap album){
		if(isFullCache(CacheManager.Type.ALBUMS)) return;
		
		albumsCache.put(url, album);
		
		int weight = getBitmapWeight(album);
		albumsCacheSize += weight;
		allCacheSize += weight;
	}
	
	public void cacheThumbPhoto(String url, Bitmap photo){
		if(isFullCache(CacheManager.Type.THUMB_PHOTO)) return;
		
		thumbPhotosCache.put(url, photo);
		
		int weight = getBitmapWeight(photo);
		thumbPhotoCacheSize += weight;
		allCacheSize += weight;
	}
	
	public void cacheBigPhoto(String url, Bitmap photo){
		if(isFullCache(CacheManager.Type.BIG_PHOTO)) return;
		
		bigPhotosCache.put(url, photo);
		
		int weight = getBitmapWeight(photo);
		bigPhotosCacheSize += weight;
		allCacheSize += weight;
	}
	
	public boolean contains(String url, CacheManager.Type cacheType){
		if(cacheType.equals(CacheManager.Type.ALBUMS)){
			return albumsCache.contains(url);
		} else if(cacheType.equals(CacheManager.Type.THUMB_PHOTO)){
			return thumbPhotosCache.contains(url);
		} else if(cacheType.equals(CacheManager.Type.BIG_PHOTO)){
			return bigPhotosCache.contains(url);
		} else {
			return false;
		}
	}
	
	public Bitmap getAlbum(String url){
		return albumsCache.get(url);
	}
	
	public Bitmap getThumbPhoto(String url){
		return thumbPhotosCache.get(url);
	}
	
	public Bitmap getBigPhoto(String url){
		return bigPhotosCache.get(url);
	}
	
	private boolean isFullCache(Type cacheType){
		if(cacheType.equals(CacheManager.Type.ALBUMS)){
			return albumsCacheSize >= albumsCacheSizeLimit;
		} else if(cacheType.equals(CacheManager.Type.THUMB_PHOTO)){
			return thumbPhotoCacheSize >= thumbPhotoCacheSizeLimit;
		} else if(cacheType.equals(CacheManager.Type.BIG_PHOTO)){
			return bigPhotosCacheSize >= bigPhotosCacheSizeLimit;
		}
		
		return true;
	}
	
	private int getBitmapWeight(Bitmap bitmap){
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
	
	public int getAllCacheSize(){
		return allCacheSize;
	}
	
	public void clearAllCaches(){
		if(albumsCache != null) albumsCache.clear();
		if(thumbPhotosCache != null) thumbPhotosCache.clear();
		if(bigPhotosCache != null) bigPhotosCache.clear();
		
		albumsCacheSizeLimit = 0;
		thumbPhotoCacheSizeLimit = 0;
		bigPhotosCacheSizeLimit = 0;
		albumsCacheSize = 0;
		thumbPhotoCacheSize = 0;
		bigPhotosCacheSize = 0;
	}
}
