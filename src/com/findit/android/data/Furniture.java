package com.findit.android.data;

import com.findit.android.data.abstraction.RootItemWithList;

public class Furniture extends RootItemWithList<Drawer> {
	private int width;
	private int height;
	
	public Furniture(String name, int width, int height, long creatorId) {
		super(name, creatorId);
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Drawer createDrawer(String name, int locIndex) {
		return new Drawer(name, locIndex, this.getId(), this.getCreatorId());
	}
}


