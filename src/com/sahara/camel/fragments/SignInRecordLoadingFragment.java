package com.sahara.camel.fragments;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sahara.camel.R;
import com.sahara.camel.data.LoginUser;
import com.sahara.camel.model.UserInfo;
import com.sahara.camel.network.HttpUtils;
import com.sahara.camel.network.UrlLocations;

public class SignInRecordLoadingFragment extends Fragment {
	
	private String TAG = "SignInRecordLoadingFragment";

	TextView loadingHint;
	ProgressBar loadingBar;
	OnSignInRecordLoadedListener listener;

	public SignInRecordLoadingFragment(OnSignInRecordLoadedListener onSignInRecordLoadedListener) {
		this.listener = onSignInRecordLoadedListener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_loading, container,
				false);
		loadingBar = (ProgressBar) view.findViewById(R.id.loading_bar);
		loadingHint = (TextView) view.findViewById(R.id.loading_hint);

		new LoadSignInRecordTask().execute();

		return view;
	}

	class LoadSignInRecordResult {
		String msg;
		List<Map<String, Object>> signInRecords;
	}

	class LoadSignInRecordTask extends
			AsyncTask<Void, Void, LoadSignInRecordResult> {

		@Override
		protected LoadSignInRecordResult doInBackground(Void... arg0) {

			LoadSignInRecordResult result = new LoadSignInRecordResult();

			String url = UrlLocations.getSignInRecordQueryUrl();

			UserInfo loginUser = LoginUser.getUser();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String today = dateFormat.format(cal.getTime());

			cal.add(Calendar.DATE, -30);
			String last30Day = dateFormat.format(cal.getTime());

			String urlSpec = Uri
					.parse(url)
					.buildUpon()
					.appendQueryParameter("companyid", loginUser.getCompanyId())
					.appendQueryParameter("token", loginUser.getToken())
					.appendQueryParameter("empid", loginUser.getEmpId())
					.appendQueryParameter("pindex", "0")
					.appendQueryParameter("psize", "128")
					.appendQueryParameter("bdate", last30Day)
					.appendQueryParameter("edate", today).build().toString();
			
			Log.e(TAG, urlSpec);

			String response = null;
			try {
				response = HttpUtils.get(urlSpec);
			} catch (IOException e1) {
				Log.e(TAG, e1.getMessage(), e1);
				result.msg = "Õ¯¬Á∑√Œ “Ï≥££¨«Î…‘∫Ú‘Ÿ ‘";
				return result;
			}

			if (response != null) {

				JSONTokener jsonParser = new JSONTokener(response);
				try {
					JSONObject res = (JSONObject) jsonParser.nextValue();
					String status = res.getString("status");
					String summary = res.getString("summary");
					if (status.equals("00000")) {

						List<Map<String, Object>> signInRecords = new LinkedList<Map<String, Object>>();
						JSONArray recordList = res.getJSONArray("records");
						for (int i = 0; i < recordList.length(); i++) {
							JSONObject recordJ = (JSONObject) recordList
									.get(i);
							Map<String, Object> record = new HashMap<String, Object>();
							record.put("signInSummary", recordJ.get("summary"));
							record.put("signInDate", recordJ.get("attdate"));
							record.put("signInTime", recordJ.get("intime"));
							record.put("signOutTime", recordJ.get("outtime"));
							
							signInRecords.add(0, record);
						}

						result.signInRecords = signInRecords;
						result.msg = "≥…π¶";
					} else {
						result.msg = "º”‘ÿøº«⁄º«¬º ß∞‹: " + summary;
					}

				} catch (JSONException e) {
					Log.e(TAG, e.getMessage(), e);
					result.msg = "œ˚œ¢Ω‚Œˆ¥ÌŒÛ";
				}
			}
			return result;
		}

		@Override
		protected void onPostExecute(LoadSignInRecordResult result) {

			List<Map<String, Object>> signInRecordList = result.signInRecords;
			String msg = result.msg;

			if (signInRecordList == null) {
				loadingBar.setVisibility(View.GONE);
				loadingHint.setText(msg);
			} else {
				listener.onLoaded(signInRecordList);
			}
		}
	}

	public interface OnSignInRecordLoadedListener {
		void onLoaded(List<Map<String, Object>> signInRecord);
	}

}
