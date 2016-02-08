package com.findit.android.data;

import com.findit.android.data.abstraction.ChildItemWithList;
import com.findit.android.data.abstraction.IDrawerItem;

public class Drawer extends ChildItemWithList<IDrawerItem> {
	private int locIndex;
	
	public Drawer(String name, int locIndex, long parentId, long creatorId) {
		super(name, parentId, creatorId);
		this.locIndex = locIndex;
	}
	
	public int getLocIndex() {
		return this.locIndex;
	}
	
	public void setLocIndex(int locIndex) {
		this.locIndex = locIndex;
	}
	
	public IDrawerItem createDrawerItem(String name, ItemType type) {
		if (type.equals(ItemType.CATEGORY)) {
			return new Category(name, this.getId(), this.getId(), this.getCreatorId());
		}
		else if (type.equals(ItemType.SINGLE_ITEM)) {
			return new SingleItem(name, this.getId(), this.getId(), this.getCreatorId());
		}
		return null;
	}
}
