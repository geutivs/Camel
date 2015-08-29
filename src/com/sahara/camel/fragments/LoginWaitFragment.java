package com.sahara.camel.fragments;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sahara.camel.MainActivity;
import com.sahara.camel.R;
import com.sahara.camel.data.LoginUser;
import com.sahara.camel.model.UserInfo;
import com.sahara.camel.network.HttpResult;
import com.sahara.camel.network.HttpUtils;
import com.sahara.camel.network.UrlLocations;

public class LoginWaitFragment extends Fragment {

	private static final String TAG = "LoginWaitFragment";

	ProgressBar loginProgress;
	private String username;
	private String password;
	private OnLoginFailureListener listener;

	public LoginWaitFragment(String username, String password,
			OnLoginFailureListener listener) {
		this.username = username;
		this.password = password;
		this.listener = listener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_login_wait, container,
				false);

		loginProgress = (ProgressBar) v.findViewById(R.id.progress_login);

		new LoginTask().execute();

		return v;
	}

	class LoginTaskResult {
		UserInfo user;
		String msg;
		boolean isSuccess;
	}

	class LoginTask extends AsyncTask<Void, Void, LoginTaskResult> {

		@Override
		protected LoginTaskResult doInBackground(Void... params) {

			LoginTaskResult result = new LoginTaskResult();

			String url = UrlLocations.getLoginUrl();
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("phone_number", username);
			paramMap.put("password", password);

			HttpResult r = null;
			try {
				r = HttpUtils.post(url, paramMap, null);
			} catch (IOException e1) {
				Log.e(TAG, "Fail to access URL " + url, e1);
				result.isSuccess = false;
				result.msg = "网络访问异常";
				return result;
			}

			if (r != null) {

				if (r.isSuccess()) {

					JSONTokener jsonParser = new JSONTokener(r.getData());
					try {
						JSONObject res = (JSONObject) jsonParser.nextValue();
						
						LoginUser.setAuthToken(res.getString("auth_token"));
						
						JSONObject userRes = res.getJSONObject("user");
						UserInfo user = new UserInfo();
						user.setPhoneNumber(userRes.getString("phone_number"));
						user.setId(userRes.getInt("id"));
						user.setRole(userRes.getInt("role"));
						user.setNickname(userRes.getString("nickname"));
						user.setGender(userRes.getInt("gender"));
						user.setProfileImageUrl(userRes.getString("profile_image_url"));
						user.setCompanyId(userRes.getString("company_id"));
						user.setEmail(userRes.getString("email"));
						user.setLandline(userRes.getString("landline"));
						user.setReferrerPhoneNumber(userRes.getString("referrer_phone_number"));
						user.setCreateTime(userRes.getLong("create_time"));
						
						LoginUser.setUser(user);
						
						result.isSuccess = true;
						result.msg = "登录成功";

					} catch (JSONException e) {
						result.isSuccess = false;
						result.msg = "消息解析错误";
					}

				} else {
					result.isSuccess = false;
					result.msg = r.getMessage();
				}
			}

			return result;
		}

		@Override
		protected void onPostExecute(LoginTaskResult result) {

			String msg = result.msg;

			if (result.isSuccess) {
				Intent i = new Intent(getActivity(), MainActivity.class);
				startActivity(i);
			} else {
				if (listener != null)
					listener.onLoginFailure(msg);
			}
		}

	}

	public interface OnLoginFailureListener {
		void onLoginFailure(String msg);
	}

}
