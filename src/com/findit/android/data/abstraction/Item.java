package com.findit.android.data.abstraction;

public abstract class Item {
	private long id;
	private String name; 
	private long creatorId;
	
	Item(String name, long creatorId) {
		this.name = name;
		this.creatorId = creatorId;
	}
	
	public long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public long getCreatorId() {
		return this.creatorId;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}
}
