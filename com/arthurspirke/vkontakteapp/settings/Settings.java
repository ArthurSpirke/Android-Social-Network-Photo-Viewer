package com.arthurspirke.vkontakteapp.settings;

public enum Settings {

	NOTIFY("notify"),
	FRIENDS("friends"),
	PHOTOS("photos"),
	AUDIO("audio"),
	VIDEO("video"),
	DOCS("docs"),
	STATUS("status"),
	NOTES("notes"),
	PAGES("pages"),
	WALL("wall"),
	GROUPS("groups"),
	MESSAGES("messages"),
	OFFLINE("offline"),
	NOTIFICATION("notifications");
	
	private String name;
	
	private Settings(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
