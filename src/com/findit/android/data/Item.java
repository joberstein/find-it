package com.findit.android.data;

import java.util.Locale;

public abstract class Item {
	private long id;
	private String name;
	private ItemType type;
	private long parentId; 
	private long creatorId;
	
	Item(String name, ItemType type, long parentId, long creatorId) {
		this.name = name;
		this.type = type;
		this.parentId = parentId;
		this.creatorId = creatorId;
	}
	
	public long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ItemType getType() {
		return this.type;
	}
	
	public long getParentId() {
		return this.parentId;
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
	
	public void setType(ItemType type) {
		this.type = type;
	}
	
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		else if (!(o instanceof Item)) {
			return false;
		}
		Item temp = (Item) o;
		return this.id == temp.id && 
				this.name.toLowerCase(Locale.US).equals(temp.name.toLowerCase(Locale.US)) && 
				this.parentId == temp.parentId;
	}
	
	@Override
	public int hashCode() {
		return Long.valueOf(this.id).intValue();
	}
}
