package com.findit.android.listener;

import java.util.List;

import com.findit.android.utils.TableCreator;

import android.graphics.Color;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SelectAllDrawersListener implements OnCheckedChangeListener {
	private static boolean[] savedStateSelected;
	
	@Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    	if (isChecked) {
     		List<Button> selectorButtons = TableCreator.getSelectorButtons();
     		int gridSize = selectorButtons.size();
     		savedStateSelected = new boolean[gridSize];
    		for (int i = 0; i < gridSize; i++) {
    			Button button = selectorButtons.get(i);
    			boolean isButtonSelected = this.isButtonSelected(button);
    			savedStateSelected[i] = isButtonSelected;
    			
    			if (!isButtonSelected) {
        			button.performClick();
        		}

    			TableCreator.setButtonBackground(button, Color.GREEN);
    			button.setOnClickListener(null);
    		}
    	}
    	else {
    		List<Button> selectorButtons = TableCreator.getSelectorButtons();
    		for (int i = 0; i < savedStateSelected.length; i++) {
    			Button button = selectorButtons.get(i);
    			TableCreator.setButtonBackground(button, Color.GREEN);
    			SelectDrawerToCreateListener clickListener = new SelectDrawerToCreateListener();
    			clickListener.setSelected(true);
    			button.setOnClickListener(clickListener);
    			
    			if (!savedStateSelected[i]) {
        			button.performClick();
        		}
    		}
    	}
	}
	
	public static boolean[] getSavedStateSelected() {
		return savedStateSelected;
	}
	
	public static void setSavedStateSelected(boolean[] newSavedStateSelected) {
		savedStateSelected = newSavedStateSelected;
	}
	
	private boolean isButtonSelected(Button button) {
		return button.getId() == 1;
	}
	
}
