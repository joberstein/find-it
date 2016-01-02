package com.jro.findit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jro.dao.FindItContract.UserTable;
import com.jro.dao.FindItDbHelper;
import com.jro.data.User;

public class SignUpActivity extends Activity {
	FindItDbHelper dbInstance;
	SharedPreferences preferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		dbInstance = FindItDbHelper.getInstance(this);
		preferences = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
	}
	
	public void saveCredentials(View view) {
		EditText emailInput = (EditText) findViewById(R.id.newEmail);
		EditText usernameInput = (EditText) findViewById(R.id.newUsername);
		EditText passwordInput = (EditText) findViewById(R.id.newPassword);
		EditText confirmInput = (EditText) findViewById(R.id.confirmPassword);
		String email = emailInput.getText().toString();
		String username = usernameInput.getText().toString();
		String password = passwordInput.getText().toString();
		String confirm = confirmInput.getText().toString();
		
		Toast toast = Toast.makeText(getApplicationContext(), "Sign up successful!", Toast.LENGTH_SHORT);
		
		if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
			toast.setText("Missing information");
		}
		else if (!password.equals(confirm)) {
			toast.setText("Passwords don't match");
		}
		else if (isUsernameTaken(username)) {
			toast.setText("Username already taken");
		}
		else {
			User newUser = new User(email, username, password);
			long newUserId = dbInstance.saveUser(newUser);
			newUser.setId(newUserId);
			Editor editor = preferences.edit();
			editor.putLong("userId", newUserId);
			editor.commit();
			finish();
		}
		toast.show();
	}
	
	public boolean isUsernameTaken(String username) {
		Cursor usernames = dbInstance.getUsernames();
		if (usernames != null) {
			usernames.moveToFirst();
		}
		
		while (!usernames.isAfterLast()) {
			String usernameValue = usernames.getString(usernames.getColumnIndex(UserTable.COLUMN_NAME_USERNAME));
			if (username.toLowerCase().equals(usernameValue.toLowerCase())) {
				return true;
			}
			usernames.moveToNext();
		}
		return false;
	}
}
