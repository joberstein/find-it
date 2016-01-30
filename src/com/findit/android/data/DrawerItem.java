package com.findit.android.data;

public abstract class DrawerItem extends Item {
	
	DrawerItem(String name, ItemType type, long parentId, long creatorId) {
		super(name, type, parentId, creatorId);
	}
	
	
}
