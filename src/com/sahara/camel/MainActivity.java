package com.sahara.camel;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.sahara.camel.fragments.MainAreaFragment;
import com.sahara.camel.fragments.SlidingMenuFragment;


public class MainActivity extends SlidingActivity {

	private Fragment mainAreaFragment;
	private Fragment slidingMenuFragment;
	private FragmentManager fManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.frame_main_area);
		setBehindContentView(R.layout.frame_sliding_menu);
		
		fManager = getFragmentManager();
		
		mainAreaFragment = fManager.findFragmentById(R.id.main_area_frame);
		if (mainAreaFragment == null) {
			mainAreaFragment = new MainAreaFragment();
			fManager.beginTransaction().add(R.id.main_area_frame, mainAreaFragment)
					.commit();
		}
		
		slidingMenuFragment = fManager.findFragmentById(R.id.sliding_menu_frame);
		if(slidingMenuFragment == null) {
			slidingMenuFragment = new SlidingMenuFragment();
			fManager.beginTransaction().add(R.id.sliding_menu_frame, slidingMenuFragment)
					.commit();
		}
		
		// customize the SlidingMenu
		setUpSlidingMenu();

        //ʹ�����Ϸ�icon�ɵ㣬������onOptionsItemSelected����ſ��Լ�����R.id.home
        getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	private void setUpSlidingMenu() {
        
		SlidingMenu sm = getSlidingMenu();
		
		//�������һ��˵� 
		sm.setMode(SlidingMenu.LEFT); 
		// ���û�����Ӱ�Ŀ��
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// ���û�����Ӱ��ͼ����Դ
		sm.setShadowDrawable(R.drawable.shadow);
		// ���û����˵���ͼ�Ŀ��
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// ���ý��뽥��Ч����ֵ
	    sm.setFadeDegree(0.35f);
		// ���ô�����Ļ��ģʽ
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
