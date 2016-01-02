package com.jro.dao;

import android.database.Cursor;

import com.jro.data.Furniture;
import com.jro.data.User;

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
