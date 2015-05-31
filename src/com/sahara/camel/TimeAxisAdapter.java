package com.sahara.camel;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TimeAxisAdapter extends BaseAdapter {

	private List<HashMap<String, Object>> list;

	private Context context;

	private static class ViewHolder {
		private TextView tvContent;
	}

	public TimeAxisAdapter(Context context, List<HashMap<String, Object>> list) {
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

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.listitem_signin_record, null);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_content);

			viewHolder.tvContent.setText(list.get(position).get("content")
					.toString());
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();

		}
		return convertView;
	}
}
