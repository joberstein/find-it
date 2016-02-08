package com.findit.android.db;

import java.util.ArrayList;

import com.findit.android.db.FindItContract.FurnitureTable;

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
	
	public Cursor get(String tableName, String[] projection, String cols, String[] vals, String group, String filter) {
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
	
	public long create(String tableName, ContentValues values) {
		SQLiteDatabase db = getWritableDatabase();
		long newRowId = db.insert(tableName, null, values);
		return newRowId;
	}
	
	public void update(String tableName, long id, ContentValues values) {
		SQLiteDatabase db = getReadableDatabase();
		String selection = FurnitureTable._ID + " =?";
		String[] selectionArgs = { Long.toString(id) };
		db.update(tableName, values, selection, selectionArgs);
	}
	
	public void delete(String tableName, long id, String[] projection) {
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
