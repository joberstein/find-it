package com.jro.findit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;

import com.jro.dao.FindItContract.FurnitureTable;
import com.jro.data.Furniture;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
	private static SectionsPagerAdapter adapter;
	private static List<Fragment> mFragments;
	private static Set<String> mTags;

	private SectionsPagerAdapter(FragmentManager fm) {
		super(fm);
		mFragments = SectionsPagerAdapter.getFragments();
		mTags = SectionsPagerAdapter.getFragmentTags();
	}

	public static SectionsPagerAdapter getInstance(FragmentManager fm) {
		if (adapter == null) {
			return new SectionsPagerAdapter(fm);
		}
		return adapter;
	}

	public static List<Fragment> getFragments() {
		if (mFragments == null) {
			List<Fragment> frags = new ArrayList<Fragment>();
			frags.add(new EmptyFurnitureFragment());
			return frags;
		}
		return mFragments;
	}

	public static Set<String> getFragmentTags() {
		if (mTags == null) {
			return new HashSet<String>();
		}
		return mTags;
	}

	@Override
	public Fragment getItem(int position) {
		if (ViewFurniture.FURNITURE_COUNT == 0) {
			return new EmptyFurnitureFragment();
		}
		else {
			Fragment frag = mFragments.get(position);
			if (frag != null) {
				String fragTag = frag.getTag();
				if (fragTag != null) {
					long furnitureId = Long.parseLong(fragTag);
					return FurnitureFragment.newInstance(position, furnitureId);
				}
			}
			System.out.println("Something is wrong for fragment: " + frag.toString());
			return null;
//			return FurnitureFragment.newInstance(position, -1);
		}
	}

	@Override
	public int getItemPosition(Object o) {
//		Fragment fragment = (Fragment) o;
//		if (!mTags.isEmpty() && mTags.contains(fragment.getTag())) {
//			return POSITION_UNCHANGED;
//		}
//		return POSITION_NONE;
		
		Fragment fragment = (Fragment) o;
		Bundle args = fragment.getArguments();
		if (args != null) {
			String fragTag = Long.toString(args.getLong("furnitureId"));
			if (mTags.contains(fragTag)) {
				for (int i = 0; i < mFragments.size(); i++) {
					Fragment listFragment = mFragments.get(i);
					if (listFragment.getTag().equals(fragTag)) {
						return i;
					}
				}
			}
		}
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	public void addFragment(Fragment fragment) {
		if (ViewFurniture.FURNITURE_COUNT == 1) {
			mFragments = new ArrayList<Fragment>();
		}
		mFragments.add(fragment);
 		mTags.add(fragment.getTag());
	}
	
	public void reAddFragment(Fragment fragment) {
		mFragments.add(fragment);
		mTags.add(fragment.getTag());
	}
	
	public void clearFragments() {
		mFragments.clear();
		mTags.clear();
	}

	public void reAddFragments(List<Fragment> fragments) {
		mFragments.clear();
		mFragments.addAll(fragments);
		mTags.clear();
		for (Fragment frag : fragments) {
			mTags.add(frag.getTag());
		}
	}


	public void removeFragment(int position) {
		if (mFragments.size() == 1) {
			mFragments.clear();
			mFragments.add(new EmptyFurnitureFragment());
			mTags.clear();
		}
		else {
			Fragment fragToRemove = getFragmentAtPosition(position);
			mTags.remove(fragToRemove.getTag());
			mFragments.remove(position);
		}
	}

	public Fragment getFragmentAtPosition(int position) {
		return mFragments.get(position);
	}

	public List<Furniture> getFurnitureForUser() {
		return cursorToList(ViewFurniture.FURNITURE_FOR_USER);
	}

	private List<Furniture> cursorToList(Cursor c) {
		List<Furniture> data = new ArrayList<Furniture>();
		if (c != null) {
			c.moveToFirst();
		}
		while (!c.isAfterLast()) {
			data.add(createFurnitureFromCursor(c));
			c.moveToNext();
		}

		return data;
	}
	
	public Furniture createFurnitureFromCursor(Cursor c) {
		long id = c.getLong(c.getColumnIndex(FurnitureTable._ID));
		String name = c.getString(c.getColumnIndex(FurnitureTable.COLUMN_NAME_NAME));
		int width = c.getInt(c.getColumnIndex(FurnitureTable.COLUMN_NAME_WIDTH));
		int height = c.getInt(c.getColumnIndex(FurnitureTable.COLUMN_NAME_HEIGHT));
		long creatorId = c.getLong(c.getColumnIndex(FurnitureTable.COLUMN_NAME_CREATOR_ID));

		Furniture furniture = new Furniture(name, width, height);
		furniture.setId(id);
		furniture.setCreatorId(creatorId);
		return furniture;
	}
}