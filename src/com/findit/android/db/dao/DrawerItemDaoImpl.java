package com.findit.android.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.findit.android.data.Category;
import com.findit.android.data.ItemType;
import com.findit.android.data.SingleItem;
import com.findit.android.data.abstraction.IDrawerItem;
import com.findit.android.db.FindItContract.DrawerItemTable;
import com.findit.android.db.FindItContract.FurnitureTable;
import com.findit.android.db.FindItDbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DrawerItemDaoImpl implements DrawerItemDao {
	private FindItDbHelper dbHelper;
	private static DrawerItemDao instance;
	private static final String[] ALL_COLUMNS = {
		DrawerItemTable._ID,
		DrawerItemTable.COLUMN_NAME_NAME,
		DrawerItemTable.COLUMN_NAME_TYPE,
		DrawerItemTable.COLUMN_NAME_DRAWER_ID,
		DrawerItemTable.COLUMN_NAME_PARENT_ID,
		DrawerItemTable.COLUMN_NAME_CREATOR_ID
	};
	
	private DrawerItemDaoImpl(Context context) {
		this.dbHelper = FindItDbHelper.getInstance(context);
	}
	
	public static DrawerItemDao getInstance(Context context) {
		return (instance == null) ? new DrawerItemDaoImpl(context) : instance;
	}

	public Cursor getDrawerItemById(long id) {
		String condition = DrawerItemTable._ID + "=?";
		String[] whereValues = { Long.toString(id) };
		return this.dbHelper.get(DrawerItemTable.TABLE_NAME, ALL_COLUMNS, condition, whereValues, "", "");
	}
	
	public Cursor getDrawerItemsByParent(long parentId) {
		String condition = DrawerItemTable.COLUMN_NAME_PARENT_ID + "=?";
		String[] whereValues = { Long.toString(parentId) };
		return this.dbHelper.get(DrawerItemTable.TABLE_NAME, ALL_COLUMNS, condition, whereValues, "", "");
	}
	
	public Cursor getDrawerItemsByCreator(long creatorId) {
		String condition = DrawerItemTable.COLUMN_NAME_CREATOR_ID + "=?";
		String[] whereValues = { Long.toString(creatorId) };
		return this.dbHelper.get(DrawerItemTable.TABLE_NAME, ALL_COLUMNS, condition, whereValues, "", "");
	}
	
	public long saveDrawerItem(IDrawerItem item) {
		ContentValues values = new ContentValues();
		values.put(DrawerItemTable.COLUMN_NAME_NAME, item.getName());
		values.put(DrawerItemTable.COLUMN_NAME_TYPE, item.getType().name());
		values.put(DrawerItemTable.COLUMN_NAME_DRAWER_ID, item.getDrawerId());
		values.put(DrawerItemTable.COLUMN_NAME_PARENT_ID, item.getParentId());
		values.put(DrawerItemTable.COLUMN_NAME_CREATOR_ID, item.getCreatorId());
		return this.dbHelper.create(DrawerItemTable.TABLE_NAME, values);
	}
	
	public void updateDrawerItem(IDrawerItem item) {
		ContentValues values = new ContentValues();
		values.put(DrawerItemTable.COLUMN_NAME_NAME, item.getName());
		values.put(DrawerItemTable.COLUMN_NAME_TYPE, item.getType().name());
		values.put(DrawerItemTable.COLUMN_NAME_DRAWER_ID, item.getDrawerId());
		values.put(DrawerItemTable.COLUMN_NAME_PARENT_ID, item.getParentId());
		values.put(DrawerItemTable.COLUMN_NAME_CREATOR_ID, item.getCreatorId());
		this.dbHelper.update(FurnitureTable.TABLE_NAME, item.getId(), values);
	}
	
	public void deleteDrawerItem(long id) {
		String[] projection = { DrawerItemTable._ID };
		this.dbHelper.delete(DrawerItemTable.TABLE_NAME, id, projection);
	}
	

	public SingleItem toSingleItem(Cursor c) {
		if (c != null) {
			c.moveToFirst();
		}
		return (c.isAfterLast()) ? null : cursorToSingleItem(c);
	}
	
	public Category toCategory(Cursor c) {
		if (c != null) {
			c.moveToFirst();
		}
		return (c.isAfterLast()) ? null : cursorToCategory(c);
	}
	
	public List<IDrawerItem> toDrawerItemList(Cursor c) {
		List<IDrawerItem> data = new ArrayList<>();
		if (c != null) {
			c.moveToFirst();
		}
		while (!c.isAfterLast()) {
			String storedItemType = c.getString(c.getColumnIndex(DrawerItemTable.COLUMN_NAME_TYPE));
			if (storedItemType.equals(ItemType.CATEGORY.name())) {
				data.add(cursorToCategory(c));
			}
			else {
				data.add(cursorToSingleItem(c));
			}
			c.moveToNext();
		}

		return data;
	}
	
	private Category cursorToCategory(Cursor c) {
		long id = c.getLong(c.getColumnIndex(DrawerItemTable._ID));
		String name = c.getString(c.getColumnIndex(DrawerItemTable.COLUMN_NAME_NAME));
		long drawerId = c.getLong(c.getColumnIndex(DrawerItemTable.COLUMN_NAME_DRAWER_ID));
		long parentId = c.getLong(c.getColumnIndex(DrawerItemTable.COLUMN_NAME_PARENT_ID));
		long creatorId = c.getLong(c.getColumnIndex(DrawerItemTable.COLUMN_NAME_CREATOR_ID));

		Category category = new Category(name, drawerId, parentId, creatorId);
		category.setId(id);
		
		return category;
	}
	
	private SingleItem cursorToSingleItem(Cursor c) {
		long id = c.getLong(c.getColumnIndex(DrawerItemTable._ID));
		String name = c.getString(c.getColumnIndex(DrawerItemTable.COLUMN_NAME_NAME));
		long drawerId = c.getLong(c.getColumnIndex(DrawerItemTable.COLUMN_NAME_DRAWER_ID));
		long parentId = c.getLong(c.getColumnIndex(DrawerItemTable.COLUMN_NAME_PARENT_ID));
		long creatorId = c.getLong(c.getColumnIndex(DrawerItemTable.COLUMN_NAME_CREATOR_ID));

		SingleItem singleItem = new SingleItem(name, drawerId, parentId, creatorId);
		singleItem.setId(id);
		
		return singleItem;
	}
}
