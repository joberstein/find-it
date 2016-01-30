package com.findit.android.activity;

import java.util.List;

import com.findit.android.R;
import com.findit.android.dao.FindItContract.FurnitureTable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;

public class SetFurnitureProperties extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_furniture_properties);
	}

	public void goToCreateFurniture(View view) {
		EditText text = (EditText) findViewById(R.id.inputFurnitureName);
		String name = (text).getText().toString();
		int width = Integer.parseInt(((Spinner) findViewById(R.id.inputFurnitureWidth)).getSelectedItem().toString());
		int height = Integer.parseInt(((Spinner) findViewById(R.id.inputFurnitureHeight)).getSelectedItem().toString());

		Intent intent = new Intent(this, CreateFurniture.class);
		intent.putExtra("furnitureName", name);
		intent.putExtra("furnitureWidth", width);
		intent.putExtra("furnitureHeight", height);
		startActivityForResult(intent, ViewFurniture.CREATE_FURNITURE_REQUEST);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ViewFurniture.CREATE_FURNITURE_REQUEST) {
			if (resultCode == RESULT_OK) {
				setResult(resultCode, data);
				finish();
			}
		}
	}
	
	public void goBackToPreviousActivity(View view) {
		finish();
	}
}
