package com.sahara.camel.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sahara.camel.R;
import com.sahara.camel.data.LoginUser;
import com.sahara.camel.model.UserInfo;
import com.sahara.camel.network.HttpUtils;
import com.sahara.camel.network.UrlLocations;
import com.sahara.camel.widget.sortlistview.CharacterParser;
import com.sahara.camel.widget.sortlistview.SortModel;

public class AddrBookLoadingFragment extends Fragment  {
	
	TextView loadingHint;
	ProgressBar loadingBar;
	OnContactLoadedListener listener;
	CharacterParser characterParser;
	
	public AddrBookLoadingFragment(OnContactLoadedListener listener) {
		this.listener = listener;
		characterParser = CharacterParser.getInstance();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_loading, container, false);
		loadingBar = (ProgressBar)view.findViewById(R.id.loading_bar);
		loadingHint = (TextView) view.findViewById(R.id.loading_hint);

		new LoadContactsTask().execute();
		
		return view;
	}
	
	class LoadContactResult {
		String msg;
		List<SortModel> contactList;
	}
	
	class LoadContactsTask extends AsyncTask<Void, Void, LoadContactResult> {

		@Override
		protected LoadContactResult doInBackground(Void... arg0) {
			
			LoadContactResult result = new LoadContactResult();

			String url = UrlLocations.getContactsListUrl();
			
			UserInfo loginUser = LoginUser.getUser();

			String urlSpec = Uri.parse(url).buildUpon()
					.appendQueryParameter("companyid", loginUser.getCompanyId())
//					.appendQueryParameter("token", loginUser.getToken())
//					.appendQueryParameter("empid", loginUser.getEmpId())
					.appendQueryParameter("pindex", "0")
					.appendQueryParameter("psize", "256")
					.build().toString();

			String response = null;
			try {
				response = HttpUtils.get(urlSpec);
			} catch (IOException e1) {
				e1.printStackTrace();
				result.msg = "网络访问异常，请稍候再试";
				return result;
			}

			if (response != null) {

				JSONTokener jsonParser = new JSONTokener(response);
				try {
					JSONObject res = (JSONObject) jsonParser.nextValue();
					String status = res.getString("status");
					String summary = res.getString("summary");
					if (status.equals("00000")) {
						
						List<SortModel> contacts = new ArrayList<SortModel>();
						JSONArray contactList = res.getJSONArray("records");
						for(int i = 0; i < contactList.length(); i ++) {
							JSONObject contactJ = (JSONObject)contactList.get(i);
							SortModel contact = new SortModel();
							contact.setName(contactJ.getString("empname"));
							contact.setPhone(contactJ.getString("empphone"));
							
							//汉字转换成拼音
							String pinyin = characterParser.getSelling(contact.getName());
							String sortString = pinyin.substring(0, 1).toUpperCase();
							
							// 正则表达式，判断首字母是否是英文字母
							if(sortString.matches("[A-Z]")){
								contact.setSortLetters(sortString.toUpperCase());
							} else{
								contact.setSortLetters("#");
							}
							contacts.add(contact);
						}
						
						result.contactList = contacts;
						result.msg = "成功";
					} else {
						result.msg = "加载通讯录失败: " + summary;
					}

				} catch (JSONException e) {
					e.printStackTrace();
					result.msg = "消息解析错误";
				}
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(LoadContactResult result) {
			
			List<SortModel> contactList = result.contactList;
			String msg = result.msg;
			
			if (contactList == null) {
				loadingBar.setVisibility(View.GONE);
				loadingHint.setText(msg);
			} else {
				listener.onLoaded(contactList);
			}
		}
	}
	
	public interface OnContactLoadedListener {
		void onLoaded(List<SortModel> contactList);
	}
}
