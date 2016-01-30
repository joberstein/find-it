package com.findit.android.activity;

import java.util.List;

import com.findit.android.R;
import com.findit.android.activity.profile.Login;
import com.findit.android.dao.FindItContract.FurnitureTable;
import com.findit.android.dao.FindItDbHelper;
import com.findit.android.data.Drawer;
import com.findit.android.data.Furniture;
import com.findit.android.listener.SelectAllDrawersListener;
import com.findit.android.utils.TableCreator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TextView;

public class CreateFurniture extends Activity {
	private FindItDbHelper db;
	private SharedPreferences preferences;
	private String name;
	private int width;
	private int height;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = FindItDbHelper.getInstance(this);
		preferences = getSharedPreferences(Login.PREFS_NAME, Context.MODE_PRIVATE);
		
		setContentView(R.layout.create_furniture);
		
		Intent receivedIntent = getIntent();
		name = receivedIntent.getStringExtra("furnitureName");
		width = receivedIntent.getIntExtra("furnitureWidth", 0);
		height = receivedIntent.getIntExtra("furnitureHeight", 0);
		
		CheckBox selectAllCheckBox = ( CheckBox ) findViewById(R.id.selectAllDrawers);
		selectAllCheckBox.setOnCheckedChangeListener(new SelectAllDrawersListener());
				
		TableLayout createFurnitureTable = (TableLayout) this.findViewById(R.id.createFurnitureTable);
		TableCreator.createSelectorButtons(this, width, height);
		TableCreator.createButtonTable(this, createFurnitureTable, TableCreator.getSelectorButtons(), width, height);
		
		TextView textView = (TextView) this.findViewById(R.id.furnitureName);
		textView.setText(name);
	}

	public void createFurniture(View view) {
		Furniture newFurniture = new Furniture(name, width, height);
		newFurniture.setCreatorId(preferences.getLong(Login.USER_ID, -1));
		long id = db.saveFurniture(newFurniture);
		newFurniture.setId(id);
		
		this.createDrawersForFurniture(newFurniture);
		TableCreator.resetSelectorButtons();
		
		Intent data = new Intent();
		data.putExtra(FurnitureTable._ID, id);
		data.putExtra(FurnitureTable.COLUMN_NAME_NAME, name);
		data.putExtra(FurnitureTable.COLUMN_NAME_WIDTH, width);
		data.putExtra(FurnitureTable.COLUMN_NAME_HEIGHT, height);
		setResult(Activity.RESULT_OK, data);
		finish();
	}
	
	private void createDrawersForFurniture(Furniture furniture) {
		List<Button> selectorButtons = TableCreator.getSelectorButtons();
		int gridSize = furniture.getHeight() * furniture.getWidth();
		int drawerLocIndex = 0;
		
		for (int i = 0; i < gridSize; i++) {
			if (selectorButtons.get(i).getId() == 1) {
				drawerLocIndex++;
				Drawer newDrawer = furniture.createDrawer("Drawer " + drawerLocIndex, i);
				long id = db.saveDrawer(newDrawer);
				newDrawer.setId(id);
				furniture.addDrawer(newDrawer);
			}
		}
	}
	
	public void goBackToPreviousActivity(View view) {
		finish();
	}
}
