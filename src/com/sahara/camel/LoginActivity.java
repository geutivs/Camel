package com.sahara.camel;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.sahara.camel.fragments.LoginFragment;
import com.sahara.camel.fragments.LoginFragment.OnSigninBtnClickListener;
import com.sahara.camel.fragments.LoginWaitFragment;
import com.sahara.camel.fragments.LoginWaitFragment.OnLoginFailureListener;

public class LoginActivity extends Activity {

	private FragmentManager fManager;
	private Fragment loginFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		fManager = getFragmentManager();

		loginFragment = new LoginFragment(new OnSigninBtnClickListener() {

			@Override
			public void onClick(String username, String password) {
				LoginWaitFragment loginWaitfragment = new LoginWaitFragment(
						username, password, new OnLoginFailureListener() {

							@Override
							public void onLoginFailure() {
								fManager.beginTransaction()
										.replace(R.id.login_container,
												loginFragment).commit();
								Toast.makeText(LoginActivity.this,
										"µÇÂ¼Ê§°Ü", Toast.LENGTH_SHORT).show();
							}

						});
				fManager.beginTransaction()
						.replace(R.id.login_container, loginWaitfragment)
						.commit();
			}

		});
		fManager.beginTransaction().add(R.id.login_container, loginFragment)
				.commit();

	}

}
