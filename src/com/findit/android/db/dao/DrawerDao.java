package com.findit.android.db.dao;

import java.util.List;

import com.findit.android.data.Drawer;

import android.database.Cursor;

public interface DrawerDao {

	Cursor getDrawerById(long id);
	
	Cursor getDrawersByFurniture(long parentId);
	
	Cursor getDrawersByCreator(long creatorId);
	
	long saveDrawer(Drawer drawer);
	
	void updateDrawer(Drawer drawer);
	
	void deleteDrawer(long id);
	
	Drawer toDrawer(Cursor c);
	
	List<Drawer> toDrawerList(Cursor c);
}
