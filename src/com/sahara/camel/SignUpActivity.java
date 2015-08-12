package com.sahara.camel;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sahara.camel.network.HttpUtils;
import com.sahara.camel.network.UrlLocations;
import com.sahara.camel.utils.Hex;

public class SignUpActivity extends Activity {
	
	private static final String TAG = "SignUpActivity";

	private TextView activityTitle;
	private Button mButtonBack;
	private Button mButtonSignUp;
	private EditText mUserName;
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
		mUserName = (EditText) findViewById(R.id.signup_user_name);
		mUserPassword = (EditText) findViewById(R.id.signup_user_password);
		mUserPasswordAgain = (EditText) findViewById(R.id.signup_user_password_again);
		mUserCompany = (EditText) findViewById(R.id.signup_user_company);

		mButtonSignUp = (Button) findViewById(R.id.signup_action_button);
		mButtonSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String userAccount = mUserAccount.getText().toString().trim();
				String userName = mUserName.getText().toString().trim();
				String userPassword = mUserPassword.getText().toString();
				String userPasswordAgain = mUserPasswordAgain.getText()
						.toString();
				String userCompany = mUserCompany.getText().toString().trim();

				if (userAccount.isEmpty()) {
					Toast.makeText(SignUpActivity.this, "请输入手机账号",
							Toast.LENGTH_SHORT).show();
					mUserAccount.requestFocus();
					return;
				}
				
				if (userName.isEmpty()) {
					Toast.makeText(SignUpActivity.this, "请输入用户姓名",
							Toast.LENGTH_SHORT).show();
					mUserName.requestFocus();
					return;
				}
				
				if(userPassword.isEmpty()) {
					Toast.makeText(SignUpActivity.this, "请输入账号密码",
							Toast.LENGTH_SHORT).show();
					mUserPassword.requestFocus();
					return;
				}
				
				if(userPasswordAgain.isEmpty()) {
					Toast.makeText(SignUpActivity.this, "请再次输入账号密码",
							Toast.LENGTH_SHORT).show();
					mUserPasswordAgain.requestFocus();
					return;
				}
				
				if(userCompany.isEmpty()) {
					Toast.makeText(SignUpActivity.this, "请输入公司名称",
							Toast.LENGTH_SHORT).show();
					mUserCompany.requestFocus();
					return;
				}
				

				if (!userPassword.equals(userPasswordAgain)) {
					Toast.makeText(SignUpActivity.this, "两次输入的密码不匹配,请重新输入",
							Toast.LENGTH_SHORT).show();
					mUserPassword.setText("");
					mUserPasswordAgain.setText("");
					mUserPassword.requestFocus();
					return;
				}
				
				new SignupTask().execute();
			}
		});

	}

	private void initTitleBar() {
		activityTitle = (TextView) findViewById(R.id.textview_title);
		activityTitle.setText("账号注册");

		mButtonBack = (Button) findViewById(R.id.button_back);
		mButtonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SignUpActivity.this.finish();
			}
		});
	}
	
	class SignupResult {
		boolean isSuccess;
		String msg;
	}
	
	class SignupTask extends AsyncTask<Void, Void, SignupResult> {
		
		@Override
		protected SignupResult doInBackground(Void... params) {
			
			String userAccount = mUserAccount.getText().toString().trim();
			String userName = mUserName.getText().toString().trim();
			String userPassword = mUserPassword.getText().toString();
			String userCompany = mUserCompany.getText().toString().trim();
			
			SignupResult result = new SignupResult();
			
			// 密码的MD5
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e2) {
				e2.printStackTrace();
			}
			md5.update(userPassword.getBytes());
			byte[] m = md5.digest();// 加密
			String md5ForPassword = Hex.encodeHexStr(m, false);
			
			String url = UrlLocations.getSignUpUrl();
			
			String urlSpec = Uri.parse(url).buildUpon()
					.appendQueryParameter("companyname", userCompany)
					.appendQueryParameter("username", userName)
					.appendQueryParameter("account", userAccount)
					.appendQueryParameter("userpwd", md5ForPassword)
					.build().toString();

			String response = null;
			try {
				response = HttpUtils.get(urlSpec);
			} catch (IOException e1) {
				Log.e(TAG, "Fail to access URL " + urlSpec, e1);
			}

			if (response != null) {

				JSONTokener jsonParser = new JSONTokener(response);
				try {
					JSONObject res = (JSONObject) jsonParser.nextValue();
					String status = res.getString("status");
					String summary = res.getString("summary");
					if (status.equals("00000")) {
						
						result.isSuccess = true;
						result.msg = "注册成功";
						
					} else {
						result.isSuccess = false;
						result.msg = summary;
					}

				} catch (JSONException e) {
					e.printStackTrace();
					result.isSuccess = false;
					result.msg = "消息解析错误";
				}
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(SignupResult result) {
			
			if(result.isSuccess) {
				Toast.makeText(SignUpActivity.this, result.msg, Toast.LENGTH_SHORT).show();
				SignUpActivity.this.finish();
			} else {
				Toast.makeText(SignUpActivity.this, result.msg, Toast.LENGTH_SHORT).show();
			}		
		}
	}
}