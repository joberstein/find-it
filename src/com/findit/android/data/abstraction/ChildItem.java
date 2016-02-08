package com.findit.android.data.abstraction;

import java.util.Locale;

public class ChildItem extends Item {
	private long parentId;
	
	protected ChildItem(String name, long parentId, long creatorId) {
		super(name, creatorId);
		this.parentId = parentId;
	}
	
	public long getParentId() {
		return this.parentId;
	}
	
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		else if (!(o instanceof ChildItem)) {
			return false;
		}
		ChildItem temp = (ChildItem) o;
		return this.getId() == temp.getId() && 
				this.getName().toLowerCase(Locale.US).equals(temp.getName().toLowerCase(Locale.US)) &&
				this.parentId == temp.getParentId();
	}
	
	@Override
	public int hashCode() {
		return Long.valueOf(this.getId()).intValue();
	}
}
