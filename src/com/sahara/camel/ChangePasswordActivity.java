//package com.sahara.camel;
//
//import java.io.IOException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.JSONTokener;
//
//import android.app.Activity;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.sahara.camel.SignUpActivity.SignupResult;
//import com.sahara.camel.data.LoginUser;
//import com.sahara.camel.model.UserInfo;
//import com.sahara.camel.network.HttpUtils;
//import com.sahara.camel.network.UrlLocations;
//import com.sahara.camel.utils.Hex;
//
//public class ChangePasswordActivity extends Activity {
//
//	private TextView activityTitle;
//	private Button mButtonBack;
//	private Button mChangePassBtn;
//	private EditText mNewPass;
//	private EditText mNewPass2;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.activity_sign_up);
//		initTitleBar();
//
//		mChangePassBtn = (Button) findViewById(R.id.changepass_action_button);
//		mChangePassBtn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				String pass1 = mNewPass.getText().toString();
//				String pass2 = mNewPass2.getText().toString();
//
//				if (pass1.isEmpty()) {
//					Toast.makeText(ChangePasswordActivity.this, "请输入新密码",
//							Toast.LENGTH_SHORT).show();
//					mNewPass.requestFocus();
//					return;
//				}
//
//				if (pass2.isEmpty()) {
//					Toast.makeText(ChangePasswordActivity.this, "请再次输入新密码",
//							Toast.LENGTH_SHORT).show();
//					mNewPass2.requestFocus();
//					return;
//				}
//
//				if (!pass1.equals(pass2)) {
//					Toast.makeText(ChangePasswordActivity.this,
//							"两次输入的密码不匹配,请重新输入", Toast.LENGTH_SHORT).show();
//					mNewPass.setText("");
//					mNewPass2.setText("");
//					mNewPass.requestFocus();
//					return;
//				}
//			}
//
//		});
//
//	}
//
//	private void initTitleBar() {
//		activityTitle = (TextView) findViewById(R.id.textview_title);
//		activityTitle.setText("修改密码");
//
//		mButtonBack = (Button) findViewById(R.id.button_back);
//		mButtonBack.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				ChangePasswordActivity.this.finish();
//			}
//		});
//	}
//	
//	class ChangePassResult {
//		boolean isSuccess;
//		String msg;
//	}
//	
//	class ChangePassTask extends AsyncTask<Void, Void, ChangePassResult> {
//		
//		@Override
//		protected ChangePassResult doInBackground(Void... params) {
//			
//			ChangePassResult result = new ChangePassResult();
//			
//			UserInfo loginUser = LoginUser.getUser();
//			
//			// 密码的MD5
//			MessageDigest md5 = null;
//			try {
//				md5 = MessageDigest.getInstance("MD5");
//			} catch (NoSuchAlgorithmException e2) {
//				e2.printStackTrace();
//			}
//			md5.update(userPassword.getBytes());
//			byte[] m = md5.digest();// 加密
//			String md5ForPassword = Hex.encodeHexStr(m, false);
//			
//			String url = UrlLocations.getEditPwdUrl();
//			
//			
//			
//			String urlSpec = Uri.parse(url).buildUpon()
//					.appendQueryParameter("companyid", loginUser.getCompanyId())
//					.appendQueryParameter("token", loginUser.getToken())
//					.appendQueryParameter("account", userAccount)
//					.appendQueryParameter("newpwd", md5ForPassword)
//					.appendQueryParameter("usertype", md5ForPassword)
//					.build().toString();
//
//			String response = null;
//			try {
//				response = HttpUtils.get(urlSpec);
//			} catch (IOException e1) {
//				Log.e(TAG, "Fail to access URL " + urlSpec, e1);
//			}
//
//			if (response != null) {
//
//				JSONTokener jsonParser = new JSONTokener(response);
//				try {
//					JSONObject res = (JSONObject) jsonParser.nextValue();
//					String status = res.getString("status");
//					String summary = res.getString("summary");
//					if (status.equals("00000")) {
//						
//						result.isSuccess = true;
//						result.msg = "注册成功";
//						
//					} else {
//						result.isSuccess = false;
//						result.msg = summary;
//					}
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//					result.isSuccess = false;
//					result.msg = "消息解析错误";
//				}
//			}
//			
//			return result;
//		}
//		
//		@Override
//		protected void onPostExecute(SignupResult result) {
//			
//			if(result.isSuccess) {
//				Toast.makeText(SignUpActivity.this, result.msg, Toast.LENGTH_SHORT).show();
//				SignUpActivity.this.finish();
//			} else {
//				Toast.makeText(SignUpActivity.this, result.msg, Toast.LENGTH_SHORT).show();
//			}		
//		}
//	}
//
//}
