package com.findit.android.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;

import com.findit.android.activity.ViewFurniture;
import com.findit.android.dao.FindItContract.FurnitureTable;
import com.findit.android.fragment.EmptyFurnitureFragment;
import com.findit.android.fragment.FurnitureFragment;

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

	// Took out item at 0, added a new item, and received old view before removal.
	@Override
	public Fragment getItem(int position) {
		if (ViewFurniture.FURNITURE_COUNT == 0) {
			return new EmptyFurnitureFragment();
		}
		else {
			Fragment frag = mFragments.get(position);
			long furnitureId = -1;
			if (frag != null) {
				String fragTag = frag.getTag();
				if (fragTag != null) {
					furnitureId = Long.parseLong(fragTag);
				}
			}
			return FurnitureFragment.newInstance(position, furnitureId);
		}
	}

	@Override
	public int getItemPosition(Object o) {
		Fragment fragment = (Fragment) o;
		Bundle args = fragment.getArguments();
		if (args != null) {
			String fragTag = Long.toString(args.getLong(FurnitureTable._ID));
			if (mTags.contains(fragTag)) {
				for (int i = 0; i < mFragments.size(); i++) {
					if (mFragments.get(i).getTag().equals(fragTag)) {
						return i;
					}
				}
				return POSITION_UNCHANGED;
			}
		}
		return POSITION_NONE;
	}
	
	@Override
	public long getItemId (int position) {
		Fragment frag = mFragments.get(position);
		if (frag instanceof FurnitureFragment) {
			return Long.parseLong(frag.getTag());
		}
		return -1;
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
}