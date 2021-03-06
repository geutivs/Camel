package com.sahara.camel.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sahara.camel.R;
import com.sahara.camel.SignUpActivity;

public class LoginFragment extends Fragment {
	
	private TextView mSignup;
	private Button mSigninButton;
	private EditText mUserAccount;
	private EditText mUserPass;
	
	private OnSigninBtnClickListener signinBtnListener;
	
	public LoginFragment(OnSigninBtnClickListener listener) {
		signinBtnListener = listener;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_login, container, false);
		
		mUserAccount = (EditText)v.findViewById(R.id.login_username_edit);
		mUserPass =(EditText)v.findViewById(R.id.login_password_edit);
				
		mSigninButton = (Button)v.findViewById(R.id.login_signin_button);
		mSigninButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = mUserAccount.getText().toString();
				String password = mUserPass.getText().toString();
				
				if(signinBtnListener != null ) {
					signinBtnListener.onClick(username, password);
				}	
				
			}
			
		});
		
		mSignup = (TextView)v.findViewById(R.id.login_signup);
		mSignup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), SignUpActivity.class);
				startActivity(intent);				
			}
			
		});
		
		return v;
	}
	
	public interface OnSigninBtnClickListener {
		void onClick(String username, String password);
	}

}
