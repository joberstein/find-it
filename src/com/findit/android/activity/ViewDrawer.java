package com.findit.android.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.findit.android.R;
import com.findit.android.adapter.ExpandableListAdapter;
import com.findit.android.dao.FindItContract.ItemTable;
import com.findit.android.dao.FindItDbHelper;
import com.findit.android.data.Category;
import com.findit.android.data.Drawer;
import com.findit.android.data.DrawerItem;
import com.findit.android.data.ItemType;
import com.findit.android.data.SingleItem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

public class ViewDrawer extends AppCompatActivity {
	private static FindItDbHelper db;

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		db = FindItDbHelper.getInstance(this);
		Intent intent = getIntent();
		Drawer drawer = getDrawer(intent.getLongExtra("drawerId", -1));
		String title = (drawer != null && drawer.getName() != null) ? drawer.getName() : intent.getStringExtra("defaultName");
		setTitle(title);
		
		Category videoGames = (Category) drawer.createDrawerItem("Video Games", ItemType.CATEGORY);
		long videoGamesId = db.saveItem(videoGames);
		videoGames.setId(videoGamesId);
		SingleItem ssbb = (SingleItem) videoGames.createSingleItem("Super Smash Bros. Brawl");
		SingleItem mkwii = (SingleItem) videoGames.createSingleItem("Mario Kart Wii");
		db.saveItem(ssbb);
		db.saveItem(mkwii);
		db.saveItem(drawer.createDrawerItem("Empty Category", ItemType.CATEGORY));
		db.saveItem(drawer.createDrawerItem("Pencils", ItemType.SINGLE_ITEM));
		
		setContentView(R.layout.view_drawer);

		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.expandableList);

		// preparing list data
		// Normally the drawer only needs to be retrieved once (above).
		prepareListData(getDrawer(drawer.getId())); 

		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		if (savedInstanceState != null) {
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//		getMenuInflater().inflate(R.menu.view_furniture_options, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		//		MenuItem removeFurniture = menu.findItem(R.id.remove_furniture);      
		//		removeFurniture.setVisible((FURNITURE_COUNT > 0)); 
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}

	/*
	 * Preparing the list data
	 */
	private void prepareListData(Drawer drawer) {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		List<DrawerItem> items = drawer.getItems();
		
		for (int i = 0; i < items.size(); i++) {
			DrawerItem item = items.get(i);
			String itemName = items.get(i).getName();
			List<String> childItems = Collections.emptyList();
			listDataHeader.add(itemName);
			
			if (item.getType().equals(ItemType.CATEGORY)) {
				childItems = cursorToSingleItemList(db.getItemsByParent(item.getId()));
			}
			
			listDataChild.put(itemName, childItems);
		}
	}
	
	public static Drawer getDrawer(long drawerId) {
		Cursor drawerCursor = db.getDrawerById(drawerId);
		if (drawerCursor != null) {
			drawerCursor.moveToFirst();
		}
		Drawer drawer = Drawer.createDrawerFromCursor(drawerCursor);
		
		Cursor drawerItemsCursor = db.getItemsByParent(drawerId);
		List<DrawerItem> drawerItems = cursorToDrawerItemsList(drawerItemsCursor);
		drawer.setItems(drawerItems);
		return drawer;
	}
	
	private static Category getCategory(long id) {
		Cursor cursor = db.getItemById(id);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return Category.createCategoryFromCursor(cursor);
	}
	
	private static SingleItem getSingleItem(long id) {
		Cursor cursor = db.getItemById(id);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return SingleItem.createSingleItemFromCursor(cursor);
	}
	
	private static List<DrawerItem> cursorToDrawerItemsList(Cursor c) {
		List<DrawerItem> data = new ArrayList<>();
		if (c != null) {
			c.moveToFirst();
		}
		while (!c.isAfterLast()) {
			String storedItemType = c.getString(c.getColumnIndex(ItemTable.COLUMN_NAME_TYPE));
			if (storedItemType.equals(ItemType.CATEGORY.name())) {
				data.add(Category.createCategoryFromCursor(c));
			}
			else {
				data.add(SingleItem.createSingleItemFromCursor(c));
			}
			c.moveToNext();
		}

		return data;
	}
	
	private static List<String> cursorToSingleItemList(Cursor c) {
		List<String> data = new ArrayList<>();
		if (c != null) {
			c.moveToFirst();
		}
		while (!c.isAfterLast()) {
			SingleItem item = SingleItem.createSingleItemFromCursor(c);
			data.add(item.getName());
			c.moveToNext();
		}

		return data;
	}
	
	
}
