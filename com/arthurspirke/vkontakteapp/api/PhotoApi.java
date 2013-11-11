package com.arthurspirke.vkontakteapp.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.arthurspirke.vkontakteapp.entity.Album;
import com.arthurspirke.vkontakteapp.entity.Photo;
import com.arthurspirke.vkontakteapp.service.RequestParams;
import com.arthurspirke.vkontakteapp.service.RequestService;

public class PhotoApi {

	private final String accessToken;
	private final String apiId;
	private final int ownerId;
	
	public PhotoApi(String accessToken, String apiId, int ownerId){
		this.accessToken = accessToken;
		this.apiId = apiId;
		this.ownerId = ownerId;
	}
	
	public List<Album> getAlbums(boolean covers) throws JSONException{
		RequestParams requestParams = new RequestParams("photos.getAlbums");
		requestParams.put("owner_id", ownerId);
		requestParams.put("need_covers", covers ? 1 : 0);
		
		RequestService requestService = new RequestService(requestParams);
		JSONObject jsonObject = requestService.sendRequest(accessToken, apiId);
		
		List<Album> albums = new ArrayList<Album>();
		
		JSONArray jsonArray = jsonObject.optJSONArray("response");
		
		if(jsonArray == null) return albums;
		
		int categoryCount = jsonArray.length();
		
		for(int i = 0; i < categoryCount; i++){
			JSONObject o = (JSONObject) jsonArray.get(i);
			Album album = Album.getInstance(o);
			if(album.getTitle().equals("DELETED")) continue;
			albums.add(album);
		}
		
		return albums;
	}
	
	public Photo getPhoto(int imageId, boolean extended, boolean photoSizes) throws JSONException{
		RequestParams params = new RequestParams("photos.getById");
		params.put("photos", ownerId + "_" + imageId);
		params.put("extended", extended ? 1 : 0);
		params.put("photo_sizes", photoSizes ? 1 : 0);
		
		RequestService requestService = new RequestService(params);
		JSONObject jsonObject = requestService.sendRequest(accessToken, apiId);
		JSONObject response = (JSONObject) jsonObject.optJSONArray("response").get(0);
		
		Photo photo = Photo.getInstance(response);
		
		return photo;
	}
	
	public List<Photo> getPhotos(long albumId, int offset, int count) throws JSONException{
		RequestParams params = new RequestParams("photos.get");
		params.put("owner_id", ownerId);
		params.put("album_id", albumId);
		params.put("extended", "1");
		params.put("offset", offset);
		params.put("limit", count);
		
		RequestService requestService = new RequestService(params);
		JSONObject jsonObject = requestService.sendRequest(accessToken, apiId);
		JSONArray items = jsonObject.optJSONArray("response");
		
		if(items == null) return new ArrayList<Photo>();
		
		int photosCount = items.length();
		
		List<Photo> photos = new ArrayList<Photo>();
		
		for(int i = 0; i < photosCount; i++){
			JSONObject o = (JSONObject) items.get(i);
			Photo photo = Photo.getInstance(o);
			photos.add(photo);
		}
		
		return photos;
	}
	
	public List<Photo> getUserPhotos(long userId, int offset, int count) throws JSONException{
		RequestParams params = new RequestParams("photos.getUserPhotos");
		params.put("user_id", userId);
		params.put("sort", "0");
		params.put("count", count);
		params.put("offset", offset);
		params.put("extended", "1");
		
		RequestService requestService = new RequestService(params);
		JSONObject jsonObject = requestService.sendRequest(accessToken, apiId);
		JSONObject response = jsonObject.optJSONObject("response");
		JSONArray items = response.optJSONArray("items");
		
		if(items == null) return new ArrayList<Photo>();
		
		List<Photo> photos = new ArrayList<Photo>();
		
		int itemsCount = items.length();
		
		for(int i = 0; i < itemsCount; i++){
			JSONObject o = (JSONObject) items.get(i);
			Photo photo = Photo.getInstance(o);
			
			photos.add(photo);
		}
		
		return photos;
	}
	
	public List<Photo> getAllPhotos(int offset, int count, boolean extended) throws JSONException{
		RequestParams params = new RequestParams("photos.getAll");
		params.put("owner_id", ownerId);
		params.put("offset", offset);
		params.put("count", count);
		params.put("extended", extended ? 1 : 0);

		RequestService requestService = new RequestService(params);
		JSONObject jsonObject = requestService.sendRequest(accessToken, apiId);
		JSONArray items = jsonObject.getJSONArray("response");
		
		if(items == null) return new ArrayList<Photo>();
		
		List<Photo> photos = new ArrayList<Photo>();
		
		int itemsCount = items.length();
		
		for(int i = 1; i < itemsCount; i++){
			JSONObject o = (JSONObject) items.get(i);
			Photo photo = Photo.getInstance(o);
			
			photos.add(photo);
		}
		
		return photos;
	}
	


}
