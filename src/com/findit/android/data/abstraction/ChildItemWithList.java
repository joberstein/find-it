package com.findit.android.data.abstraction;

import java.util.ArrayList;
import java.util.List;

public class ChildItemWithList<T> extends ChildItem {
	private List<T> items;
	
	protected ChildItemWithList(String name, long parentId, long creatorId) {
		super(name, parentId, creatorId);
		items = new ArrayList<>();
	}
	
	public List<T> getItems() {
		return this.items;
	}
	
	public void setItems(List<T> items) {
		this.items = items;
	}

	public void addItem(T item) {
		this.items.add(item);
	}
	
	public void removeItem(T item) {
		this.items.remove(item);
	}

}
