package com.sahara.camel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sahara.camel.fragments.SignInRecordFragment;
import com.sahara.camel.fragments.SignInRecordLoadingFragment;
import com.sahara.camel.fragments.SignInRecordLoadingFragment.OnSignInRecordLoadedListener;

public class SigninRecordActivity extends Activity {

	private FragmentManager fManager;
	private SignInRecordLoadingFragment loadingFragment;
	private SignInRecordFragment recordFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin_record);

		fManager = getFragmentManager();

		loadingFragment = new SignInRecordLoadingFragment(
				new OnSignInRecordLoadedListener() {

					@Override
					public void onLoaded(List<Map<String, Object>> signInRecord) {
						recordFragment = new SignInRecordFragment(signInRecord);
						fManager.beginTransaction()
								.replace(R.id.signin_record_container,
										recordFragment).commit();

					}
				});
		
		fManager.beginTransaction()
				.replace(R.id.signin_record_container, loadingFragment)
				.commit();
	}
}
