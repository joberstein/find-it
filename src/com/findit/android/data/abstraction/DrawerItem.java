package com.findit.android.data.abstraction;

import com.findit.android.data.ItemType;

public abstract class DrawerItem extends ChildItem implements IDrawerItem {
	private long drawerId;
	private ItemType type;

	protected DrawerItem(String name, ItemType type, long drawerId, long parentId, long creatorId) {
		super(name, parentId, creatorId);
		this.type = type;
		this.drawerId = drawerId;
	}
	
	public long getDrawerId() {
		return this.drawerId;
	}
	
	public ItemType getType() {
		return this.type;
	}
	
	public void setDrawerId(long drawerId) {
		this.drawerId = drawerId;
	}
}
