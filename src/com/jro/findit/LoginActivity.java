package com.jro.findit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jro.dao.FindItContract.UserTable;
import com.jro.dao.FindItDbHelper;

public class LoginActivity extends Activity {
	public static final String PREFS_NAME = "MyPrefsFile";
	
	FindItDbHelper db;
	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
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
		if (isUserAlreadyLoggedIn(preferences.getLong("userId", -1))) {
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
				saveUserInformation(idValue, username, passwordValue);
				forwardToViewFurniture();
				return;
			}
			matchingUsers.moveToNext();
		}

		Toast toast = Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT);
		toast.show();
	}

	public void forwardToSignUp(View view) {
		Intent intent = new Intent(this, SignUpActivity.class);
		startActivity(intent);
	}
	
	private void saveUserInformation(long id, String username, String password) {
		Editor editor = preferences.edit();
		editor.putLong("userId", id);
		editor.putString("username", username);
		editor.putString("password", password);
		editor.commit();
	}

	private void forwardToViewFurniture() {
		Intent intent = new Intent(this, ViewFurniture.class);
		startActivity(intent);
		finish();
	}

}
