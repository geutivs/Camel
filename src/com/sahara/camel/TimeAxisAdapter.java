package com.sahara.camel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TimeAxisAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;

	private Context context;

	private static class ViewHolder {
		private TextView signInSummary;
		private TextView signInTime;
		private TextView signOutTime;
		private TextView signInDate;
		private TextView signInDayInWeek;
	}

	public TimeAxisAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;

		viewHolder = new ViewHolder();
		convertView = LayoutInflater.from(context).inflate(
				R.layout.listitem_signin_record, null);

		Map<String, Object> dataModel = list.get(position);

		// Summary
		viewHolder.signInSummary = (TextView) convertView
				.findViewById(R.id.sign_in_summary);
		viewHolder.signInSummary.setText(dataModel.get("signInSummary")
				.toString());

		// 上班时间
		viewHolder.signInTime = (TextView) convertView
				.findViewById(R.id.sign_in_time_text);
		String signInTmStr = dataModel.get("signInTime").toString();
		if (signInTmStr.isEmpty()) {
			signInTmStr = "- -:- -";
		} else {
			signInTmStr = signInTmStr.substring(0, 5);
		}
		viewHolder.signInTime.setText(signInTmStr);

		// 下班时间
		viewHolder.signOutTime = (TextView) convertView
				.findViewById(R.id.sign_out_time_text);
		String signOutTmStr = dataModel.get("signOutTime").toString();
		if (signOutTmStr.isEmpty()) {
			signOutTmStr = "- -:- -";
		} else {
			signOutTmStr = signOutTmStr.substring(0, 5);
		}
		viewHolder.signOutTime.setText(signOutTmStr);

		// 考勤日期
		viewHolder.signInDate = (TextView) convertView
				.findViewById(R.id.sign_in_record_date);
		String signInDate = dataModel.get("signInDate").toString();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = dateFormat1.parse(signInDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy年MM月dd日");
		String displayDate = dateFormat2.format(d);
		viewHolder.signInDate.setText(displayDate);

		// 星期几？
		viewHolder.signInDayInWeek = (TextView) convertView
				.findViewById(R.id.sign_in_day_in_week);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		viewHolder.signInDayInWeek.setText(getDayOfWeekNm(dayOfWeek));

		convertView.setTag(viewHolder);
	
		return convertView;
	}

	private String getDayOfWeekNm(int dayOfWeek) {

		String name = "未知";

		switch (dayOfWeek) {
		case Calendar.SUNDAY:
			name = "周日";
			break;
		case Calendar.MONDAY:
			name = "周一";
			break;
		case Calendar.TUESDAY:
			name = "周二";
			break;
		case Calendar.WEDNESDAY:
			name = "周三";
			break;
		case Calendar.THURSDAY:
			name = "周四";
			break;
		case Calendar.FRIDAY:
			name = "周五";
			break;
		case Calendar.SATURDAY:
			name = "周六";
			break;
		}
		;

		return name;

	}
}
