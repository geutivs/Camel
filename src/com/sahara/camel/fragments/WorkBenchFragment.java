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

		// �������
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("workbenchItemImage", R.drawable.task_todo_task);
		map.put("workbenchItemText", "�������");
		menuList.add(map);

		map = new HashMap<String, Object>();
		map.put("workbenchItemImage", R.drawable.task_attendence_rec);
		map.put("workbenchItemText", "���ڼ�¼");
		menuList.add(map);

		map = new HashMap<String, Object>();
		map.put("workbenchItemImage", R.drawable.task_location_signin);
		map.put("workbenchItemText", "��λ����");
		menuList.add(map);

		map = new HashMap<String, Object>();
		map.put("workbenchItemImage", R.drawable.task_scan_signin);
		map.put("workbenchItemText", "ɨ�뿼��");
		menuList.add(map);

		map = new HashMap<String, Object>();
		map.put("workbenchItemImage", R.drawable.task_notice);
		map.put("workbenchItemText", "���ӹ���");
		menuList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("workbenchItemImage", R.drawable.task_management);
		map.put("workbenchItemText", "��ҵ����");
		menuList.add(map);

		SimpleAdapter saItem = new SimpleAdapter(getActivity(), menuList, // ����Դ
				R.layout.workbench_item, // xmlʵ��
				new String[] { "workbenchItemImage", "workbenchItemText" }, // ��Ӧmap��Key
				new int[] { R.id.workbenchItemImage, R.id.workbenchItemText }); // ��ӦR��Id

		// ���Item��������
		gridview.setAdapter(saItem);

		// ��ӵ���¼�
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if(position == 2) {
					// ��λ����
					doLocationSignin();
				}
				
				
				Toast.makeText(getActivity().getApplicationContext(),
						"�㰴����ѡ�" + position, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void doLocationSignin() {
		FragmentManager fm = getActivity().getFragmentManager();
		LocationSigninFragment dialog = new LocationSigninFragment();
		dialog.show(fm, "localtion_signin");
	}
}
