package com.sahara.camel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
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

import com.sahara.camel.data.LoginUser;
import com.sahara.camel.network.HttpResult;
import com.sahara.camel.network.HttpUtils;
import com.sahara.camel.network.UrlLocations;

public class CreateFirmActivity extends Activity {

	private TextView activityTitle;
	private Button mButtonBack;

	private Button mCreateFirmConfirm;
	private EditText mFirmName;

	private static final String TAG = "CreateFirmActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_create_firm);
		initTitleBar();

		mFirmName = (EditText) findViewById(R.id.edit_firm_name);

		mCreateFirmConfirm = (Button) findViewById(R.id.button_create_firm_confirm);
		mCreateFirmConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String firmName = mFirmName.getText().toString().trim();
				if (firmName.isEmpty()) {
					Toast.makeText(CreateFirmActivity.this, "请输入企业名称",
							Toast.LENGTH_SHORT).show();
					mFirmName.requestFocus();
					return;
				}

				new CreateFirmTask().execute();
			}

		});

	}

	private void initTitleBar() {

		activityTitle = (TextView) findViewById(R.id.textview_title);
		activityTitle.setText("创建企业");

		mButtonBack = (Button) findViewById(R.id.button_back);
		mButtonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CreateFirmActivity.this.finish();
			}
		});
	}

	class CreateFirmTaskResult {
		int firmId;
		String msg;
		boolean isSuccess;
	}

	class CreateFirmTask extends AsyncTask<Void, Void, CreateFirmTaskResult> {

		@Override
		protected CreateFirmTaskResult doInBackground(Void... p) {

			CreateFirmTaskResult result = new CreateFirmTaskResult();

			String firmName = mFirmName.getText().toString().trim();

			String url = UrlLocations.getCreateFirmUrl();
			Map<String, String> params = new HashMap<String, String>();
			params.put("creator", LoginUser.getUser().getId()+"");
			params.put("name", firmName);

			HttpResult r = null;
			try {
				r = HttpUtils.post(url, params, LoginUser.getAuthToken());
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

						int firmId = res.getInt("id");

						result.isSuccess = true;
						result.msg = "创建企业成功";
						result.firmId = firmId;

					} catch (JSONException e) {
						result.isSuccess = false;
						result.msg = "消息解析错误";
					}

				} else {
					result.isSuccess = false;
					result.msg = "创建企业失败\n" + r.getMessage();
				}
			}

			return result;
		}

		@Override
		protected void onPostExecute(CreateFirmTaskResult result) {

			if (result.isSuccess) {
				Toast.makeText(CreateFirmActivity.this, result.msg,
						Toast.LENGTH_SHORT).show();
				CreateFirmActivity.this.finish();
			} else {
				Toast.makeText(CreateFirmActivity.this, result.msg,
						Toast.LENGTH_SHORT).show();
			}
		}

	}

}
