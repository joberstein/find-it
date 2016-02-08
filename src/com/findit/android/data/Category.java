package com.findit.android.data;

import com.findit.android.data.abstraction.DrawerItem;
import com.findit.android.data.abstraction.DrawerItemWithList;
import com.findit.android.data.abstraction.IDrawerItem;

public class Category extends DrawerItemWithList implements IDrawerItem {
	
	public Category(String name, long drawerId, long parentId, long creatorId) {
		super(name, ItemType.CATEGORY, drawerId, parentId, creatorId);
	}
	
	public DrawerItem createSingleItem(String name) {
		return new SingleItem(name, this.getParentId(), this.getId(), this.getCreatorId());
	}
}

