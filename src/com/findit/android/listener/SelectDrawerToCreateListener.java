package com.findit.android.listener;

import com.findit.android.utils.TableCreator;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectDrawerToCreateListener implements OnClickListener {
	private boolean selected;
	
	public SelectDrawerToCreateListener() {
		this.selected = false;
	}
	
	@Override
	public void onClick(View view) {
		this.toggleSelected();
		Button button = (Button) view;
		int id = (this.selected) ? 1 : 0;
		int color = (this.selected) ? Color.GREEN : Color.LTGRAY;
		
		button.setId(id);
		TableCreator.setButtonBackground(button, color);
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
