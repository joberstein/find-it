package com.jro.findit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jro.dao.FindItDbHelper;

public class RemoveFurniture extends Activity {
	FindItDbHelper db;
	SharedPreferences preferences;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = FindItDbHelper.getInstance(this);
		preferences = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
		setContentView(R.layout.create_furniture);
	}

	//	public void onDraw(Canvas canvas) {
	//		Paint myPaint = new Paint();
	//		myPaint.setColor(Color.rgb(0, 0, 0));
	//		myPaint.setStrokeWidth(10);
	//		canvas.drawRect(100, 100, 200, 200, myPaint);
	//	}

	public void removeFurniture(View view) {
		super.finish();
	}

	public void goBackToPreviousActivity(View view) {
		finish();
	}
}
