package com.findit.android.activity.profile;

import com.findit.android.R;
import com.findit.android.data.User;
import com.findit.android.db.dao.UserDao;
import com.findit.android.db.dao.UserDaoImpl;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity {
	UserDao userDao;
	SharedPreferences preferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		userDao = UserDaoImpl.getInstance(this);
		preferences = getSharedPreferences(Login.PREFS_NAME, Context.MODE_PRIVATE);
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
			long newUserId = userDao.saveUser(newUser);
			newUser.setId(newUserId);
			Editor editor = preferences.edit();
			editor.putLong(Login.USER_ID, newUserId);
			editor.commit();
			finish();
		}
		toast.show();
	}
	
	public boolean isUsernameTaken(String username) {
		Cursor matchingUser = userDao.getUserByUsername(username);
		return (matchingUser.getCount() > 0);
		
		
//		Cursor usernames = userDao.getAllUsers();
//		if (usernames != null) {
//			usernames.moveToFirst();
//		}
//		
//		while (!usernames.isAfterLast()) {
//			String usernameValue = usernames.getString(usernames.getColumnIndex(UserTable.COLUMN_NAME_USERNAME));
//			if (username.toLowerCase(Locale.US).equals(usernameValue.toLowerCase(Locale.US))) {
//				return true;
//			}
//			usernames.moveToNext();
//		}
//		return false;
	}
}
