package com.findit.android.dao;

import android.provider.BaseColumns;

public final class FindItContract {
	
    public static final String[] CREATE_TABLES = {
        UserTable.CREATE_TABLE,
        FurnitureTable.CREATE_TABLE,
        DrawerTable.CREATE_TABLE,
        ItemTable.CREATE_TABLE
    };
    
    public static final String[] DELETE_TABLES = {
        UserTable.DELETE_TABLE,
        FurnitureTable.DELETE_TABLE,
        DrawerTable.DELETE_TABLE,
        ItemTable.DELETE_TABLE
    };

	public static final String _ID = "id";
    public static final String COMMA_SEP = ",";
    public static final String AUTO_INCREMENT = " INTEGER PRIMARY KEY AUTOINCREMENT";
	private static final String TEXT_TYPE = " TEXT";
    
	/* Inner class that defines the table contents */
	public static abstract class UserTable implements BaseColumns {
		public static final String TABLE_NAME = "user";
		public static final String COLUMN_NAME_EMAIL = "email";
		public static final String COLUMN_NAME_USERNAME = "username";
		public static final String COLUMN_NAME_PASSWORD = "password";
		
		private static final String CREATE_TABLE =
		    "CREATE TABLE " + UserTable.TABLE_NAME + " (" +
		    _ID + AUTO_INCREMENT + COMMA_SEP +
		    UserTable.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
		    UserTable.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
		    UserTable.COLUMN_NAME_PASSWORD + TEXT_TYPE +
		    " )";

		private static final String DELETE_TABLE =
		    "DROP TABLE IF EXISTS " + UserTable.TABLE_NAME;
		
		public static String createTable() {
			return CREATE_TABLE;
		}
		
		public static String deleteTable() {
			return DELETE_TABLE;
		}
	}
	
	public static abstract class FurnitureTable implements BaseColumns {
		public static final String TABLE_NAME = "furniture";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_WIDTH = "width";
		public static final String COLUMN_NAME_HEIGHT = "height";
		public static final String COLUMN_NAME_CREATOR_ID = "creator_id";
		
		private static final String CREATE_TABLE =
		    "CREATE TABLE " + FurnitureTable.TABLE_NAME + " (" +
		    _ID + AUTO_INCREMENT + COMMA_SEP +
		    FurnitureTable.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
		    FurnitureTable.COLUMN_NAME_WIDTH + " INTEGER" + COMMA_SEP +
		    FurnitureTable.COLUMN_NAME_HEIGHT + " INTEGER" + COMMA_SEP +
		    FurnitureTable.COLUMN_NAME_CREATOR_ID + " INTEGER NOT NULL" + COMMA_SEP +
		    " FOREIGN KEY(" + FurnitureTable.COLUMN_NAME_CREATOR_ID + ") REFERENCES " + UserTable.TABLE_NAME + "(" + UserTable._ID + ") ON DELETE CASCADE" +
		    " )";

		private static final String DELETE_TABLE =
		    "DROP TABLE IF EXISTS " + FurnitureTable.TABLE_NAME;
		
		public static String createTable() {
			return CREATE_TABLE;
		}
		
		public static String deleteTable() {
			return DELETE_TABLE;
		}
	}
	
	public static abstract class DrawerTable implements BaseColumns {
		public static final String TABLE_NAME = "drawer";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_LOC_INDEX = "loc_index";
		public static final String COLUMN_NAME_PARENT_ID = "parent_id";
		public static final String COLUMN_NAME_CREATOR_ID = "creator_id";
		
		private static final String CREATE_TABLE =
		    "CREATE TABLE " + DrawerTable.TABLE_NAME + " (" +
		    _ID + AUTO_INCREMENT + COMMA_SEP +
		    DrawerTable.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
		    DrawerTable.COLUMN_NAME_LOC_INDEX + " INTEGER" + COMMA_SEP +
		    DrawerTable.COLUMN_NAME_PARENT_ID + " INTEGER" + COMMA_SEP +
		    DrawerTable.COLUMN_NAME_CREATOR_ID + " INTEGER NOT NULL" + COMMA_SEP +
		    " FOREIGN KEY(" + DrawerTable.COLUMN_NAME_CREATOR_ID + ") REFERENCES " + UserTable.TABLE_NAME + "(" + UserTable._ID + ") ON DELETE CASCADE" + COMMA_SEP +
		    " FOREIGN KEY(" + DrawerTable.COLUMN_NAME_PARENT_ID + ") REFERENCES " + FurnitureTable.TABLE_NAME + "(" + FurnitureTable._ID + ") ON DELETE CASCADE" +
		    " )";

		private static final String DELETE_TABLE =
		    "DROP TABLE IF EXISTS " + DrawerTable.TABLE_NAME;
		
		public static String createTable() {
			return CREATE_TABLE;
		}
		
		public static String deleteTable() {
			return DELETE_TABLE;
		}
	}
	
	public static abstract class ItemTable implements BaseColumns {
		public static final String TABLE_NAME = "item";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_TYPE = "type";
		public static final String COLUMN_NAME_DRAWER_ID = "drawer_id";
		public static final String COLUMN_NAME_PARENT_ID = "parent_id";
		public static final String COLUMN_NAME_CREATOR_ID = "creator_id";
		
		private static final String CREATE_TABLE =
		    "CREATE TABLE " + ItemTable.TABLE_NAME + " (" +
		    _ID + AUTO_INCREMENT + COMMA_SEP +
		    ItemTable.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
		    ItemTable.COLUMN_NAME_TYPE + " INTEGER" + COMMA_SEP +
		    ItemTable.COLUMN_NAME_DRAWER_ID + " INTEGER" + COMMA_SEP +
		    ItemTable.COLUMN_NAME_PARENT_ID + " INTEGER" + COMMA_SEP +
		    ItemTable.COLUMN_NAME_CREATOR_ID + " INTEGER NOT NULL" + COMMA_SEP +
		    " FOREIGN KEY(" + ItemTable.COLUMN_NAME_DRAWER_ID + ") REFERENCES " + DrawerTable.TABLE_NAME + "(" + DrawerTable._ID + ") ON DELETE CASCADE" + COMMA_SEP +
		    " FOREIGN KEY(" + ItemTable.COLUMN_NAME_CREATOR_ID + ") REFERENCES " + UserTable.TABLE_NAME + "(" + UserTable._ID + ") ON DELETE CASCADE" +
		    " )";

		private static final String DELETE_TABLE =
		    "DROP TABLE IF EXISTS " + ItemTable.TABLE_NAME;
		
		public static String createTable() {
			return CREATE_TABLE;
		}
		
		public static String deleteTable() {
			return DELETE_TABLE;
		}
	}
}
