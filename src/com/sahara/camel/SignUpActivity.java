package com.sahara.camel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends Activity {

	private TextView activityTitle;
	private Button mButtonBack;
	private Button mButtonSignUp;
	private EditText mUserAccount;
	private EditText mUserPassword;
	private EditText mUserPasswordAgain;
	private EditText mUserCompany;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sign_up);
		initTitleBar();

		mUserAccount = (EditText) findViewById(R.id.signup_user_account);
		mUserPassword = (EditText) findViewById(R.id.signup_user_password);
		mUserPasswordAgain = (EditText) findViewById(R.id.signup_user_password_again);
		mUserCompany = (EditText) findViewById(R.id.signup_user_company);

		mButtonSignUp = (Button) findViewById(R.id.signup_action_button);
		mButtonSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String userAccount = mUserAccount.getText().toString().trim();
				String userPassword = mUserPassword.getText().toString();
				String userPasswordAgain = mUserPasswordAgain.getText()
						.toString();
				String userCompany = mUserCompany.getText().toString().trim();

				if (userAccount.isEmpty()) {
					Toast.makeText(SignUpActivity.this, "�������ֻ��˺�",
							Toast.LENGTH_SHORT).show();
					mUserAccount.requestFocus();
					return;
				}
				
				if(userPassword.isEmpty()) {
					Toast.makeText(SignUpActivity.this, "�������˺�����",
							Toast.LENGTH_SHORT).show();
					mUserPassword.requestFocus();
					return;
				}
				
				if(userPasswordAgain.isEmpty()) {
					Toast.makeText(SignUpActivity.this, "���ٴ������˺�����",
							Toast.LENGTH_SHORT).show();
					mUserPasswordAgain.requestFocus();
					return;
				}
				
				if(userCompany.isEmpty()) {
					Toast.makeText(SignUpActivity.this, "�����빫˾����",
							Toast.LENGTH_SHORT).show();
					mUserCompany.requestFocus();
					return;
				}
				

				if (!userPassword.equals(userPasswordAgain)) {
					Toast.makeText(SignUpActivity.this, "������������벻ƥ��,����������",
							Toast.LENGTH_SHORT).show();
					mUserPassword.setText("");
					mUserPasswordAgain.setText("");
					mUserPassword.requestFocus();
					return;
				}

			}
		});

	}

	private void initTitleBar() {
		activityTitle = (TextView) findViewById(R.id.textview_title);
		activityTitle.setText("�˺�ע��");

		mButtonBack = (Button) findViewById(R.id.button_back);
		mButtonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SignUpActivity.this.finish();
			}
		});
	}

}
