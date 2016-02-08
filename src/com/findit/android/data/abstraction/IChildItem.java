package com.findit.android.data.abstraction;

public interface IChildItem extends IItem {
	
	public long getParentId();
	
	public void setParentId(long parentId);
}
