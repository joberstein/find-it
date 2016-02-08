package com.findit.android.data.abstraction;

import java.util.ArrayList;
import java.util.List;

public class RootItemWithList<T> extends RootItem {
	private List<T> items;
	
	protected RootItemWithList(String name, long creatorId) {
		super(name, creatorId);
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
