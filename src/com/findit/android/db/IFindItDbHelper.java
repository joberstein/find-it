package com.findit.android.db;

import android.content.ContentValues;
import android.database.Cursor;

public interface IFindItDbHelper {
	
	public Cursor get(String tableName, String[] projection, String cols, String[] vals, String group, String filter);
	
	public long create(String tableName, ContentValues values);
	
	public void update(String tableName, long id, ContentValues values);
	
	public void delete(String tableName, long id, String[] projection);
}
