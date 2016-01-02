package com.jro.data;

public class Item {
	private long id;
	private String name;
	private long parentId; 
	private long creatorId;
	
	public Item(String name) {
		this.name = name;
	}
	
	public long getId() {
		return this.id;
	}
	
	// used for testing
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getParentId() {
		return this.parentId;
	}
	
	// used for testing
	public void setParentId(long parentId) {
		this.parentId = parentId;
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
				this.name.toLowerCase().equals(temp.name.toLowerCase()) && 
				this.parentId == temp.parentId;
	}
	
	@Override
	public int hashCode() {
		return new Long(this.id).intValue();
	}
}
