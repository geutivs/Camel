package com.sahara.camel.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class HttpUtils {

	public static byte[] getInBytes(String urlSpec) throws IOException {
		URL url = new URL(urlSpec);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream in = connection.getInputStream();

			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}

			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, bytesRead);
			}
			out.close();

			return out.toByteArray();
		} finally {
			connection.disconnect();
		}
	}

	public static String get(String urlSpec) throws IOException {
		return new String(getInBytes(urlSpec));
	}
	
	public static HttpResult post(String url, Map<String, String> params,
			String token) throws IOException {

		JSONObject obj = null;
		if(params != null)  {
			obj = new JSONObject();
			try {
				for(Entry<String, String> data : params.entrySet()) {
					String key = data.getKey();
					String value = data.getValue();
					obj.put(key, value);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				throw new IOException("网络包打包错误", e);
			}
		}
		
		return post(url, obj, token);
	}

	public static HttpResult post(String url, JSONObject body,
			String token) throws IOException {

		HttpResponse httpResponse = null;
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-type", "application/json");

		// 设置httpPost请求参数
		if(body != null) {
			StringEntity entity = new StringEntity(body.toString());
			httpPost.setEntity(entity);
		}
		
		if (token != null) {
			httpPost.setHeader("X-AUTH-TOKEN", token);
		}

		HttpResult result = new HttpResult();
		httpResponse = new DefaultHttpClient().execute(httpPost);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		String data = EntityUtils.toString(httpResponse.getEntity());
		if (statusCode == 200) {
			// 使用getEntity方法活得返回结果 
			 result.setData(data);
			 result.setSuccess(true);
			 
		} else {
			// 访问失败		
			JSONTokener jsonParser = new JSONTokener(data);
			try {
				JSONObject res = (JSONObject) jsonParser.nextValue();
				res.getJSONObject("error");
				int code = res.getInt("code");
				String message = res.getString("message");
				
				result.setCode(code);
				result.setMessage(message);
				result.setSuccess(false);
			} catch (JSONException e) {
				e.printStackTrace();
				throw new IOException("网络包解析错误", e);
			}
		}
		
		return result;
	}
}
