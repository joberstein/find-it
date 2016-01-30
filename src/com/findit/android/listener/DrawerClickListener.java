package com.findit.android.listener;

import com.findit.android.activity.ViewDrawer;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DrawerClickListener implements OnClickListener {
	private Context context;
	
	public DrawerClickListener(Context context) {
		this.context = context;
	}
	
	@Override
	public void onClick(View view) {
		Button button = (Button) view;
		Intent intent = new Intent(context, ViewDrawer.class);
		intent.putExtra("drawerId", Integer.valueOf(button.getId()).longValue());
		intent.putExtra("defaultName", "Drawer " + button.getText());
		context.startActivity(intent);
	}
}
