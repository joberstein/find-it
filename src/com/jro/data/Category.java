package com.jro.data;

public class Category extends Group {
	
	public Category(String name) {
		super(name);
	}
	
	public boolean isDrawer() {
		return false;
	}
}
