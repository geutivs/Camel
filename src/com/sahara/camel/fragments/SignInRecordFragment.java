package com.sahara.camel.fragments;

import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sahara.camel.R;
import com.sahara.camel.TimeAxisAdapter;

public class SignInRecordFragment extends Fragment {
	
	private TextView activityTitle;
	private Button mButtonBack;
	private ListView signinRecListView;
	private TimeAxisAdapter mTimeAxisAdapter;
	
	private List<Map<String, Object>> dataModel;
	
	public SignInRecordFragment(List<Map<String, Object>> dataModel) {
		this.dataModel = dataModel;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_signin_record, container, false);
		initTitleBar(view);
		initView(view);		
		
		return view;
	}
	
	private void initTitleBar(View v) {
		activityTitle = (TextView)v.findViewById(R.id.textview_title);
		activityTitle.setText(R.string.title_signin_record);
		
		mButtonBack = (Button)v.findViewById(R.id.button_back);
		mButtonBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().finish();				
			}
		});
	}
	
	private void initView(View v) {
		signinRecListView = (ListView)v.findViewById(R.id.signin_record_list);
		signinRecListView.setDividerHeight(0);
		mTimeAxisAdapter = new TimeAxisAdapter(getActivity(), dataModel);
		signinRecListView.setAdapter(mTimeAxisAdapter);
	}
}
