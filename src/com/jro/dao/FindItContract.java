package com.jro.dao;

import android.provider.BaseColumns;

public final class FindItContract {
	// To prevent someone from accidentally instantiating the contract class,
	// give it an empty constructor.
	public FindItContract() {}
	
    public static final String[] CREATE_TABLES = {
        UserTable.CREATE_TABLE,
        FurnitureTable.CREATE_TABLE,
        ItemTypeTable.CREATE_TABLE,
        ItemTable.CREATE_TABLE
    };
    
    public static final String[] DELETE_TABLES = {
        UserTable.DELETE_TABLE,
        FurnitureTable.DELETE_TABLE,
        ItemTypeTable.DELETE_TABLE,
        ItemTable.DELETE_TABLE
    };
    
	/* Inner class that defines the table contents */
	public static abstract class UserTable implements BaseColumns {
		public static final String TABLE_NAME = "user";
		public static final String _ID = "id";
		public static final String COLUMN_NAME_EMAIL = "email";
		public static final String COLUMN_NAME_USERNAME = "username";
		public static final String COLUMN_NAME_PASSWORD = "password";
		
		private static final String TEXT_TYPE = " TEXT";
		private static final String COMMA_SEP = ",";
		private static final String CREATE_TABLE =
		    "CREATE TABLE " + UserTable.TABLE_NAME + " (" +
		    UserTable._ID + " INTEGER NOT NULL PRIMARY KEY" + COMMA_SEP +
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
		public static final String _ID = "id";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_WIDTH = "width";
		public static final String COLUMN_NAME_HEIGHT = "height";
		public static final String COLUMN_NAME_CREATOR_ID = "creator_id";
		
		private static final String TEXT_TYPE = " TEXT";
		private static final String COMMA_SEP = ",";
		private static final String CREATE_TABLE =
		    "CREATE TABLE " + FurnitureTable.TABLE_NAME + " (" +
		    FurnitureTable._ID + " INTEGER NOT NULL PRIMARY KEY" + COMMA_SEP +
		    FurnitureTable.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
		    FurnitureTable.COLUMN_NAME_WIDTH + " INTEGER" + COMMA_SEP +
		    FurnitureTable.COLUMN_NAME_HEIGHT + " INTEGER" + COMMA_SEP +
		    FurnitureTable.COLUMN_NAME_CREATOR_ID + " INTEGER NOT NULL" + COMMA_SEP +
		    " FOREIGN KEY(" + FurnitureTable.COLUMN_NAME_CREATOR_ID + ") REFERENCES " + UserTable.TABLE_NAME + "(" + UserTable._ID + ")" +
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
	
	public static abstract class ItemTable implements BaseColumns {
		public static final String TABLE_NAME = "item";
		public static final String _ID = "id";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_TYPE = "type";
		public static final String COLUMN_NAME_PARENT_ID = "parent_id";
		public static final String COLUMN_NAME_CREATOR_ID = "creator_id";
		
		private static final String TEXT_TYPE = " TEXT";
		private static final String COMMA_SEP = ",";
		private static final String CREATE_TABLE =
		    "CREATE TABLE " + ItemTable.TABLE_NAME + " (" +
		    ItemTable._ID + " INTEGER NOT NULL PRIMARY KEY" + COMMA_SEP +
		    ItemTable.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
		    ItemTable.COLUMN_NAME_TYPE + " INTEGER" + COMMA_SEP +
		    ItemTable.COLUMN_NAME_PARENT_ID + " INTEGER" + COMMA_SEP +
		    ItemTable.COLUMN_NAME_CREATOR_ID + " INTEGER NOT NULL" + COMMA_SEP +
		    " FOREIGN KEY(" + ItemTable.COLUMN_NAME_CREATOR_ID + ") REFERENCES " + UserTable.TABLE_NAME + "(" + UserTable._ID + ")" + COMMA_SEP +
		    " FOREIGN KEY(" + ItemTable.COLUMN_NAME_TYPE + ") REFERENCES " + ItemTypeTable.TABLE_NAME + "(" + ItemTypeTable._ID + ")" +
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
	
	public static abstract class ItemTypeTable implements BaseColumns {
		public static final String TABLE_NAME = "item_type";
		public static final String _ID = "id";
		public static final String COLUMN_NAME_TYPE = "type";
		
		private static final String TEXT_TYPE = " TEXT";
		private static final String COMMA_SEP = ",";
		private static final String CREATE_TABLE =
		    "CREATE TABLE " + ItemTypeTable.TABLE_NAME + " (" +
		    ItemTypeTable._ID + " INTEGER NOT NULL PRIMARY KEY" + COMMA_SEP +
		    ItemTypeTable.COLUMN_NAME_TYPE + TEXT_TYPE + 
		    " )";

		private static final String DELETE_TABLE =
		    "DROP TABLE IF EXISTS " + ItemTypeTable.TABLE_NAME;
		
		public static String createTable() {
			return CREATE_TABLE;
		}
		
		public static String deleteTable() {
			return DELETE_TABLE;
		}
		
		public static String[] insertValues() {
			ItemType[] typeValues = ItemType.values();
			String[] insertTypeValues = new String[typeValues.length];
			for (int i = 0; i < typeValues.length; i++) {
				insertTypeValues[i] = "INSERT INTO " + ItemTypeTable.TABLE_NAME + " (" + ItemTypeTable.COLUMN_NAME_TYPE + ") " +
									  "VALUES (\"" + typeValues[i].toString() + "\")";
			}
			return insertTypeValues;
		}
	}
}
