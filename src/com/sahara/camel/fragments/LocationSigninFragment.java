package com.sahara.camel.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sahara.camel.R;

public class LocationSigninFragment extends DialogFragment {
	
	private TextView locationText;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		View view = getActivity().getLayoutInflater().inflate(
				R.layout.dialog_location_signin, null);
		
		view.findViewById(R.id.dialog_location_signin_content);
		
		LocationManager locationManager = (LocationManager) getActivity()
				.getSystemService(Context.LOCATION_SERVICE);		
		
		String provider = null;
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;
		} else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			provider = LocationManager.NETWORK_PROVIDER;
		} else {
			// TODO
		}
		
		locationManager.requestLocationUpdates(provider, 0, 0, new LocationListener() {

			@Override
			public void onLocationChanged(Location loc) {
			    updateView(loc);	
			}

			@Override
			public void onProviderDisabled(String provider) {
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {				
			}
			
		});

		return new AlertDialog.Builder(getActivity()).setView(view)
				.create();
	}
	
	private void updateView(Location location) {
		if (location != null) {
			StringBuffer sb = new StringBuffer();
			sb.append("经度：");
			sb.append(location.getLongitude());
			sb.append("\n纬度：");
			sb.append(location.getLatitude());
			locationText.setText(sb.toString());
		} else {
			// 如果传入的Location对象为空则清空EditText
			locationText.setText("");
		}
	}
}
