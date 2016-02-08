package com.findit.android.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.findit.android.data.Drawer;
import com.findit.android.db.FindItContract.DrawerItemTable;
import com.findit.android.db.FindItContract.DrawerTable;
import com.findit.android.db.FindItDbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DrawerDaoImpl implements DrawerDao {
	private FindItDbHelper dbHelper;
	private static DrawerDao instance;
	private static final String[] ALL_COLUMNS = {
		DrawerTable._ID,
		DrawerTable.COLUMN_NAME_NAME,
		DrawerTable.COLUMN_NAME_LOC_INDEX,
		DrawerTable.COLUMN_NAME_PARENT_ID,
		DrawerTable.COLUMN_NAME_CREATOR_ID
	};

	private DrawerDaoImpl(Context context) {
		this.dbHelper = FindItDbHelper.getInstance(context);
	}
	
	public static DrawerDao getInstance(Context context) {
		return (instance == null) ? new DrawerDaoImpl(context) : instance;
	}

	public Cursor getDrawerById(long id) {
		String condition = DrawerItemTable._ID + "=?";
		String[] whereValues = { Long.toString(id) };
		return this.dbHelper.get(DrawerTable.TABLE_NAME, ALL_COLUMNS, condition, whereValues, "", "");
	}
	
	public Cursor getDrawersByFurniture(long parentId) {
		String condition = DrawerTable.COLUMN_NAME_PARENT_ID + "=?";
		String[] whereValues = { Long.toString(parentId) };
		return this.dbHelper.get(DrawerTable.TABLE_NAME, ALL_COLUMNS, condition, whereValues, "", "");
	}
	
	public Cursor getDrawersByCreator(long creatorId) {
		String condition = DrawerTable.COLUMN_NAME_CREATOR_ID + "=?";
		String[] whereValues = { Long.toString(creatorId) };
		return this.dbHelper.get(DrawerTable.TABLE_NAME, ALL_COLUMNS, condition, whereValues, "", "");
	}
	
	public long saveDrawer(Drawer drawer) {
		ContentValues values = new ContentValues();
		values.put(DrawerTable.COLUMN_NAME_NAME, drawer.getName());
		values.put(DrawerTable.COLUMN_NAME_LOC_INDEX, drawer.getLocIndex());
		values.put(DrawerTable.COLUMN_NAME_PARENT_ID, drawer.getParentId());
		values.put(DrawerTable.COLUMN_NAME_CREATOR_ID, drawer.getCreatorId());
		return this.dbHelper.create(DrawerTable.TABLE_NAME, values);
	}
	
	public void updateDrawer(Drawer drawer) {
		ContentValues values = new ContentValues();
		values.put(DrawerTable.COLUMN_NAME_NAME, drawer.getName());
		values.put(DrawerTable.COLUMN_NAME_LOC_INDEX, drawer.getLocIndex());
		values.put(DrawerTable.COLUMN_NAME_PARENT_ID, drawer.getParentId());
		values.put(DrawerTable.COLUMN_NAME_CREATOR_ID, drawer.getCreatorId());
		this.dbHelper.update(DrawerTable.TABLE_NAME, drawer.getId(), values);
	}
	
	public void deleteDrawer(long id) {
		String[] projection = { DrawerTable._ID };
		this.dbHelper.delete(DrawerTable.TABLE_NAME, id, projection);
	}
	

	
	public Drawer toDrawer(Cursor c) {
		if (c != null) {
			c.moveToFirst();
		}
		return (c.isAfterLast()) ? null : cursorToDrawer(c);
	}
	
	public List<Drawer> toDrawerList(Cursor c) {
		List<Drawer> data = new ArrayList<>();
		if (c != null) {
			c.moveToFirst();
		}
		
		while (!c.isAfterLast()) {
			data.add(cursorToDrawer(c));
			c.moveToNext();
		}

		return data;
	}
	
	private Drawer cursorToDrawer(Cursor c) {
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
