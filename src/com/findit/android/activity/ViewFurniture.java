package com.findit.android.activity;

import java.util.ArrayList;
import java.util.List;

import com.findit.android.R;
import com.findit.android.activity.profile.Login;
import com.findit.android.adapter.SectionsPagerAdapter;
import com.findit.android.dao.AndroidDatabaseManager;
import com.findit.android.dao.FindItContract.FurnitureTable;
import com.findit.android.dao.FindItDbHelper;
import com.findit.android.data.Drawer;
import com.findit.android.data.Furniture;
import com.findit.android.fragment.EmptyFurnitureFragment;
import com.findit.android.fragment.FurnitureFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ViewFurniture extends AppCompatActivity {
	public static int FURNITURE_COUNT;
	public static Cursor FURNITURE_FOR_USER;
	
	public static String CURRENT_NAME;
	public static int CURRENT_WIDTH;
	public static int CURRENT_HEIGHT;
	
	public static final int CREATE_FURNITURE_REQUEST = 1;
	
	private static final String CURRENT_PAGE = "currentPage";
	private static int currentPage;
	private static FindItDbHelper db;
	private static SharedPreferences preferences;
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	private static SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private static ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Your Furniture");
		setContentView(R.layout.view_furniture);
		db = FindItDbHelper.getInstance(this);
		preferences = getSharedPreferences(Login.PREFS_NAME, Context.MODE_PRIVATE);

		// Create the adapter that will return a fragment for each of the sections of the activity.
		mSectionsPagerAdapter = SectionsPagerAdapter.getInstance(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.fragment_container);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		refreshFurnitureCount();

		if (FURNITURE_COUNT > 0) {
			reAddFurniture(getFurnitureForUser());
		}

		if (savedInstanceState != null) {
			int savedCurrentPage = savedInstanceState.getInt(CURRENT_PAGE);
			currentPage = savedCurrentPage;
			mViewPager.setCurrentItem(savedCurrentPage);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.view_furniture_options, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem removeFurniture = menu.findItem(R.id.remove_furniture);      
		removeFurniture.setVisible((FURNITURE_COUNT > 0)); 
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.add_furniture) {
			forwardToAddFurniture(findViewById(R.layout.set_furniture_properties));
		}
		else if (id == R.id.remove_furniture) {
			removeFurniture(mViewPager.getCurrentItem());
		}
		else if (id == R.id.search) {
			return true;
		}
		else if (id == R.id.logout) {
			Editor editor = preferences.edit();
			editor.putLong(Login.USER_ID, -1);
			editor.commit();
			Intent intent = new Intent(this, Login.class);
			startActivity(intent);
		}
		else if (id == R.id.database) {
			Intent dbmanager = new Intent(this, AndroidDatabaseManager.class);
			startActivity(dbmanager);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume() {
		super.onResume();
		mSectionsPagerAdapter = SectionsPagerAdapter.getInstance(getFragmentManager());
		mSectionsPagerAdapter.notifyDataSetChanged();
 		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setCurrentItem(currentPage);
		refreshFurnitureCount();
	}

	@Override
	public void onPause() {
		super.onPause();
		currentPage = mViewPager.getCurrentItem();
	}

	@Override
	public void onStop() {
		super.onStop();
		currentPage = mViewPager.getCurrentItem();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		currentPage = mViewPager.getCurrentItem();
	}

	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}

	@Override
	public void onSaveInstanceState(Bundle instance) {
		super.onSaveInstanceState(instance);
		instance.putInt(CURRENT_PAGE, currentPage);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CREATE_FURNITURE_REQUEST) {
			if (resultCode == RESULT_OK) {
				addNewFurniture(data.getLongExtra(FurnitureTable._ID, -1));
			}
		}
	}

	private void addNewFurniture(long furnitureId) {
		refreshFurnitureCount();
		FragmentManager fm = getFragmentManager();
		String fragTag = Long.toString(furnitureId);
		Fragment newFragment = null;

		if (findViewById(R.id.fragment_container) != null) {
			newFragment = FurnitureFragment.newInstance(FURNITURE_COUNT - 1, furnitureId);

			if (FURNITURE_COUNT == 1) {
				fm.beginTransaction().replace(R.id.fragment_container, newFragment, fragTag).commit();
			} 
			else {
				fm.beginTransaction().add(R.id.fragment_container, newFragment, fragTag).commit();
			}
			mSectionsPagerAdapter.addFragment(newFragment);
			mSectionsPagerAdapter.notifyDataSetChanged();
			currentPage = FURNITURE_COUNT - 1;
			mViewPager.setCurrentItem(currentPage);
		}
	}

	private void removeFurniture(int fragmentPosition) {
		Fragment fragment = mSectionsPagerAdapter.getFragmentAtPosition(fragmentPosition);
		long furnitureId = Long.parseLong(fragment.getTag());
		removeItemsByFurniture(furnitureId);
		db.deleteFurniture(furnitureId);
		refreshFurnitureCount();
		FragmentManager fm = getFragmentManager();

		if (findViewById(R.id.fragment_container) != null) {
			if (FURNITURE_COUNT == 0) {
				fm.beginTransaction().replace(R.id.fragment_container, new EmptyFurnitureFragment()).commit();
			}
			else {
				fm.beginTransaction().remove(fragment).commit();
			}
			mSectionsPagerAdapter.removeFragment(fragmentPosition);
			mSectionsPagerAdapter.notifyDataSetChanged();
			int newPosition = (fragmentPosition == FURNITURE_COUNT) ? fragmentPosition - 1: fragmentPosition;
			currentPage = newPosition;
			mViewPager.setCurrentItem(currentPage);
		}
	}
	
	private void removeItemsByFurniture(long furnitureId) {
		Furniture furniture = getFurniture(furnitureId);
		List<Drawer> drawers = furniture.getDrawers();
		for (int i = 0; i < drawers.size(); i++) {
			Drawer drawer = drawers.get(i);
			db.deleteItem(drawer.getId());
			furniture.removeDrawer(drawer); // TODO is this necessary?
		}
	}

	private void reAddFurniture(List<Furniture> furnitureList) {
		FragmentManager fm = getFragmentManager();
		List<Fragment> reAddedFragments = new ArrayList<Fragment>();
		Fragment newFragment = null;

		for (int i = 0; i < furnitureList.size(); i++) {
			Furniture furniture = furnitureList.get(i);
			String fragTag = Long.toString(furniture.getId());
			if (findViewById(R.id.fragment_container) != null) {
				newFragment = FurnitureFragment.newInstance(i, furniture.getId());
				if (i == 0) {
					fm.beginTransaction().replace(R.id.fragment_container, newFragment, fragTag).commit();
				} 
				else {
					fm.beginTransaction().add(R.id.fragment_container, newFragment, fragTag).commit();
				}
			}
			reAddedFragments.add(newFragment);
		}
		mSectionsPagerAdapter.reAddFragments(reAddedFragments);
		mSectionsPagerAdapter.notifyDataSetChanged();
	}

	private static void refreshFurnitureCount() {
		long userId = preferences.getLong(Login.USER_ID, -1);
		FURNITURE_FOR_USER = db.getFurnitureByCreator(userId);
		FURNITURE_COUNT = FURNITURE_FOR_USER.getCount();
	}

	public void forwardToAddFurniture(View view) {
		Intent intent = new Intent(this, SetFurnitureProperties.class);
		startActivityForResult(intent, CREATE_FURNITURE_REQUEST);
	}

	public static List<Furniture> getFurnitureForUser() {
		return cursorToFurnitureList(FURNITURE_FOR_USER);
	}
	
	private static List<Furniture> cursorToFurnitureList(Cursor c) {
		List<Furniture> data = new ArrayList<Furniture>();
		if (c != null) {
			c.moveToFirst();
		}
		while (!c.isAfterLast()) {
			data.add(Furniture.createFurnitureFromCursor(c));
			c.moveToNext();
		}

		return data;
	}
	
	private static List<Drawer> cursorToDrawerList(Cursor c) {
		List<Drawer> data = new ArrayList<>();
		if (c != null) {
			c.moveToFirst();
		}
		while (!c.isAfterLast()) {
			data.add(Drawer.createDrawerFromCursor(c));
			c.moveToNext();
		}

		return data;
	}

	public static Furniture getFurniture(long furnitureId) {
		refreshFurnitureCount();
		Cursor furnitureCursor = db.getFurnitureById(furnitureId);
		if (furnitureCursor != null) {
			furnitureCursor.moveToFirst();
		}
		Furniture furniture = Furniture.createFurnitureFromCursor(furnitureCursor);
		
		Cursor drawersCursor = db.getDrawersByFurniture(furnitureId);
		List<Drawer> drawers = cursorToDrawerList(drawersCursor);
		furniture.setDrawers(drawers);
		return furniture;
	}
}
