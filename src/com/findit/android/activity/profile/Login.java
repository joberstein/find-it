package com.findit.android.activity.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.findit.android.activity.ViewFurniture;
import com.findit.android.dao.FindItDbHelper;
import com.findit.android.dao.FindItContract.UserTable;
import com.findit.android.R;

public class Login extends Activity {
	public static final String PREFS_NAME = "MyPrefsFile";
	public static final String USER_ID = "userId";
	
	FindItDbHelper db;
	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		db = FindItDbHelper.getInstance(this);
		preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		
		loginReturningUser();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		loginReturningUser();
	}
	
	private void loginReturningUser() {
		if (isUserAlreadyLoggedIn(preferences.getLong(USER_ID, -1))) {
			forwardToViewFurniture();
		}
	}
	
	private boolean isUserAlreadyLoggedIn(long userId) {
		return (userId != -1) && (db.getUser(userId).getCount() == 1);
	}

	public void validateLogin(View view) {
		EditText usernameInput = (EditText) findViewById(R.id.usernameText);
		EditText passwordInput = (EditText) findViewById(R.id.passwordText);
		String username = usernameInput.getText().toString();
		String password = passwordInput.getText().toString();

		Cursor matchingUsers = db.getUserByUsername(username);
		if (matchingUsers != null) {
			matchingUsers.moveToFirst();
		}

		while (!matchingUsers.isAfterLast()) {
			String passwordValue = matchingUsers.getString(matchingUsers.getColumnIndex(UserTable.COLUMN_NAME_PASSWORD));
			if (password.equals(passwordValue)) {
				long idValue = matchingUsers.getLong(matchingUsers.getColumnIndex(UserTable._ID));
				rememberUserId(idValue);
				forwardToViewFurniture();
				return;
			}
			matchingUsers.moveToNext();
		}

		Toast toast = Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT);
		toast.show();
	}

	public void forwardToSignUp(View view) {
		Intent intent = new Intent(this, SignUp.class);
		startActivity(intent);
	}
	
	private void rememberUserId(long id) {
		Editor editor = preferences.edit();
		editor.putLong(USER_ID, id);
		editor.commit();
	}

	private void forwardToViewFurniture() {
		Intent intent = new Intent(this, ViewFurniture.class);
		startActivity(intent);
		finish();
	}

}
