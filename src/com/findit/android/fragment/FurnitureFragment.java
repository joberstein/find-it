package com.findit.android.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.findit.android.activity.ViewFurniture;
import com.findit.android.data.Furniture;
import com.findit.android.R;

public class FurnitureFragment extends Fragment {

	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		setHasOptionsMenu(true);
		
		Bundle args = getArguments();
		if (ViewFurniture.FURNITURE_COUNT == 0) {
			return inflater.inflate(R.layout.fragment_empty_furniture, container, false);
		}
		
		if (args != null) {
			view = inflater.inflate(R.layout.fragment_furniture, container, false);
			TextView furnitureNameView = (TextView) view.findViewById(R.id.furnitureName);
			TableLayout furnitureTable = (TableLayout) view.findViewById(R.id.furnitureTable);
			
			String name = args.getString("name", "");
			int width = args.getInt("width", 1);
			int height = args.getInt("height", 1);
			
			furnitureNameView.setText(name);
			createFurnitureTable(furnitureTable, width, height);
		}
		return view;
	}

	public static FurnitureFragment newInstance(int position, long furnitureId) {
		FurnitureFragment fragment = new FurnitureFragment();
		Furniture furniture = ViewFurniture.getFurniture(furnitureId);
		
		Bundle args = new Bundle();
		args.putLong("furnitureId", furnitureId);
		args.putString("name", furniture.getName());
		args.putInt("width", furniture.getWidth());
		args.putInt("height", furniture.getHeight());
		
		fragment.setArguments(args);
		return fragment;
	}
	
	@SuppressLint("NewApi")
	private void createFurnitureTable(TableLayout furnitureTable, int width, int height) {
		GradientDrawable blackBorder = new GradientDrawable();
        blackBorder.setStroke(1, Color.BLACK);
        
		for (int i = 0; i < height; i++) {
			TableRow tableRow = new TableRow(getActivity());
			tableRow.setLayoutParams(new LayoutParams(TableRow.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			
			for (int j = 0; j < width; j++) {
				Button button = new Button(getActivity());
		        button.setBackground(blackBorder);
				button.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
				button.setGravity(Gravity.CENTER);
				button.setText("+");
				tableRow.addView(button);
			}
			furnitureTable.addView(tableRow);
		}
	}
}
