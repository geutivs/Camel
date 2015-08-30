package com.sahara.camel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FirmListActivity extends Activity {

	private ListView mFirmList;
	private Button mCreateFirm;
	private TextView activityTitle;
	private Button mButtonBack;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_firm_list);
		initTitleBar();

		mFirmList = (ListView) findViewById(R.id.firms_list_view);
		mFirmList.setAdapter(getAdapter());
		
		mCreateFirm = (Button) findViewById(R.id.button_create_firm);
		mCreateFirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(FirmListActivity.this, CreateFirmActivity.class);
				startActivity(i);
			}	
		});

	}

	private ListAdapter getAdapter() {

		String[] firms = { "魅族", "苹果", "三星" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, firms);
		return adapter;
	}

	private void initTitleBar() {

		activityTitle = (TextView) findViewById(R.id.textview_title);
		activityTitle.setText("企业列表");

		mButtonBack = (Button) findViewById(R.id.button_back);
		mButtonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FirmListActivity.this.finish();
			}
		});
	}
	
}
