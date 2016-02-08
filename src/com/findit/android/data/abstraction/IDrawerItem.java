package com.findit.android.data.abstraction;

import com.findit.android.data.ItemType;

public interface IDrawerItem extends IChildItem {
	
	public long getDrawerId();
	
	public ItemType getType();
	
	public void setDrawerId(long drawerId);

}
