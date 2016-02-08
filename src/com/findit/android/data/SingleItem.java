package com.findit.android.data;

import com.findit.android.data.abstraction.DrawerItem;
import com.findit.android.data.abstraction.IDrawerItem;

public class SingleItem extends DrawerItem implements IDrawerItem {
	
	public SingleItem(String name, long drawerId, long parentId, long creatorId) {
		super(name, ItemType.SINGLE_ITEM, drawerId, parentId, creatorId);
	}
}
