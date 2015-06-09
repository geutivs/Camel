package com.sahara.camel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private TextView mForgetPass;
	private TextView mSignup;
	private Button mSigninButton;
	private EditText mUserAccount;
	private EditText mUserPass;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		mSigninButton = (Button)findViewById(R.id.login_signin_button);
		mUserAccount = (EditText)findViewById(R.id.login_username_edit);
		mUserPass =(EditText)findViewById(R.id.login_password_edit);
		
		mForgetPass = (TextView)findViewById(R.id.login_forget_pass);
		mForgetPass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(LoginActivity.this, "Íü¼ÇÃÜÂë", Toast.LENGTH_SHORT).show();				
			}
			
		});
		
		mSignup = (TextView)findViewById(R.id.login_signup);
		mSignup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(LoginActivity.this, "Á¢¼´×¢²á", Toast.LENGTH_SHORT).show();
			}
			
		});
	}

}
