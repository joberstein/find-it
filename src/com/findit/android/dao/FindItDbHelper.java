package com.findit.android.dao;

import java.util.ArrayList;

import com.findit.android.dao.FindItContract.DrawerTable;
import com.findit.android.dao.FindItContract.FurnitureTable;
import com.findit.android.dao.FindItContract.ItemTable;
import com.findit.android.dao.FindItContract.UserTable;
import com.findit.android.data.Drawer;
import com.findit.android.data.DrawerItem;
import com.findit.android.data.Furniture;
import com.findit.android.data.User;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FindItDbHelper extends SQLiteOpenHelper implements IFindItDbHelper {
	// If you change the database schema, you must increment the database version. 
	public static final int DATABASE_VERSION = 4;
	public static final String DATABASE_NAME = "FindIt.db";
	private static FindItDbHelper db;

	// change to private for prod
	private FindItDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static synchronized FindItDbHelper getInstance(Context context) {
		if (db == null) {
			db = new FindItDbHelper(context.getApplicationContext()); 
		}
		return db;
	}

	@SuppressLint("NewApi")
	@Override
	public void onConfigure(SQLiteDatabase db){
	    db.setForeignKeyConstraintsEnabled(true);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		for (String createTableScript : FindItContract.CREATE_TABLES) {
			db.execSQL(createTableScript);
		}
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy is
		// to simply to discard the data and start over
		for (String deleteTableScript : FindItContract.DELETE_TABLES) {
			db.execSQL(deleteTableScript);
		}
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
	
	public void createTables(Context context) {
		for (String createTableScript : FindItContract.CREATE_TABLES) {
			getWritableDatabase().execSQL(createTableScript);
		}
		close();
	}
	
	public void deleteTables(Context context) {
		for (String deleteTableScript : FindItContract.DELETE_TABLES) {
			getWritableDatabase().execSQL(deleteTableScript);
		}
		close();
	}
	
	
	/********************************************************************************
	 * USER TABLE
	 ********************************************************************************/
	public Cursor getUsernames() {
		String[] projection = { UserTable.COLUMN_NAME_USERNAME };
		return this.get(UserTable.TABLE_NAME, projection, null, null, "", "");
	}

	public Cursor getUser(long userId) {
		String[] projection = {
				UserTable._ID,
				UserTable.COLUMN_NAME_USERNAME,
				UserTable.COLUMN_NAME_PASSWORD
		};
		String[] whereValues = { Long.toString(userId) };
		return this.get(UserTable.TABLE_NAME, projection, projection[0] + "=?", whereValues, "", "");
	}
	
	public Cursor getUserByUsername(String usernameMatch) {
		String[] projection = {
				UserTable._ID,
				UserTable.COLUMN_NAME_USERNAME,
				UserTable.COLUMN_NAME_PASSWORD
		};
		String[] whereValues = { usernameMatch };
		return this.get(UserTable.TABLE_NAME, projection, projection[1] + "=?", whereValues, "", "");
	}

	public long saveUser(User user) {
		ContentValues values = new ContentValues();
		values.put(UserTable.COLUMN_NAME_EMAIL, user.getEmail());
		values.put(UserTable.COLUMN_NAME_USERNAME, user.getUsername());
		values.put(UserTable.COLUMN_NAME_PASSWORD, user.getPassword());
		return this.create(UserTable.TABLE_NAME, values);
	}
	
	public void updateUser(User user) {
		ContentValues values = new ContentValues();
		values.put(UserTable.COLUMN_NAME_EMAIL, user.getEmail());
		values.put(UserTable.COLUMN_NAME_USERNAME, user.getUsername());
		values.put(UserTable.COLUMN_NAME_PASSWORD, user.getPassword());
		this.update(UserTable.TABLE_NAME, user.getId(), values);
	}
	
	public void deleteUser(long id) {
		String[] projection = { UserTable._ID };
		this.delete(UserTable.TABLE_NAME, id, projection);
	}
	

	/********************************************************************************
	 * FURNITURE TABLE
	 ********************************************************************************/
	public Cursor getFurnitureById(long id) {
		String[] projection = {
			FurnitureTable._ID,
			FurnitureTable.COLUMN_NAME_NAME,
			FurnitureTable.COLUMN_NAME_WIDTH,
			FurnitureTable.COLUMN_NAME_HEIGHT,
			FurnitureTable.COLUMN_NAME_CREATOR_ID
		};
		String condition = FurnitureTable._ID + "=?";
		String[] whereValues = { Long.toString(id) };
		return this.get(FurnitureTable.TABLE_NAME, projection, condition, whereValues, "", "");
	}
	
	public Cursor getFurnitureByCreator(long creatorId) {
		String[] projection = {
			FurnitureTable._ID,
			FurnitureTable.COLUMN_NAME_NAME,
			FurnitureTable.COLUMN_NAME_WIDTH,
			FurnitureTable.COLUMN_NAME_HEIGHT,
			FurnitureTable.COLUMN_NAME_CREATOR_ID
		};
		String condition = FurnitureTable.COLUMN_NAME_CREATOR_ID + "=?";
		String[] whereValues = { Long.toString(creatorId) };
		return this.get(FurnitureTable.TABLE_NAME, projection, condition, whereValues, "", "");
	}
	
	public long saveFurniture(Furniture furniture) {
		ContentValues values = new ContentValues();
		values.put(FurnitureTable.COLUMN_NAME_NAME, furniture.getName());
		values.put(FurnitureTable.COLUMN_NAME_WIDTH, furniture.getWidth());
		values.put(FurnitureTable.COLUMN_NAME_HEIGHT, furniture.getHeight());
		values.put(FurnitureTable.COLUMN_NAME_CREATOR_ID, furniture.getCreatorId());
		return this.create(FurnitureTable.TABLE_NAME, values);
	}
	
	public void updateFurniture(Furniture furniture) {
		ContentValues values = new ContentValues();
		values.put(FurnitureTable.COLUMN_NAME_NAME, furniture.getName());
		values.put(FurnitureTable.COLUMN_NAME_WIDTH, furniture.getWidth());
		values.put(FurnitureTable.COLUMN_NAME_HEIGHT, furniture.getHeight());
		values.put(FurnitureTable.COLUMN_NAME_CREATOR_ID, furniture.getCreatorId());
		this.update(FurnitureTable.TABLE_NAME, furniture.getId(), values);
	}
	
	public void deleteFurniture(long id) {
		String[] projection = { FurnitureTable._ID };
		this.delete(FurnitureTable.TABLE_NAME, id, projection);
	}
	
	/********************************************************************************
	 * DRAWER TABLE
	 ********************************************************************************/
	public Cursor getDrawerById(long id) {
		String[] projection = {
			DrawerTable._ID,
			DrawerTable.COLUMN_NAME_NAME,
			DrawerTable.COLUMN_NAME_LOC_INDEX,
			DrawerTable.COLUMN_NAME_PARENT_ID,
			DrawerTable.COLUMN_NAME_CREATOR_ID
		};
		String condition = ItemTable._ID + "=?";
		String[] whereValues = { Long.toString(id) };
		return this.get(DrawerTable.TABLE_NAME, projection, condition, whereValues, "", "");
	}
	
	public Cursor getDrawersByFurniture(long parentId) {
		String[] projection = {
			DrawerTable._ID,
			DrawerTable.COLUMN_NAME_NAME,
			DrawerTable.COLUMN_NAME_LOC_INDEX,
			DrawerTable.COLUMN_NAME_PARENT_ID,
			DrawerTable.COLUMN_NAME_CREATOR_ID
		};
		String condition = DrawerTable.COLUMN_NAME_PARENT_ID + "=?";
		String[] whereValues = { Long.toString(parentId) };
		return this.get(DrawerTable.TABLE_NAME, projection, condition, whereValues, "", "");
	}
	
	public Cursor getDrawersByCreator(long creatorId) {
		String[] projection = {
			DrawerTable._ID,
			DrawerTable.COLUMN_NAME_NAME,
			DrawerTable.COLUMN_NAME_LOC_INDEX,
			DrawerTable.COLUMN_NAME_PARENT_ID,
			DrawerTable.COLUMN_NAME_CREATOR_ID
		};
		String condition = DrawerTable.COLUMN_NAME_CREATOR_ID + "=?";
		String[] whereValues = { Long.toString(creatorId) };
		return this.get(DrawerTable.TABLE_NAME, projection, condition, whereValues, "", "");
	}
	
	public long saveDrawer(Drawer drawer) {
		ContentValues values = new ContentValues();
		values.put(DrawerTable.COLUMN_NAME_NAME, drawer.getName());
		values.put(DrawerTable.COLUMN_NAME_LOC_INDEX, drawer.getLocIndex());
		values.put(DrawerTable.COLUMN_NAME_PARENT_ID, drawer.getParentId());
		values.put(DrawerTable.COLUMN_NAME_CREATOR_ID, drawer.getCreatorId());
		return this.create(DrawerTable.TABLE_NAME, values);
	}
	
	public void updateDrawer(Drawer drawer) {
		ContentValues values = new ContentValues();
		values.put(DrawerTable.COLUMN_NAME_NAME, drawer.getName());
		values.put(DrawerTable.COLUMN_NAME_LOC_INDEX, drawer.getLocIndex());
		values.put(DrawerTable.COLUMN_NAME_PARENT_ID, drawer.getParentId());
		values.put(DrawerTable.COLUMN_NAME_CREATOR_ID, drawer.getCreatorId());
		this.update(DrawerTable.TABLE_NAME, drawer.getId(), values);
	}
	
	public void deleteDrawer(long id) {
		String[] projection = { DrawerTable._ID };
		this.delete(DrawerTable.TABLE_NAME, id, projection);
	}
	
	
	/********************************************************************************
	 * ITEM TABLE
	 ********************************************************************************/
	public Cursor getItemById(long id) {
		String[] projection = {
			ItemTable._ID,
			ItemTable.COLUMN_NAME_NAME,
			ItemTable.COLUMN_NAME_TYPE,
			ItemTable.COLUMN_NAME_DRAWER_ID,
			ItemTable.COLUMN_NAME_PARENT_ID,
			ItemTable.COLUMN_NAME_CREATOR_ID
		};
		String condition = ItemTable._ID + "=?";
		String[] whereValues = { Long.toString(id) };
		return this.get(ItemTable.TABLE_NAME, projection, condition, whereValues, "", "");
	}
	
	public Cursor getItemsByParent(long parentId) {
		String[] projection = {
			ItemTable._ID,
			ItemTable.COLUMN_NAME_NAME,
			ItemTable.COLUMN_NAME_TYPE,
			ItemTable.COLUMN_NAME_DRAWER_ID,
			ItemTable.COLUMN_NAME_PARENT_ID,
			ItemTable.COLUMN_NAME_CREATOR_ID
		};
		String condition = ItemTable.COLUMN_NAME_PARENT_ID + "=?";
		String[] whereValues = { Long.toString(parentId) };
		return this.get(ItemTable.TABLE_NAME, projection, condition, whereValues, "", "");
	}
	
	public Cursor getItemsByCreator(long creatorId) {
		String[] projection = {
			ItemTable._ID,
			ItemTable.COLUMN_NAME_NAME,
			ItemTable.COLUMN_NAME_TYPE,
			ItemTable.COLUMN_NAME_DRAWER_ID,
			ItemTable.COLUMN_NAME_PARENT_ID,
			ItemTable.COLUMN_NAME_CREATOR_ID
		};
		String condition = ItemTable.COLUMN_NAME_CREATOR_ID + "=?";
		String[] whereValues = { Long.toString(creatorId) };
		return this.get(ItemTable.TABLE_NAME, projection, condition, whereValues, "", "");
	}
	
	public long saveItem(DrawerItem item) {
		ContentValues values = new ContentValues();
		values.put(ItemTable.COLUMN_NAME_NAME, item.getName());
		values.put(ItemTable.COLUMN_NAME_TYPE, item.getType().name());
		values.put(ItemTable.COLUMN_NAME_DRAWER_ID, item.getDrawerId());
		values.put(ItemTable.COLUMN_NAME_PARENT_ID, item.getParentId());
		values.put(ItemTable.COLUMN_NAME_CREATOR_ID, item.getCreatorId());
		return this.create(ItemTable.TABLE_NAME, values);
	}
	
	public void updateItem(DrawerItem item) {
		ContentValues values = new ContentValues();
		values.put(ItemTable.COLUMN_NAME_NAME, item.getName());
		values.put(ItemTable.COLUMN_NAME_TYPE, item.getType().name());
		values.put(ItemTable.COLUMN_NAME_DRAWER_ID, item.getDrawerId());
		values.put(ItemTable.COLUMN_NAME_PARENT_ID, item.getParentId());
		values.put(ItemTable.COLUMN_NAME_CREATOR_ID, item.getCreatorId());
		this.update(FurnitureTable.TABLE_NAME, item.getId(), values);
	}
	
	public void deleteItem(long id) {
		String[] projection = { ItemTable._ID };
		this.delete(ItemTable.TABLE_NAME, id, projection);
	}
	
	
	/********************************************************************************
	 * COMMON CRUD METHODS
	 ********************************************************************************/
	private Cursor get(String tableName, String[] projection, String cols, String[] vals, String group, String filter) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(
				tableName,  		// The table to query
				projection,             // The columns to return
				cols,  					 // The columns for the WHERE clause
				vals,            		// The values for the WHERE clause
				group,                     // don't group the rows
				filter,                     // don't filter by row groups
				"");
		
		return cursor;
	}
	
	private long create(String tableName, ContentValues values) {
		SQLiteDatabase db = getWritableDatabase();
		long newRowId = db.insert(tableName, null, values);
		return newRowId;
	}
	
	private void update(String tableName, long id, ContentValues values) {
		SQLiteDatabase db = getReadableDatabase();
		String selection = FurnitureTable._ID + " =?";
		String[] selectionArgs = { Long.toString(id) };
		db.update(tableName, values, selection, selectionArgs);
	}
	
	private void delete(String tableName, long id, String[] projection) {
		SQLiteDatabase db = getWritableDatabase();
		String[] whereValues = { Long.toString(id) };
		db.delete(tableName, projection[0] + "=?", whereValues);
	}
	
	
	
	
	
	
	/********************************************************************************
	 * FOR DATABASE VIEW ONLY
	 ********************************************************************************/
	public ArrayList<Cursor> getData(String Query){
		//get writable database
		SQLiteDatabase sqlDB = this.getWritableDatabase();
		String[] columns = new String[] { "mesage" };
		//an array list of cursor to save two cursors one has results from the query 
		//other cursor stores error message if any errors are triggered
		ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
		MatrixCursor Cursor2= new MatrixCursor(columns);
		alc.add(null);
		alc.add(null);
		
		
		try{
			String maxQuery = Query ;
			//execute the query results will be save in Cursor c
			Cursor c = sqlDB.rawQuery(maxQuery, null);
			

			//add value to cursor2
			Cursor2.addRow(new Object[] { "Success" });
			
			alc.set(1,Cursor2);
			if (null != c && c.getCount() > 0) {

				
				alc.set(0,c);
				c.moveToFirst();
				
				return alc ;
			}
			return alc;
		} catch(SQLException sqlEx){
			Log.d("printing exception", sqlEx.getMessage());
			//if any exceptions are triggered save the error message to cursor an return the arraylist
			Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
			alc.set(1,Cursor2);
			return alc;
		} catch(Exception ex){

			Log.d("printing exception", ex.getMessage());

			//if any exceptions are triggered save the error message to cursor an return the arraylist
			Cursor2.addRow(new Object[] { ""+ex.getMessage() });
			alc.set(1,Cursor2);
			return alc;
		}

		
	}
}
