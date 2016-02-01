package com.findit.android.data;

import java.util.ArrayList;
import java.util.List;

import com.findit.android.dao.FindItContract.DrawerTable;

import android.database.Cursor;

public class Drawer extends Item {
	List<DrawerItem> items;
	private int locIndex;
	
	Drawer(String name, int locIndex, long parentId, long creatorId) {
		super(name, ItemType.DRAWER, parentId, creatorId);
		this.items = new ArrayList<>();
		this.locIndex = locIndex;
	}
	
	public DrawerItem createDrawerItem(String name, ItemType type) {
		if (type.equals(ItemType.CATEGORY)) {
			return new Category(name, this.getId(), this.getId(), this.getCreatorId());
		}
		else if (type.equals(ItemType.SINGLE_ITEM)) {
			return new SingleItem(name, this.getId(), this.getId(), this.getCreatorId());
		}
		return null;
	}
	
	public int getLocIndex() {
		return this.locIndex;
	}
	
	public void setLocIndex(int locIndex) {
		this.locIndex = locIndex;
	}
	
	public void addItem(DrawerItem item) {
		this.items.add(item);
	}
	
	public void removeItem(DrawerItem item) {
		this.items.remove(item);
	}
	
	public List<DrawerItem> getItems() {
		return this.items;
	}
	
	public void setItems(List<DrawerItem> items) {
		this.items = items;
	}
	
	public static Drawer createDrawerFromCursor(Cursor c) {
		long id = c.getLong(c.getColumnIndex(DrawerTable._ID));
		String name = c.getString(c.getColumnIndex(DrawerTable.COLUMN_NAME_NAME));
		int locIndex = c.getInt(c.getColumnIndex(DrawerTable.COLUMN_NAME_LOC_INDEX));
		long parentId = c.getLong(c.getColumnIndex(DrawerTable.COLUMN_NAME_PARENT_ID));
		long creatorId = c.getLong(c.getColumnIndex(DrawerTable.COLUMN_NAME_CREATOR_ID));

		Drawer drawer = new Drawer(name, locIndex, parentId, creatorId);
		drawer.setId(id);
		
		return drawer;
	}
}
