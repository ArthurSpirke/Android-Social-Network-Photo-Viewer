package com.arthurspirke.vkontakteapp.entity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.arthurspirke.vkontakteapp.settings.PhotoSize;

public class Photo {

	private int id;
	private int albumId;
	private String ownerId;
	private String photoText;
	private int created;
	private int likeCount;
	private boolean userLikes;
	private int commentsCount;
	private int tagsCount;
	private boolean canComment;
	private int height;
	private int width;
	private String accessKey;
	private HashMap<PhotoSize, String> photoSizes;
	
	public static Photo getInstance(JSONObject o) throws JSONException{
		Photo photo = new Photo();
		photo.id = o.getInt("pid");
		photo.albumId = o.optInt("album_id");
		photo.ownerId = o.getString("owner_id");
		photo.photoText = o.optString("text");
		photo.created = o.optInt("created");
		
		if(o.has("likes")){
			JSONObject jsonLikes = o.getJSONObject("likes");
			photo.likeCount = jsonLikes.optInt("count");
			photo.userLikes = jsonLikes.optInt("user_likes") == 1;
		}
		
		if(o.has("comments")){
			JSONObject jsonComments = o.getJSONObject("comments");
			photo.commentsCount = jsonComments.optInt("count");
		}
		
		if(o.has("tags")){
			JSONObject jsonTags = o.getJSONObject("tags");
			photo.tagsCount = jsonTags.optInt("count");
		}
		
		if(o.has("can_comments")){
			photo.canComment = o.optInt("can_comment") == 1;
		}
		
		photo.width = o.optInt("width");
		photo.height = o.optInt("height");
		photo.accessKey = o.optString("access_key");
		fillOutPhotoSizes(photo, o);
		
		return photo;
	}
	
	private static void fillOutPhotoSizes(Photo photo, JSONObject jsonSizes){
		photo.photoSizes = new HashMap<PhotoSize, String>();
		photo.photoSizes.put(PhotoSize.SMALL, jsonSizes.optString("src_small"));
		photo.photoSizes.put(PhotoSize.MIDDLE, jsonSizes.optString("src"));
		photo.photoSizes.put(PhotoSize.BIG, jsonSizes.optString("src_big"));
		photo.photoSizes.put(PhotoSize.XBIG, jsonSizes.optString("src_xbig"));
		photo.photoSizes.put(PhotoSize.XXBIG, jsonSizes.optString("src_xxbig"));
		photo.photoSizes.put(PhotoSize.XXXBIG, jsonSizes.optString("src_xxxbig"));
	}
	
	public int getId() {
		return id;
	}

	public int getAlbumId() {
		return albumId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public String getPhotoText() {
		return photoText;
	}

	public int getCreated() {
		return created;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public boolean isUserLikes() {
		return userLikes;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public int getTagsCount() {
		return tagsCount;
	}

	public boolean isCanComment() {
		return canComment;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public HashMap<PhotoSize, String> getPhotoSizes() {
		return photoSizes;
	}

	public String getMostLargestPhotoSizeUrl(){
        if(!"".equals(photoSizes.get(PhotoSize.XXXBIG))){
        	return photoSizes.get(PhotoSize.XXXBIG);
        } else if(!"".equals(photoSizes.get(PhotoSize.XXBIG))){
        	return photoSizes.get(PhotoSize.XXBIG);
        } else if(!"".equals(photoSizes.get(PhotoSize.XBIG))){
        	return photoSizes.get(PhotoSize.XBIG);
        } else if(!"".equals(photoSizes.get(PhotoSize.BIG))){
        	return photoSizes.get(PhotoSize.BIG);
        } else if(!"".equals(photoSizes.get(PhotoSize.MIDDLE))){
        	return photoSizes.get(PhotoSize.MIDDLE);
        } else {
        	return photoSizes.get(PhotoSize.SMALL);
        }
		
	}
	
}
