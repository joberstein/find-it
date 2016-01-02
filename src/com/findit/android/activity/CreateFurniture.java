package com.findit.android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.findit.android.activity.profile.Login;
import com.findit.android.dao.FindItContract.FurnitureTable;
import com.findit.android.dao.FindItDbHelper;
import com.findit.android.data.Furniture;
import com.findit.android.R;

public class CreateFurniture extends Activity {
	FindItDbHelper db;
	SharedPreferences preferences;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = FindItDbHelper.getInstance(this);
		preferences = getSharedPreferences(Login.PREFS_NAME, Context.MODE_PRIVATE);
		setContentView(R.layout.create_furniture);
	}

	public void createFurniture(View view) {
		EditText text = (EditText) findViewById(R.id.inputFurnitureName);
		String name = (text).getText().toString();
		int width = Integer.parseInt(((Spinner) findViewById(R.id.inputFurnitureWidth)).getSelectedItem().toString());
		int height = Integer.parseInt(((Spinner) findViewById(R.id.inputFurnitureHeight)).getSelectedItem().toString());
		
		Furniture newFurniture = new Furniture(name, width, height);
		newFurniture.setCreatorId(preferences.getLong(Login.USER_ID, -1));
		long id = db.saveFurniture(newFurniture);
		newFurniture.setId(id);
		
		Intent data = new Intent();
		data.putExtra(FurnitureTable._ID, id);
		data.putExtra(FurnitureTable.COLUMN_NAME_NAME, name);
		data.putExtra(FurnitureTable.COLUMN_NAME_WIDTH, width);
		data.putExtra(FurnitureTable.COLUMN_NAME_HEIGHT, height);
		setResult(Activity.RESULT_OK, data);
		finish();
	}
	
	public void goBackToPreviousActivity(View view) {
		finish();
	}
}
