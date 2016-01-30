package com.findit.android.listener;

import com.findit.android.utils.TableCreator;

import android.annotation.SuppressLint;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SelectAllDrawersListener implements OnCheckedChangeListener {
	
	@SuppressLint("NewApi")
	@Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    	int selectAll = (isChecked) ? 1 : 0;
    	for (Button button : TableCreator.getSelectorButtons()) {
    		if (button.getId() != selectAll) {
    			button.performClick();
    		}
    	}
    	if (isChecked) {
    		buttonView.setText("Exclude All Drawers");
    	}
    	else {
    		buttonView.setText("Include All Drawers");
    	}
	}
}
