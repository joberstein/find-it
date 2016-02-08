package com.findit.android.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.findit.android.data.Furniture;
import com.findit.android.db.FindItDbHelper;
import com.findit.android.db.FindItContract.FurnitureTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class FurnitureDaoImpl implements FurnitureDao {
	private FindItDbHelper dbHelper;
	private static FurnitureDao instance;
	private static final String[] ALL_COLUMNS = { 
		FurnitureTable._ID, 
		FurnitureTable.COLUMN_NAME_NAME, 
		FurnitureTable.COLUMN_NAME_WIDTH,
		FurnitureTable.COLUMN_NAME_HEIGHT, 
		FurnitureTable.COLUMN_NAME_CREATOR_ID 
	};

	private FurnitureDaoImpl(Context context) {
		this.dbHelper = FindItDbHelper.getInstance(context);
	}
	
	public static FurnitureDao getInstance(Context context) {
		return (instance == null) ? new FurnitureDaoImpl(context) : instance;
	}

	public Cursor getFurnitureById(long id) {
		String condition = FurnitureTable._ID + "=?";
		String[] whereValues = { Long.toString(id) };
		return this.dbHelper.get(FurnitureTable.TABLE_NAME, ALL_COLUMNS, condition, whereValues, "", "");
	}

	public Cursor getFurnitureByCreator(long creatorId) {
		String condition = FurnitureTable.COLUMN_NAME_CREATOR_ID + "=?";
		String[] whereValues = { Long.toString(creatorId) };
		return this.dbHelper.get(FurnitureTable.TABLE_NAME, ALL_COLUMNS, condition, whereValues, "", "");
	}

	public long saveFurniture(Furniture furniture) {
		ContentValues values = new ContentValues();
		values.put(FurnitureTable.COLUMN_NAME_NAME, furniture.getName());
		values.put(FurnitureTable.COLUMN_NAME_WIDTH, furniture.getWidth());
		values.put(FurnitureTable.COLUMN_NAME_HEIGHT, furniture.getHeight());
		values.put(FurnitureTable.COLUMN_NAME_CREATOR_ID, furniture.getCreatorId());
		return this.dbHelper.create(FurnitureTable.TABLE_NAME, values);
	}

	public void updateFurniture(Furniture furniture) {
		ContentValues values = new ContentValues();
		values.put(FurnitureTable.COLUMN_NAME_NAME, furniture.getName());
		values.put(FurnitureTable.COLUMN_NAME_WIDTH, furniture.getWidth());
		values.put(FurnitureTable.COLUMN_NAME_HEIGHT, furniture.getHeight());
		values.put(FurnitureTable.COLUMN_NAME_CREATOR_ID, furniture.getCreatorId());
		this.dbHelper.update(FurnitureTable.TABLE_NAME, furniture.getId(), values);
	}

	public void deleteFurniture(long id) {
		String[] projection = { FurnitureTable._ID };
		this.dbHelper.delete(FurnitureTable.TABLE_NAME, id, projection);
	}

	public Furniture toFurniture(Cursor c) {
		if (c != null) {
			c.moveToFirst();
		}
		return (c.isAfterLast()) ? null : cursorToFurniture(c);
	}
	
	public List<Furniture> toFurnitureList(Cursor c) {
		List<Furniture> data = new ArrayList<Furniture>();
		if (c != null) {
			c.moveToFirst();
		}
		
		while (!c.isAfterLast()) {
			data.add(cursorToFurniture(c));
			c.moveToNext();
		}

		return data;
	}
	
	private Furniture cursorToFurniture(Cursor c) {
		long id = c.getLong(c.getColumnIndex(FurnitureTable._ID));
		String name = c.getString(c.getColumnIndex(FurnitureTable.COLUMN_NAME_NAME));
		int width = c.getInt(c.getColumnIndex(FurnitureTable.COLUMN_NAME_WIDTH));
		int height = c.getInt(c.getColumnIndex(FurnitureTable.COLUMN_NAME_HEIGHT));
		long creatorId = c.getLong(c.getColumnIndex(FurnitureTable.COLUMN_NAME_CREATOR_ID));

		Furniture furniture = new Furniture(name, width, height, creatorId);
		furniture.setId(id);
		
		return furniture;
	}
}
