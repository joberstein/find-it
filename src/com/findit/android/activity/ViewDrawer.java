package com.findit.android.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.findit.android.R;
import com.findit.android.adapter.ExpandableListAdapter;
import com.findit.android.data.Category;
import com.findit.android.data.Drawer;
import com.findit.android.data.ItemType;
import com.findit.android.data.SingleItem;
import com.findit.android.data.abstraction.IDrawerItem;
import com.findit.android.db.dao.DrawerDao;
import com.findit.android.db.dao.DrawerDaoImpl;
import com.findit.android.db.dao.DrawerItemDao;
import com.findit.android.db.dao.DrawerItemDaoImpl;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

public class ViewDrawer extends AppCompatActivity {
	private static DrawerItemDao drawerItemDao;
	private static DrawerDao drawerDao;

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		drawerItemDao = DrawerItemDaoImpl.getInstance(this);
		drawerDao = DrawerDaoImpl.getInstance(this);
		Intent intent = getIntent();
		Drawer drawer = getDrawer(intent.getLongExtra("drawerId", -1));
		String title = (drawer != null && drawer.getName() != null) ? drawer.getName() : intent.getStringExtra("defaultName");
		setTitle(title);
		
		Category videoGames = (Category) drawer.createDrawerItem("Video Games", ItemType.CATEGORY);
		long videoGamesId = drawerItemDao.saveDrawerItem(videoGames);
		videoGames.setId(videoGamesId);
		SingleItem ssbb = (SingleItem) videoGames.createSingleItem("Super Smash Bros. Brawl");
		SingleItem mkwii = (SingleItem) videoGames.createSingleItem("Mario Kart Wii");
		drawerItemDao.saveDrawerItem(ssbb);
		drawerItemDao.saveDrawerItem(mkwii);
		drawerItemDao.saveDrawerItem(drawer.createDrawerItem("Empty Category", ItemType.CATEGORY));
		drawerItemDao.saveDrawerItem(drawer.createDrawerItem("Pencils", ItemType.SINGLE_ITEM));
		
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
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}

	/*
	 * Preparing the list data
	 */
	private void prepareListData(Drawer drawer) {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		List<IDrawerItem> drawerItems = drawer.getItems();
		
		for (IDrawerItem drawerItem : drawerItems) {
			List<String> childNames = new ArrayList<>();
			String drawerItemName = drawerItem.getName();
			listDataHeader.add(drawerItemName);
			
			if (drawerItem.getType().equals(ItemType.CATEGORY)) {
				Cursor drawerItemsCursor = drawerItemDao.getDrawerItemsByParent(drawerItem.getId());
				List<IDrawerItem> childItems = drawerItemDao.toDrawerItemList(drawerItemsCursor);
				
				for (IDrawerItem childItem : childItems) {
					childNames.add(childItem.getName());
				}
			}
			
			listDataChild.put(drawerItemName, childNames);
		}
	}
	
	public static Drawer getDrawer(long drawerId) {
		Cursor drawerCursor = drawerDao.getDrawerById(drawerId);
		Drawer drawer = drawerDao.toDrawer(drawerCursor);
		
		Cursor drawerItemsCursor = drawerItemDao.getDrawerItemsByParent(drawerId);
		List<IDrawerItem> drawerItems = drawerItemDao.toDrawerItemList(drawerItemsCursor);
		drawer.setItems(drawerItems);
		
		return drawer;
	}
}
