package com.findit.android.db.dao;

import java.util.List;

import com.findit.android.data.Category;
import com.findit.android.data.SingleItem;
import com.findit.android.data.abstraction.IDrawerItem;

import android.database.Cursor;

public interface DrawerItemDao {

	Cursor getDrawerItemById(long id);
	
	Cursor getDrawerItemsByParent(long parentId);
	
	Cursor getDrawerItemsByCreator(long creatorId);
	
	long saveDrawerItem(IDrawerItem item);
	
	void updateDrawerItem(IDrawerItem item);
	
	void deleteDrawerItem(long id);
	
	SingleItem toSingleItem(Cursor c);
	
	Category toCategory(Cursor c);
	
	List<IDrawerItem> toDrawerItemList(Cursor c);
}
