package com.sahara.camel.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.sahara.camel.R;

public class WorkBenchFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.fragment_workbench, container, false);

		GridView gridview = (GridView) v.findViewById(R.id.workbench_bar);
		initGridView(gridview);

		return v;
	}

	private void initGridView(GridView gridview) {

		ArrayList<HashMap<String, Object>> menuList = new ArrayList<HashMap<String, Object>>();

		// 任务待办
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("workbenchItemImage", R.drawable.task_todo_task);
		map.put("workbenchItemText", "任务待办");
		menuList.add(map);

		map = new HashMap<String, Object>();
		map.put("workbenchItemImage", R.drawable.task_attendence_rec);
		map.put("workbenchItemText", "考勤记录");
		menuList.add(map);

		map = new HashMap<String, Object>();
		map.put("workbenchItemImage", R.drawable.task_location_signin);
		map.put("workbenchItemText", "定位考勤");
		menuList.add(map);

		map = new HashMap<String, Object>();
		map.put("workbenchItemImage", R.drawable.task_scan_signin);
		map.put("workbenchItemText", "扫码考勤");
		menuList.add(map);

		map = new HashMap<String, Object>();
		map.put("workbenchItemImage", R.drawable.task_notice);
		map.put("workbenchItemText", "电子公告");
		menuList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("workbenchItemImage", R.drawable.task_management);
		map.put("workbenchItemText", "企业管理");
		menuList.add(map);

		SimpleAdapter saItem = new SimpleAdapter(getActivity(), menuList, // 数据源
				R.layout.workbench_item, // xml实现
				new String[] { "workbenchItemImage", "workbenchItemText" }, // 对应map的Key
				new int[] { R.id.workbenchItemImage, R.id.workbenchItemText }); // 对应R的Id

		// 添加Item到网格中
		gridview.setAdapter(saItem);

		// 添加点击事件
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if(position == 2) {
					// 定位考勤
					doLocationSignin();
				}
				
				
				Toast.makeText(getActivity().getApplicationContext(),
						"你按下了选项：" + position, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void doLocationSignin() {
		FragmentManager fm = getActivity().getFragmentManager();
		LocationSigninFragment dialog = new LocationSigninFragment();
		dialog.show(fm, "localtion_signin");
	}
}
