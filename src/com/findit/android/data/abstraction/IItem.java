package com.findit.android.data.abstraction;

public interface IItem {
	
	public long getId();
	
	public String getName();
	
	public long getCreatorId();
	
	public void setId(long id);
	
	public void setName(String name);
	
	public void setCreatorId(long creatorId);
}
