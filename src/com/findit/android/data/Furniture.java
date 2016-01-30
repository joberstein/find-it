package com.findit.android.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.findit.android.dao.FindItContract.FurnitureTable;

import android.database.Cursor;

public class Furniture {
	private long id;
	private String name;
	private List<Drawer> drawers;
	private int width;
	private int height;
	private long creatorId;
	
	public Furniture(String name, int width, int height) {
		this.name = name;
		this.drawers = new ArrayList<Drawer>();
		this.width = width;
		this.height = height;
		drawers = new ArrayList<>();
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
		return creatorId;
	}
	
	public Drawer createDrawer(String name, int locIndex) {
		return new Drawer(name, locIndex, id, creatorId);
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
	
	public static Furniture createFurnitureFromCursor(Cursor c) {
		long id = c.getLong(c.getColumnIndex(FurnitureTable._ID));
		String name = c.getString(c.getColumnIndex(FurnitureTable.COLUMN_NAME_NAME));
		int width = c.getInt(c.getColumnIndex(FurnitureTable.COLUMN_NAME_WIDTH));
		int height = c.getInt(c.getColumnIndex(FurnitureTable.COLUMN_NAME_HEIGHT));
		long creatorId = c.getLong(c.getColumnIndex(FurnitureTable.COLUMN_NAME_CREATOR_ID));

		Furniture furniture = new Furniture(name, width, height);
		furniture.setId(id);
		furniture.setCreatorId(creatorId);
		
		return furniture;
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
				this.name.toLowerCase(Locale.US).equals(temp.name.toLowerCase(Locale.US)) && 
				this.width == temp.width &&
				this.height == temp.height;
	}
	
	@Override
	public int hashCode() {
		return Long.valueOf(this.id).intValue();
	}
}


