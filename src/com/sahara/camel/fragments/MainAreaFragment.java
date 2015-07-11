package com.sahara.camel.fragments;

import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahara.camel.R;
import com.sahara.camel.fragments.AddrBookLoadingFragment.OnContactLoadedListener;
import com.sahara.camel.widget.sortlistview.SortModel;

public class MainAreaFragment extends Fragment implements OnClickListener {

	// ����3��Fragment�Ķ���
	private Fragment messageFragment;
	private Fragment workbenchFragment;
	private Fragment addrBookFragment;
	private Fragment contactLoadingFragment;

	// ����ײ�����������������
	private ViewGroup message_layout;
	private ViewGroup workbench_layout;
	private ViewGroup addrbook_layout;

	// ����ײ��������е�ImageView��TextView
	private ImageView message_image;
	private ImageView workbench_image;
	private ImageView addrbook_image;
	private TextView message_text;
	private TextView addrbook_text;
	private TextView workbench_text;

	// ����Ҫ�õ���ɫֵ
	private int white = 0xFFFFFFFF;
	private int gray = 0xFF7597B3;
	private int blue = 0xFF0AB2FB;

	// ����FragmentManager����
	FragmentManager fManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_area, container, false);
		fManager = getFragmentManager();
		initViews(view);
		setChioceItem(1);

		return view;
	}

	// �������ĳ�ʼ��
	public void initViews(View v) {
		message_image = (ImageView) v.findViewById(R.id.message_image);
		workbench_image = (ImageView) v.findViewById(R.id.workbench_image);
		addrbook_image = (ImageView) v.findViewById(R.id.addrbook_image);
		message_text = (TextView) v.findViewById(R.id.message_text);
		workbench_text = (TextView) v.findViewById(R.id.workbench_text);
		addrbook_text = (TextView) v.findViewById(R.id.addrbook_text);
		message_layout = (ViewGroup) v.findViewById(R.id.message_layout);
		workbench_layout = (ViewGroup) v.findViewById(R.id.workbench_layout);
		addrbook_layout = (ViewGroup) v.findViewById(R.id.addrbook_layout);
		message_layout.setOnClickListener(this);
		workbench_layout.setOnClickListener(this);
		addrbook_layout.setOnClickListener(this);
	}

	// ��дonClick�¼�
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.message_layout:
			setChioceItem(0);
			break;
		case R.id.workbench_layout:
			setChioceItem(1);
			break;
		case R.id.addrbook_layout:
			setChioceItem(2);
			break;
		default:
			break;
		}
	}

	// ����һ��ѡ��һ��item��Ĵ���
	public void setChioceItem(int index) {
		// ����ѡ��+��������Fragment
		FragmentTransaction transaction = fManager.beginTransaction();
		clearChioce();
		hideFragments(transaction);
		switch (index) {
		case 0:
			message_image.setImageResource(R.drawable.ic_tabbar_course_pressed);
			message_text.setTextColor(blue);
			message_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
			if (messageFragment == null) {
				// ���fg1Ϊ�գ��򴴽�һ������ӵ�������
				messageFragment = new MessageFragment();
				transaction.add(R.id.content, messageFragment);
			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(messageFragment);
			}
			break;

		case 1:
			workbench_image.setImageResource(R.drawable.ic_tabbar_found_pressed);
			workbench_text.setTextColor(blue);
			workbench_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
			if (workbenchFragment == null) {
				// ���fg1Ϊ�գ��򴴽�һ������ӵ�������
				workbenchFragment = new WorkBenchFragment();
				transaction.add(R.id.content, workbenchFragment);
			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(workbenchFragment);
			}
			break;

		case 2:
			addrbook_image
					.setImageResource(R.drawable.ic_tabbar_settings_pressed);
			addrbook_text.setTextColor(blue);
			addrbook_layout
					.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
			if (addrBookFragment == null) {
				// ���fg1Ϊ�գ��򴴽�һ������ӵ�������
				contactLoadingFragment = new AddrBookLoadingFragment(new OnContactLoadedListener() {

					@Override
					public void onLoaded(List<SortModel> contactList) {
						addrBookFragment = new AddrBookFragment(contactList);
						FragmentTransaction transaction = fManager.beginTransaction();
						transaction.remove(contactLoadingFragment);
						transaction.add(R.id.content, addrBookFragment);
						transaction.commit();
						contactLoadingFragment = null;
					}
					
				});
				
				transaction.add(R.id.content, contactLoadingFragment);				
			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(addrBookFragment);
			}
			break;
		}
		transaction.commit();
	}

	// �������е�Fragment,����fragment����
	private void hideFragments(FragmentTransaction transaction) {
		if (messageFragment != null) {
			transaction.hide(messageFragment);
		}
		if (workbenchFragment != null) {
			transaction.hide(workbenchFragment);
		}
		if (addrBookFragment != null) {
			transaction.hide(addrBookFragment);
		}
		if(contactLoadingFragment != null) {
			transaction.remove(contactLoadingFragment);
		}
	}

	// ����һ����������ѡ��ķ���
	public void clearChioce() {
		message_image.setImageResource(R.drawable.ic_tabbar_course_normal);
		message_layout.setBackgroundColor(white);
		message_text.setTextColor(gray);
		workbench_image.setImageResource(R.drawable.ic_tabbar_found_normal);
		workbench_layout.setBackgroundColor(white);
		workbench_text.setTextColor(gray);
		addrbook_image.setImageResource(R.drawable.ic_tabbar_settings_normal);
		addrbook_layout.setBackgroundColor(white);
		addrbook_text.setTextColor(gray);
	}

}
