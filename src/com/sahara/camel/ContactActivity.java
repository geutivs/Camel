package com.sahara.camel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sahara.camel.model.UserInfo;

public class ContactActivity extends Activity {
	
	public static final String CONTACT_MODEL = "com.sahara.camel.contact_model";
	
	private UserInfo contact;
	private TextView activityTitle;
	private Button mButtonBack;
	
	private TextView nameView;
	private TextView mobileView;
	private TextView companyView;
	private TextView departmentView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contact = (UserInfo)this.getIntent().getSerializableExtra(CONTACT_MODEL);
		setContentView(R.layout.activity_contact);
		
		nameView = (TextView)findViewById(R.id.contact_name);
		String name = contact.getUserName();
		if(name != null) {
			nameView.setText(name);
		}
		
		mobileView = (TextView)findViewById(R.id.contact_mobile);
		String mobile = contact.getMobile();
		if(mobile != null) {
			mobileView.setText(mobile);
		}
		
		companyView = (TextView)findViewById(R.id.contact_company_id);
		String companyName = contact.getCompanyName();
		if(companyName != null) {
			companyView.setText(companyName);
		}
		
		departmentView = (TextView)findViewById(R.id.contact_department_id);
		String departmentName = contact.getDeptName();
		if(departmentName != null) {
			departmentView.setText(departmentName);
		}
		
		initTitleBar();
	}
	
	private void initTitleBar() {
		activityTitle = (TextView) findViewById(R.id.textview_title);
		activityTitle.setText("Í¨Ñ¶Â¼ÏêÇé");
		
		mButtonBack = (Button) findViewById(R.id.button_back);
		mButtonBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ContactActivity.this.finish();				
			}
		});
	}

}
