package com.sahara.camel.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahara.camel.ContactActivity;
import com.sahara.camel.LoginActivity;
import com.sahara.camel.R;
import com.sahara.camel.data.LoginUser;
import com.sahara.camel.model.UserInfo;

public class SlidingMenuFragment extends Fragment {
	
	private TextView mUserName;
	private ImageView headImageView;
	private TextView mLogout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_sliding_menu, container, false);
		
		UserInfo loginUser = LoginUser.getUser();
		
		headImageView = (ImageView)view.findViewById(R.id.headImageView);
		headImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UserInfo user = LoginUser.getUser();
				Intent intent = new Intent(getActivity(), ContactActivity.class);
				intent.putExtra(ContactActivity.CONTACT_MODEL, user);
				startActivity(intent);	
			}
			
		});
		
		mUserName =  (TextView)view.findViewById(R.id.nickNameTextView);
		mUserName.setText(loginUser.getUserName());
		
		mLogout = (TextView)view.findViewById(R.id.toolbox_logout);
		mLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				new AlertDialog.Builder(getActivity()).setMessage("确定要退出登录吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(getActivity(), LoginActivity.class);
						startActivity(i);		
					}	
				}).setNegativeButton("取消",null).show();
						
			}
		});
		
		return view;
	}

}
