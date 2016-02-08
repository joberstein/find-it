package com.findit.android.db.dao;

import java.util.List;

import com.findit.android.data.Furniture;

import android.database.Cursor;

public interface FurnitureDao {

	Cursor getFurnitureById(long id);
	
	Cursor getFurnitureByCreator(long creatorId);
	
	long saveFurniture(Furniture furniture);
	
	void updateFurniture(Furniture furniture);
	
	void deleteFurniture(long id);
	
	Furniture toFurniture(Cursor c);
	
	List<Furniture> toFurnitureList(Cursor c);
}
