package com.findit.android.activity;

import java.util.ArrayList;
import java.util.List;

import com.findit.android.R;
import com.findit.android.activity.profile.Login;
import com.findit.android.data.Drawer;
import com.findit.android.data.Furniture;
import com.findit.android.db.FindItContract.FurnitureTable;
import com.findit.android.db.dao.DrawerDao;
import com.findit.android.db.dao.DrawerDaoImpl;
import com.findit.android.db.dao.FurnitureDao;
import com.findit.android.db.dao.FurnitureDaoImpl;
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
	private FurnitureDao furnitureDao;
	private DrawerDao drawerDao;
	private SharedPreferences preferences;
	private String name;
	private int width;
	private int height;
	private boolean[] restoreSavedStateSelected;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		furnitureDao = FurnitureDaoImpl.getInstance(this);
		drawerDao = DrawerDaoImpl.getInstance(this);
		preferences = getSharedPreferences(Login.PREFS_NAME, Context.MODE_PRIVATE);
		
		setContentView(R.layout.create_furniture);
		
		Intent receivedIntent = getIntent();
		name = receivedIntent.getStringExtra("furnitureName");
		width = receivedIntent.getIntExtra("furnitureWidth", 0);
		height = receivedIntent.getIntExtra("furnitureHeight", 0);
		
		if (savedInstanceState != null) {
			int numButtons = savedInstanceState.getInt("size");
			List<Button> restoredSelectorButtons = new ArrayList<>();
			restoreSavedStateSelected = new boolean[numButtons];
			if (savedInstanceState.getBoolean("isChecked")) {
				for (int i = 0; i < numButtons; i++) {
					Bundle bundle = savedInstanceState.getBundle(Integer.toString(i));
					restoreSavedStateSelected[i] = bundle.getBoolean("isSelected");
					Button button = TableCreator.restoreSelectorButton(this, 1);
					restoredSelectorButtons.add(button);
				}
				TableCreator.setSelectorButtons(restoredSelectorButtons);
			}
			else {
				for (int i = 0; i < numButtons; i++) {
					Bundle bundle = savedInstanceState.getBundle(Integer.toString(i));
					Button button = TableCreator.restoreSelectorButton(this, bundle.getInt("id"));
					restoredSelectorButtons.add(button);
				}
				TableCreator.setSelectorButtons(restoredSelectorButtons);
			}
		}
		else {
			TableCreator.createSelectorButtons(this, width, height);
		}

		CheckBox selectAllCheckBox = (CheckBox) findViewById(R.id.selectAllDrawers);
		selectAllCheckBox.setOnCheckedChangeListener(new SelectAllDrawersListener());
		
		TableLayout createFurnitureTable = (TableLayout) this.findViewById(R.id.createFurnitureTable);
		TableCreator.createButtonTable(this, createFurnitureTable, TableCreator.getSelectorButtons(), width, height);
		
		TextView textView = (TextView) this.findViewById(R.id.furnitureName);
		textView.setText(name);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle instance) {
		super.onRestoreInstanceState(instance);
		SelectAllDrawersListener.setSavedStateSelected(restoreSavedStateSelected);
	}
	
	@Override
	public void onSaveInstanceState(Bundle instance) {
		super.onSaveInstanceState(instance);
		List<Button> selectorButtons = TableCreator.getSelectorButtons();
		boolean[] savedStateSelected = SelectAllDrawersListener.getSavedStateSelected();
		CheckBox checkbox = (CheckBox) findViewById(R.id.selectAllDrawers);
		instance.putBoolean("isChecked", checkbox.isChecked());
		instance.putInt("size", selectorButtons.size());
		
		for (int i = 0; i < selectorButtons.size(); i++) {
			Bundle buttonBundle = new Bundle();
			int id =  selectorButtons.get(i).getId();
			buttonBundle.putInt("id", id);
			boolean isSelected = (checkbox.isChecked()) ? savedStateSelected[i] : (id == 1);
			buttonBundle.putBoolean("isSelected", isSelected);
			instance.putBundle(Integer.toString(i), buttonBundle);
		}
	}

	public void createFurniture(View view) {
		long creatorId = preferences.getLong(Login.USER_ID, -1);
		Furniture newFurniture = new Furniture(name, width, height, creatorId);
		long id = furnitureDao.saveFurniture(newFurniture);
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
				long id = drawerDao.saveDrawer(newDrawer);
				newDrawer.setId(id);
				furniture.addItem(newDrawer);
			}
		}
	}
	
	public void goBackToPreviousActivity(View view) {
		finish();
	}
}
