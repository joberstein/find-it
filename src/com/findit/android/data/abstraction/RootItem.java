package com.findit.android.data.abstraction;

import java.util.Locale;

public class RootItem extends Item {

	RootItem(String name, long creatorId) {
		super(name, creatorId);
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
		return this.getId() == temp.getId() && 
				this.getName().toLowerCase(Locale.US).equals(temp.getName().toLowerCase(Locale.US));
	}
	
	@Override
	public int hashCode() {
		return Long.valueOf(this.getId()).intValue();
	}
}
