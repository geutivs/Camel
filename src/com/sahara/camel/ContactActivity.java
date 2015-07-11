package com.sahara.camel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sahara.camel.widget.sortlistview.SortModel;

public class ContactActivity extends Activity {
	
	public static final String CONTACT_MODEL = "com.sahara.camel.contact_model";
	
	private SortModel contact;
	private TextView activityTitle;
	private Button mButtonBack;
	
	private TextView nameView;
	private TextView mobileView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contact = (SortModel)this.getIntent().getSerializableExtra(CONTACT_MODEL);
		setContentView(R.layout.activity_contact);
		
		nameView = (TextView)findViewById(R.id.contact_name);
		nameView.setText(contact.getName());
		
		mobileView = (TextView)findViewById(R.id.contact_mobile);
		mobileView.setText(contact.getPhone());
		
		
		
		
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
