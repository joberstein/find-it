package com.findit.android.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.findit.android.activity.ViewFurniture;
import com.findit.android.data.Drawer;
import com.findit.android.listener.DrawerClickListener;
import com.findit.android.listener.SelectDrawerToCreateListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class TableCreator {
	private static int drawerCounter = 1;
	private static List<Button> selectorButtons;

	public static void createButtonTable(Context context, TableLayout furnitureTable, List<Button> buttons, int width,
			int height) {
		int counter = 0;
		for (int i = 0; i < height; i++) {
			TableRow tableRow = new TableRow(context);
			tableRow.setLayoutParams(new LayoutParams(TableRow.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

			for (int j = 0; j < width; j++) {
				Button button = buttons.get(counter);
				if (button != null) {
					button.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
					button.setGravity(Gravity.CENTER);
					tableRow.addView(button);
				}
				counter++;
			}
			furnitureTable.addView(tableRow);
		}
		resetDrawerCounter();
	}

	public static void resetSelectorButtons() {
		selectorButtons = null;
	}

	public static List<Button> getSelectorButtons() {
		return selectorButtons;
	}

	public static void setSelectorButtons(List<Button> buttons) {
		selectorButtons = buttons;
	}

	public static List<Button> createDrawerButtons(Context context, long furnitureId, int width, int height) {
		List<Drawer> drawers = ViewFurniture.getFurniture(furnitureId).getItems();
		Set<Integer> selectedDrawers = new HashSet<>();

		for (int i = 0; i < drawers.size(); i++) {
			int locIndex = drawers.get(i).getLocIndex();
			selectedDrawers.add(locIndex);
		}

		int drawerCounter = 0;
		List<Button> buttons = new ArrayList<>();
		for (int i = 0; i < width * height; i++) {
			if (selectedDrawers.contains(i)) {
				int drawerId = (drawers.isEmpty()) ? -1 : Long.valueOf(drawers.get(drawerCounter).getId()).intValue();
				Button button = createDrawerButton(context, drawerId);
				buttons.add(button);
				drawerCounter++;
			} else {
				Button button = new Button(context);
				button.setVisibility(View.INVISIBLE);
				buttons.add(button);
			}
		}
		return buttons;
	}

	@SuppressLint("NewApi")
	private static Button createDrawerButton(Context context, int buttonId) {
		GradientDrawable border = new GradientDrawable();
		Button button = new Button(context);
		border.setStroke(1, Color.BLACK);

		if (buttonId > 0) {
			button.setId(buttonId);
		}

		button.setText(Integer.toString(drawerCounter++));
		button.setOnClickListener(new DrawerClickListener(context));
		button.setBackground(border);
		return button;
	}

	public static List<Button> createSelectorButtons(Context context, int width, int height) {
		List<Button> buttons = new ArrayList<>();
		for (int i = 0; i < width * height; i++) {
			Button button = createSelectorButton(context);
			buttons.add(button);
		}
		selectorButtons = buttons;
		return buttons;
	}

	private static Button createSelectorButton(Context context) {
		Button button = new Button(context);
		setButtonBackground(button, Color.LTGRAY);
		button.setId(0);
		button.setOnClickListener(new SelectDrawerToCreateListener());
		return button;
	}
	
	public static Button restoreSelectorButton(Context context, int id) {
		Button button = new Button(context);
		setButtonBackground(button, Color.LTGRAY);
		button.setId(id);
		button.setOnClickListener(new SelectDrawerToCreateListener());
		if (id == 1) {
			button.performClick();
		}
		return button;
	}
	
	@SuppressLint("NewApi")
	public static void setButtonBackground(Button button, int color) {
		GradientDrawable background = new GradientDrawable();
		background.setStroke(1, Color.WHITE);
		background.setColor(color);
		background.setAlpha(200);
		button.setBackground(background);
	}

	private static void resetDrawerCounter() {
		drawerCounter = 1;
	}
}
