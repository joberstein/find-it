package com.findit.android.data;

import java.util.ArrayList;
import java.util.List;

public class Furniture {
	private long id;
	private String name;
	private List<Drawer> drawers;
	private int width;
	private int height;
	private long creatorId;
	
	public Furniture(String name, int width, int height) {
		this.id = id;
		this.name = name;
		this.drawers = new ArrayList<Drawer>();
		this.width = width;
		this.height = height;
		this.creatorId = creatorId;
	}
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Drawer> getDrawers() {
		return drawers;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public long getCreatorId() {
		return this.creatorId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDrawers(List<Drawer> drawers) {
		this.drawers = drawers;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	public void addDrawer(Drawer drawer) {
		this.drawers.add(drawer);
	}
	
	public void removeDrawer(Drawer drawer) {
		this.drawers.remove(drawer);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		else if (!(o instanceof Item)) {
			return false;
		}
		Furniture temp = (Furniture) o;
		return this.id == temp.id && 
				this.name.toLowerCase().equals(temp.name.toLowerCase()) && 
				this.width == temp.width &&
				this.height == temp.height;
	}
	
	@Override
	public int hashCode() {
		return new Long(this.id).intValue();
	}
}


