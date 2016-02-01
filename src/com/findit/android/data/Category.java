package com.findit.android.data;

import java.util.ArrayList;
import java.util.List;

import com.findit.android.dao.FindItContract.ItemTable;

import android.database.Cursor;

public class Category extends DrawerItem {
	List<SingleItem> items;
	
	Category(String name, long drawerId, long parentId, long creatorId) {
		super(name, ItemType.CATEGORY, drawerId, parentId, creatorId);
		this.items = new ArrayList<>();
	}
	
	public void addItem(SingleItem item) {
		items.add(item);
	}
	
	public void removeItem(SingleItem item) {
		items.remove(item);
	}
	
	public List<SingleItem> getItems() {
		return this.items;
	}
	
	public void setItems(List<SingleItem> items) {
		this.items = items;
	}
	
	public static Category createCategoryFromCursor(Cursor c) {
		long id = c.getLong(c.getColumnIndex(ItemTable._ID));
		String name = c.getString(c.getColumnIndex(ItemTable.COLUMN_NAME_NAME));
		long drawerId = c.getLong(c.getColumnIndex(ItemTable.COLUMN_NAME_DRAWER_ID));
		long parentId = c.getLong(c.getColumnIndex(ItemTable.COLUMN_NAME_PARENT_ID));
		long creatorId = c.getLong(c.getColumnIndex(ItemTable.COLUMN_NAME_CREATOR_ID));

		Category category = new Category(name, drawerId, parentId, creatorId);
		category.setId(id);
		
		return category;
	}
	
	public DrawerItem createSingleItem(String name) {
		return new SingleItem(name, this.getParentId(), this.getId(), this.getCreatorId());
	}
}

