package com.findit.android.data;

public abstract class DrawerItem extends Item {
	private long drawerId;

	DrawerItem(String name, ItemType type, long drawerId, long parentId, long creatorId) {
		super(name, type, parentId, creatorId);
		this.drawerId = drawerId;
	}
	
	public long getDrawerId() {
		return this.drawerId;
	}
	
	public void setDrawerId(long drawerId) {
		this.drawerId = drawerId;
	}
	
	
}
