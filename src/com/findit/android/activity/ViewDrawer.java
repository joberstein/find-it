package com.findit.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.findit.android.R;
import com.findit.android.dao.FindItDbHelper;

public class ViewDrawer extends AppCompatActivity {
	private static FindItDbHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("___ Drawer");
		setContentView(R.layout.view_drawer);
		db = FindItDbHelper.getInstance(this);

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

}
