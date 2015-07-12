package com.sahara.camel;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahara.camel.data.LoginUser;
import com.sahara.camel.model.UserInfo;
import com.sahara.camel.network.HttpUtils;
import com.sahara.camel.network.UrlLocations;

public class LocationSigninHandler {

	private static final String TAG = "LocationSigninHandler";

	private Context ctx;
	private ProgressDialog inProgressDialog;
	private LocationManager locationManager;
	private String gpsProvider;
	private String networkProvider;
	private Location gpsLocation;
	private Location networkLocation;

	public LocationSigninHandler(Context context) {
		this.ctx = context;
	}

	public void doLocationSignin() {
		fetchLocations();
		showProgressDialog();
		new WaitLocationTask().execute();

	}

	private void showProgressDialog() {

		this.inProgressDialog = new ProgressDialog(ctx);
		// ���ý�������񣬷��ΪԲ�Σ���ת��
		inProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// ����ProgressDialog ����
		// m_pDialog.setTitle("��ʾ");
		// ����ProgressDialog ��ʾ��Ϣ
		inProgressDialog.setMessage("���ڶ�λ");
		// ����ProgressDialog ����ͼ��
		// m_pDialog.setIcon(android.R.drawable.ic_dialog_email);
		// ����ProgressDialog �Ľ������Ƿ���ȷ
		inProgressDialog.setIndeterminate(true);
		// ����ProgressDialog �Ƿ���԰��˻ذ���ȡ��
		inProgressDialog.setCancelable(false);
		// ��ProgressDialog��ʾ
		inProgressDialog.show();

	}

	private void fetchLocations() {

		locationManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			// request location updates from GPS provider
			gpsProvider = LocationManager.GPS_PROVIDER;
			locationManager.requestLocationUpdates(gpsProvider, 0, 0,
					new LocationListener() {

						@Override
						public void onLocationChanged(Location location) {
							gpsLocation = location;
						}

						@Override
						public void onProviderDisabled(String provider) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onProviderEnabled(String provider) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onStatusChanged(String provider,
								int status, Bundle extras) {
							// TODO Auto-generated method stub

						}

					});
		}

		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			// request location updates from Network provider
			networkProvider = LocationManager.NETWORK_PROVIDER;
			locationManager.requestLocationUpdates(networkProvider, 0, 0,
					new LocationListener() {

						@Override
						public void onLocationChanged(Location location) {
							networkLocation = location;
						}

						@Override
						public void onProviderDisabled(String provider) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onProviderEnabled(String provider) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onStatusChanged(String provider,
								int status, Bundle extras) {
							// TODO Auto-generated method stub

						}

					});
		}

		if (gpsProvider == null && networkProvider == null) {
			String errMsg = "�뿪��GPS�������߶�λ����";
			new AlertDialog.Builder(ctx).setMessage(errMsg)
					.setPositiveButton("ȷ��", null).show();
		}
	}

	private class WaitLocationTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			try {
				Thread.sleep(3600);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			String result = null;

			Location loc = null;
			if (gpsLocation != null)
				loc = gpsLocation;

			if (networkLocation != null)
				loc = networkLocation;

			if (loc != null) {

				// �ϴ����ڼ�¼
				double longitude = loc.getLongitude(); // ����
				double latitude = loc.getLatitude();// ά��

				UserInfo user = LoginUser.getUser();

				String url = UrlLocations.getWorkSigningUrl();

				String urlSpec = Uri
						.parse(url)
						.buildUpon()
						.appendQueryParameter("companyid", user.getCompanyId())
						.appendQueryParameter("token", user.getToken())
						.appendQueryParameter("empid", user.getEmpId())
						.appendQueryParameter("atttype",
								SigninType.LOCATION.getType() + "")
						.appendQueryParameter("attinfo",
								longitude + ":" + latitude)
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
			} else {
				result = "�޷���ȡ����ʵʱλ�ã���ȷ��GPS�����������綨λ���������ò��ƶ��������ض�λ";
			}

			return result;
		}

		@Override
		protected void onPostExecute(String msg) {

			inProgressDialog.cancel();
			
			String title = null;
			String detail = null;
			int iconId = -1;
			if(msg.equals("���ڳɹ�")) {
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
			
			ImageView resultIcon = (ImageView)v.findViewById(R.id.signin_result_icon);
			resultIcon.setImageResource(iconId);
			
			TextView titleView = (TextView)v.findViewById(R.id.signin_result_title);
			titleView.setText(title);
			
			TextView detailView = (TextView)v.findViewById(R.id.signin_result_detail);
			detailView.setText(detail);
				
			new AlertDialog.Builder(ctx)
				.setView(v)
			    .setPositiveButton("ȷ��", null).show();
		}
	}
}
