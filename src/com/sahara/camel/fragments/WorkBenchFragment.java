package com.sahara.camel.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.sahara.camel.LocationSigninHandler;
import com.sahara.camel.NfcSigninActivity;
import com.sahara.camel.R;
import com.sahara.camel.RequestCodeDef;
import com.sahara.camel.SigninRecordActivity;
import com.sahara.camel.comps.qrcode.MipcaActivityCapture;

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
		map.put("workbenchItemImage", R.drawable.task_nfc_signin);
		map.put("workbenchItemText", "NFC����");
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

				switch (position) {
				case 1:
					// ���ڼ�¼
					doQuerySigninRecord();
					break;
				case 2:
					// ��λ����
					doLocationSignin();
					break;
				case 3:
					doScanSignin();
					break;
				case 4:
					doNfcSignin();
					break;
				}

				Toast.makeText(getActivity().getApplicationContext(),
						"�㰴����ѡ�" + position, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void doLocationSignin() {
		// FragmentManager fm = getActivity().getFragmentManager();
		// LocationSigninFragment dialog = new LocationSigninFragment();
		// dialog.show(fm, "localtion_signin");
		LocationSigninHandler handler = new LocationSigninHandler(getActivity());
		handler.doLocationSignin();
	}

	private void doScanSignin() {
		Intent intent = new Intent();
		intent.setClass(getActivity(), MipcaActivityCapture.class);
		startActivityForResult(intent, RequestCodeDef.CODE_BARCODE_SCAN);
	}

	private void doQuerySigninRecord() {
		Intent intent = new Intent();
		intent.setClass(getActivity(), SigninRecordActivity.class);
		startActivity(intent);
	}

	private void doNfcSignin() {

		NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(getActivity());
		if (mAdapter == null) {
			new AlertDialog.Builder(getActivity()).setMessage("�����豸��֧��NFC����")
					.setPositiveButton(R.string.confirm, null).show();
			return;
		} else {
			Intent intent = new Intent();
			intent.setClass(getActivity(), NfcSigninActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case RequestCodeDef.CODE_BARCODE_SCAN:
			if (resultCode == Activity.RESULT_OK) {
				Bundle bundle = data.getExtras();
				// ��ʾɨ�赽������
				String barcodeData = bundle.getString("result");
				Toast.makeText(getActivity(), barcodeData, Toast.LENGTH_LONG)
						.show();
			}
			break;
		}
	}
}
