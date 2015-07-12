package com.sahara.camel;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahara.camel.data.LoginUser;
import com.sahara.camel.model.UserInfo;
import com.sahara.camel.network.HttpUtils;
import com.sahara.camel.network.UrlLocations;

public class NfcSigninHandler {

	private static final String TAG = "NfcSigninHandler";

	private Context ctx;
	private String nfcData;
	private ProgressDialog inProgressDialog;

	public NfcSigninHandler(String nfcData, Context context) {
		this.nfcData = nfcData;
		this.ctx = context;
	}

	public void doNfcSignin() {
		showProgressDialog();
		new UploadNfcDataTask().execute();
	}

	private void showProgressDialog() {

		this.inProgressDialog = new ProgressDialog(ctx);
		// ���ý�������񣬷��ΪԲ�Σ���ת��
		inProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// ����ProgressDialog ����
		// m_pDialog.setTitle("��ʾ");
		// ����ProgressDialog ��ʾ��Ϣ
		inProgressDialog.setMessage("�����ϴ�");
		// ����ProgressDialog ����ͼ��
		// m_pDialog.setIcon(android.R.drawable.ic_dialog_email);
		// ����ProgressDialog �Ľ������Ƿ���ȷ
		inProgressDialog.setIndeterminate(true);
		// ����ProgressDialog �Ƿ���԰��˻ذ���ȡ��
		inProgressDialog.setCancelable(false);
		// ��ProgressDialog��ʾ
		inProgressDialog.show();

	}

	private class UploadNfcDataTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {

			String result = null;
			UserInfo user = LoginUser.getUser();

			String url = UrlLocations.getWorkSigningUrl();

			String urlSpec = Uri
					.parse(url)
					.buildUpon()
					.appendQueryParameter("companyid", user.getCompanyId())
					.appendQueryParameter("token", user.getToken())
					.appendQueryParameter("empid", user.getEmpId())
					.appendQueryParameter("atttype",
							SigninType.NFC.getType() + "")
					.appendQueryParameter("attinfo", nfcData)
					.appendQueryParameter("atttime", "").build().toString();

			String response = null;
			try {
				response = HttpUtils.get(urlSpec);
			} catch (IOException e1) {
				Log.e(TAG, "��������쳣", e1);
				result = "��������쳣�����Ժ�����";
				return result;
			}

			if (response != null) {

				JSONTokener jsonParser = new JSONTokener(response);
				try {
					JSONObject res = (JSONObject) jsonParser.nextValue();
					String status = res.getString("status");
					if (status.equals("00000")) {
						// SUCCESS
						result = "���ڳɹ�";
					} else {
						result = "����ʧ��: " + res.getString("summary");
					}

				} catch (JSONException e) {
					Log.e(TAG, "������Ϣ��������", e);
					result = "��Ϣ��������";
				}
			}

			return result;
		}

		@Override
		protected void onPostExecute(String msg) {

			inProgressDialog.cancel();

			String title = null;
			String detail = null;
			int iconId = -1;
			if (msg.equals("���ڳɹ�")) {
				title = "���ڳɹ�";
				iconId = R.drawable.ic_succ;
				detail = "";
			} else {
				title = "����ʧ��";
				iconId = R.drawable.ic_fail;
				detail = msg;
			}

			LayoutInflater inflater = LayoutInflater.from(ctx);
			View v = inflater.inflate(R.layout.dialog_signin_result, null);

			ImageView resultIcon = (ImageView) v
					.findViewById(R.id.signin_result_icon);
			resultIcon.setImageResource(iconId);

			TextView titleView = (TextView) v
					.findViewById(R.id.signin_result_title);
			titleView.setText(title);

			TextView detailView = (TextView) v
					.findViewById(R.id.signin_result_detail);
			detailView.setText(detail);

			new AlertDialog.Builder(ctx).setView(v)
					.setPositiveButton("ȷ��", null).show();
		}
	}

}
