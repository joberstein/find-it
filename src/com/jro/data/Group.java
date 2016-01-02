package com.jro.data;

import java.util.ArrayList;
import java.util.List;

public abstract class Group extends Item {
	private List<Item> items;
	
	Group(String name) {
		super(name);
		this.items = new ArrayList<Item>();
	}
	
	public List<Item> getItems() {
		return this.items;
	}
	
	public boolean isDrawer() {
		return this.isDrawer();
	}
	
	public void addToGroup(Item item) {
		this.items.add(item);
	}
	
	public void removeFromGroup(Item item) {
		this.items.remove(item);
	}
}
