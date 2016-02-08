package com.findit.android.db.dao;

import java.util.Set;

import com.findit.android.data.User;

import android.database.Cursor;

public interface UserDao {
	
	Cursor getUser(long userId);
	
	Cursor getUserByUsername(String username);
	
	long saveUser(User newUser);
	
	void updateUser(User updatedUser);
	
	void deleteUser(long id);
	
	User toUser(Cursor c);
	
	Set<User> toUsers(Cursor c);
}
