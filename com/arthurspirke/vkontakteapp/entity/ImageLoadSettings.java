package com.arthurspirke.vkontakteapp.entity;

public class ImageLoadSettings {

	private final int heightGrowUpLimit;
	private final int widthGrowUpLimit;
	
	public ImageLoadSettings(int heightGrowUpLimit, int widthGrowUpLimit) {
		this.heightGrowUpLimit = heightGrowUpLimit;
		this.widthGrowUpLimit = widthGrowUpLimit;
	}

	public int getHeightGrowUpLimit() {
		return heightGrowUpLimit;
	}

	public int getWidthGrowUpLimit() {
		return widthGrowUpLimit;
	}
	
	
	
}
