package com.sahara.camel.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sahara.camel.MainActivity;
import com.sahara.camel.R;

public class LoginWaitFragment extends Fragment {

	private ProgressBar loginProgress;
	private String username;
	private String password;
	private OnLoginFailureListener listener;

	public LoginWaitFragment(String username, String password, OnLoginFailureListener listener) {
		this.username = username;
		this.password = password;
		this.listener = listener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_login_wait, container, false);

		loginProgress = (ProgressBar) v.findViewById(R.id.progress_login);
		
		new LoginTask().execute();

		return v;
	}

	class LoginTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			if (username.equals("jack") && password.equals("jack")) {
				Intent i = new Intent(getActivity(), MainActivity.class);
				startActivity(i);
			} else {
				if(listener != null)
					listener.onLoginFailure();			
			}
		}

	}
	
	public interface OnLoginFailureListener {
		void onLoginFailure();
	}

}
