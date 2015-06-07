package com.sahara.camel;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

public class LocationSigninHandler {

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
		inProgressDialog.setCancelable(true);
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

	private class WaitLocationTask extends AsyncTask<Void, Void, Location> {

		@Override
		protected Location doInBackground(Void... params) {
			try {
				Thread.sleep(3600);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(gpsLocation != null)
				return gpsLocation;
			
			if(networkLocation != null)
				return networkLocation;

//			if (locationManager == null) {
//				locationManager = (LocationManager) ctx
//						.getSystemService(Context.LOCATION_SERVICE);
//			}
//
//			if (gpsProvider != null) {
//				Location loc = locationManager
//						.getLastKnownLocation(gpsProvider);
//				if (loc != null) {
//					long locTime = loc.getTime();
//					long delta = System.currentTimeMillis() - locTime;
//					if (delta <= 1000 * 60 * 10) {
//						return loc;
//					}
//				}
//			}
//
//			if (networkProvider != null) {
//				Location loc = locationManager
//						.getLastKnownLocation(networkProvider);
//				if (loc != null) {
//					long locTime = loc.getTime();
//					long delta = System.currentTimeMillis() - locTime;
//					if (delta <= 1000 * 60 * 10) {
//						return loc;
//					}
//				}
//			}

			
			return null;
		}

		@Override
		protected void onPostExecute(Location loc) {

			if (loc != null) {
				inProgressDialog.setMessage("�����ϴ�: " + loc.getLongitude() + ","
						+ loc.getLatitude());
			} else {
				inProgressDialog.cancel();
				new AlertDialog.Builder(ctx).setMessage("�޷���ȡ����ʵʱλ�ã���ȷ��GPS�����������綨λ���������ò��ƶ��������ض�λ")
				.setPositiveButton("ȷ��", null).show();
			}
		}

	}
}
