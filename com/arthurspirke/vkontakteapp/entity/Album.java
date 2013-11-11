package com.arthurspirke.vkontakteapp.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class Album {

	private int id;
	private int thumbId;
	private int ownerId;
	private String title;
	private String description;
	private int created;
	private int updated;
	private int size;
	private int privacy;
	private int commentPrivacy;
	private String thumbSrc;

	
		public static Album getInstance(JSONObject o) throws JSONException{
			
			Album album = new Album();
			
			album.title = o.optString("title");
			album.description = o.optString("description");
			
			
			album.id = o.optInt("aid");
			album.ownerId = o.optInt("owner_id");	
			album.thumbId = o.optInt("thumb_id");
			album.created = o.optInt(o.getString("created"));
			album.privacy = getPrivacy(o.optJSONObject("privacy"));
			album.commentPrivacy = getPrivacy(o.optJSONObject("privacy_comment"));
			album.size = o.optInt("size");
			album.updated = o.optInt("updated");
			album.thumbSrc = o.optString("thumb_src");
			
			return album;
			
		}
	
	
	private static int getPrivacy(JSONObject o){
		if(o == null) return 0;
		
		String type = o.optString("type");
		
		
		if("all".equals(type)){
		      return 0;	
		} else if("friends".equals(type)){
			  return 1;
		} else if("friends_of_friends".equals(type)){
			  return 2;
		} else if("nobody".equals(type)){
			  return 3;
		} else if("users".equals(type)){
			  return 4;
		} else {
			throw new IllegalArgumentException();
		}
		
		
	}


	public int getId() {
		return id;
	}


	public int getThumbId() {
		return thumbId;
	}


	public int getOwnerId() {
		return ownerId;
	}


	public String getTitle() {
		return title;
	}


	public String getDescription() {
		return description;
	}


	public int getCreated() {
		return created;
	}


	public int getUpdated() {
		return updated;
	}


	public int getSize() {
		return size;
	}


	public int getPrivacy() {
		return privacy;
	}


	public int getCommentPrivacy() {
		return commentPrivacy;
	}


	public String getThumbSrc() {
		return thumbSrc;
	}

	
	
	
}
