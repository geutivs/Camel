package com.sahara.camel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SigninRecordActivity extends Activity {
	
	private TextView activityTitle;
	private Button mButtonBack;
	private ListView signinRecListView;
	private TimeAxisAdapter mTimeAxisAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin_record);
		initTitleBar();
		initView();		
	}
	
	private void initTitleBar() {
		activityTitle = (TextView) findViewById(R.id.textview_title);
		activityTitle.setText(R.string.title_signin_record);
		
		mButtonBack = (Button) findViewById(R.id.button_back);
		mButtonBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SigninRecordActivity.this.finish();				
			}
		});
	}
	
	private void initView() {
		signinRecListView = (ListView) findViewById(R.id.signin_record_list);
		signinRecListView.setDividerHeight(0);
		mTimeAxisAdapter = new TimeAxisAdapter(this, getList());
		signinRecListView.setAdapter(mTimeAxisAdapter);
	}
	
	public List<HashMap<String, Object>> getList() {
		List<HashMap<String, Object>> listChild = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("content", "无打卡记录");
		listChild.add(map);
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("content", "上班未打卡下班正常");
		listChild.add(map1);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("content", "正常上下班");
		listChild.add(map2);
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("content", "下班未打卡上班正常");
		listChild.add(map3);
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("content", "上班迟到下班未打卡");
		listChild.add(map4);
		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("content", "下班迟到上班未打卡");
		listChild.add(map5);
		return listChild;
	}
}
