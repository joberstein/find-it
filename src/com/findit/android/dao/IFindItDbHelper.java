package com.findit.android.dao;

import android.database.Cursor;

import com.findit.android.data.Furniture;
import com.findit.android.data.User;

public interface IFindItDbHelper {

	Cursor getUsernames();
	Cursor getUser(long userId);
	Cursor getUserByUsername(String username);
	long saveUser(User newUser);
	void updateUser(User updatedUser);
	void deleteUser(long id);

	Cursor getFurnitureByCreator(long id);
	long saveFurniture(Furniture furniture);
	void updateFurniture(Furniture furniture);
	void deleteFurniture(long id);
}
