package com.findit.android.dao;

import com.findit.android.data.Drawer;
import com.findit.android.data.DrawerItem;
import com.findit.android.data.Furniture;
import com.findit.android.data.User;

import android.database.Cursor;

public interface IFindItDbHelper {

	Cursor getUsernames();
	Cursor getUser(long userId);
	Cursor getUserByUsername(String username);
	long saveUser(User newUser);
	void updateUser(User updatedUser);
	void deleteUser(long id);

	Cursor getFurnitureById(long id);
	Cursor getFurnitureByCreator(long creatorId);
	long saveFurniture(Furniture furniture);
	void updateFurniture(Furniture furniture);
	void deleteFurniture(long id);
	
	Cursor getDrawerById(long id);
	Cursor getDrawersByFurniture(long parentId);
	Cursor getDrawersByCreator(long creatorId);
	long saveDrawer(Drawer drawer);
	void updateDrawer(Drawer drawer);
	void deleteDrawer(long id);
	
	Cursor getItemById(long id);
	Cursor getItemsByParent(long parentId);
	Cursor getItemsByCreator(long creatorId);
	long saveItem(DrawerItem item);
	void updateItem(DrawerItem item);
	void deleteItem(long id);
}
