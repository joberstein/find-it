package com.findit.android.db.dao;

import java.util.HashSet;
import java.util.Set;

import com.findit.android.data.User;
import com.findit.android.db.FindItContract.UserTable;
import com.findit.android.db.FindItDbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class UserDaoImpl implements UserDao {
	private FindItDbHelper dbHelper;
	private static UserDao instance;
	private static final String[] ALL_COLUMNS = {
			UserTable._ID,
			UserTable.COLUMN_NAME_EMAIL,
			UserTable.COLUMN_NAME_USERNAME,
			UserTable.COLUMN_NAME_PASSWORD
	};
	  
	private UserDaoImpl(Context context) {
		this.dbHelper = FindItDbHelper.getInstance(context);
	}
	
	public static UserDao getInstance(Context context) {
		return (instance == null) ? new UserDaoImpl(context) : instance;
	}

	public Cursor getUser(long userId) {
		String condition = UserTable._ID + "=?";
		String[] whereValues = { Long.toString(userId) };
		return this.dbHelper.get(UserTable.TABLE_NAME, ALL_COLUMNS, condition, whereValues, "", "");
	}
	
	public Cursor getUserByUsername(String username) {
		String condition = UserTable.COLUMN_NAME_USERNAME + "=? COLLATE NOCASE";
		String[] whereValues = { username };
		return this.dbHelper.get(UserTable.TABLE_NAME, ALL_COLUMNS, condition, whereValues, "", "");
	}

	public long saveUser(User user) {
		ContentValues values = new ContentValues();
		values.put(UserTable.COLUMN_NAME_EMAIL, user.getEmail());
		values.put(UserTable.COLUMN_NAME_USERNAME, user.getUsername());
		values.put(UserTable.COLUMN_NAME_PASSWORD, user.getPassword());
		return this.dbHelper.create(UserTable.TABLE_NAME, values);
	}
	
	public void updateUser(User user) {
		ContentValues values = new ContentValues();
		values.put(UserTable.COLUMN_NAME_EMAIL, user.getEmail());
		values.put(UserTable.COLUMN_NAME_USERNAME, user.getUsername());
		values.put(UserTable.COLUMN_NAME_PASSWORD, user.getPassword());
		this.dbHelper.update(UserTable.TABLE_NAME, user.getId(), values);
	}
	
	public void deleteUser(long id) {
		String[] projection = { UserTable._ID };
		this.dbHelper.delete(UserTable.TABLE_NAME, id, projection);
	}
	
	public User toUser(Cursor c) {
		if (c != null) {
			c.moveToFirst();
		}
		return (c.isAfterLast()) ? null : cursorToUser(c);
	}
	
	public Set<User> toUsers(Cursor c) {
		if (c != null) {
			c.moveToFirst();
		}

		Set<User> users = new HashSet<>();
		while (!c.isAfterLast()) {
			users.add(cursorToUser(c));
			c.moveToNext();
		}
		
		return users;
	}
	
	private User cursorToUser(Cursor c) {
		long id = c.getLong(c.getColumnIndex(UserTable._ID));
		String email = c.getString(c.getColumnIndex(UserTable.COLUMN_NAME_EMAIL));
		String username = c.getString(c.getColumnIndex(UserTable.COLUMN_NAME_USERNAME));
		String password = c.getString(c.getColumnIndex(UserTable.COLUMN_NAME_PASSWORD));

		User user = new User(email, username, password);
		user.setId(id);
		
		return user;
	}
}
