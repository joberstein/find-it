package com.findit.android.fragment;

import java.util.List;

import com.findit.android.R;
import com.findit.android.activity.ViewFurniture;
import com.findit.android.data.Furniture;
import com.findit.android.db.FindItContract.FurnitureTable;
import com.findit.android.utils.TableCreator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class FurnitureFragment extends Fragment {

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		setHasOptionsMenu(true);

		Bundle args = getArguments();
		if (ViewFurniture.getFurnitureCount() == 0) {
			return inflater.inflate(R.layout.fragment_empty_furniture, container, false);
		}

		if (args != null) {
			view = inflater.inflate(R.layout.fragment_furniture, container, false);
			TextView furnitureNameView = (TextView) view.findViewById(R.id.furnitureName);
			TableLayout furnitureTable = (TableLayout) view.findViewById(R.id.furnitureTable);

			long furnitureId = args.getLong(FurnitureTable._ID);
			String name = args.getString(FurnitureTable.COLUMN_NAME_NAME, "");
			int width = args.getInt(FurnitureTable.COLUMN_NAME_WIDTH, 1);
			int height = args.getInt(FurnitureTable.COLUMN_NAME_HEIGHT, 1);
			
			furnitureNameView.setText(name);
			List<Button> drawerButtons = TableCreator.createDrawerButtons(getActivity(), furnitureId, width, height);
			TableCreator.createButtonTable(getActivity(), furnitureTable, drawerButtons, width, height);
		}
		
		return view;
	}

	public static FurnitureFragment newInstance(int position, long furnitureId) {
		FurnitureFragment fragment = new FurnitureFragment();
		Furniture furniture = ViewFurniture.getFurniture(furnitureId);

		Bundle args = new Bundle();
		args.putLong(FurnitureTable._ID, furnitureId);
		args.putString(FurnitureTable.COLUMN_NAME_NAME, furniture.getName());
		args.putInt(FurnitureTable.COLUMN_NAME_WIDTH, furniture.getWidth());
		args.putInt(FurnitureTable.COLUMN_NAME_HEIGHT, furniture.getHeight());
		fragment.setArguments(args);

		return fragment;
	}
}
