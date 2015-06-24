package com.sahara.camel.fragments;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sahara.camel.MainActivity;
import com.sahara.camel.R;
import com.sahara.camel.model.UserInfo;
import com.sahara.camel.network.HttpUtils;
import com.sahara.camel.network.UrlLocations;

public class LoginWaitFragment extends Fragment {

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
	}

	class LoginTask extends AsyncTask<Void, Void, LoginTaskResult> {
		
		@Override
		protected LoginTaskResult doInBackground(Void... params) {
			
			LoginTaskResult result = new LoginTaskResult();

			String url = UrlLocations.getLoginUrl();

			String urlSpec = Uri.parse(url).buildUpon()
					.appendQueryParameter("utel", username)
					.appendQueryParameter("upwd", password).build().toString();

			String response = null;
			try {
				response = HttpUtils.get(urlSpec);
			} catch (IOException e1) {
				e1.printStackTrace();
				result.msg = "Õ¯¬Á∑√Œ “Ï≥££¨«Î…‘∫Ú‘Ÿ ‘";
				return result;
			}

			UserInfo user = null;
			if (response != null) {

				JSONTokener jsonParser = new JSONTokener(response);
				try {
					JSONObject res = (JSONObject) jsonParser.nextValue();
					String status = res.getString("status");
					if (status.equals("00000")) {
						// SUCCESS
						user = new UserInfo();

						user.setUserName(res.getString("username"));
						user.setEmpId(res.getString("empid"));
						user.setUserType(res.getString("usertype"));
						user.setToken(res.getString("token"));
						user.setDeptId(res.getString("deptcode"));
						user.setDeptName(res.getString("deptname"));
						user.setCardNo(res.getString("cardno"));
						user.setCardMoney(res.getString("cardmoney"));
						user.setCompanyId(res.getString("companyid"));
						user.setCompanyName(res.getString("companyname"));

						result.user = user;
						result.msg = "µ«¬º≥…π¶";
					} else {
						result.msg = "µ«¬º ß∞‹";
					}

				} catch (JSONException e) {
					e.printStackTrace();
					result.msg = "œ˚œ¢Ω‚Œˆ¥ÌŒÛ";
				}
			}

			return result;
		}

		@Override
		protected void onPostExecute(LoginTaskResult result) {
			
			UserInfo user = result.user;
			String msg = result.msg;
			
			if (user != null) {
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
