package com.findit.android.data;

import com.findit.android.dao.FindItContract.ItemTable;

import android.database.Cursor;

public class SingleItem extends DrawerItem {
	
	public SingleItem(String name, long parentId, long creatorId) {
		super(name, ItemType.SINGLE_ITEM, parentId, creatorId);
	}
	
	public static SingleItem createSingleItemFromCursor(Cursor c) {
		long id = c.getLong(c.getColumnIndex(ItemTable._ID));
		String name = c.getString(c.getColumnIndex(ItemTable.COLUMN_NAME_NAME));
		long parentId = c.getLong(c.getColumnIndex(ItemTable.COLUMN_NAME_PARENT_ID));
		long creatorId = c.getLong(c.getColumnIndex(ItemTable.COLUMN_NAME_CREATOR_ID));

		SingleItem singleItem = new SingleItem(name, parentId, creatorId);
		singleItem.setId(id);
		
		return singleItem;
	}
}
