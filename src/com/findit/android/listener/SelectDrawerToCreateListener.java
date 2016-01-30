package com.findit.android.listener;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectDrawerToCreateListener implements OnClickListener {
	private boolean selected;
	
	public SelectDrawerToCreateListener() {
		this.selected = false;
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onClick(View view) {
		this.toggleSelected();
		Button button = (Button) view;
		GradientDrawable border = new GradientDrawable();
		border.setStroke(1, Color.WHITE);
		
		if (this.selected) {
			button.setId(1);
			border.setColor(Color.GREEN);
			border.setAlpha(200);
		}
		else {
			button.setId(0);
			border.setColor(Color.LTGRAY);
		}
		button.setBackground(border);
	}
	
	public boolean getSelected() {
		return this.selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void toggleSelected() {
		this.selected = !this.selected;
	}
}
