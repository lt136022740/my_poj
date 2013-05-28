package com.szkk.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;
import android.util.Log;

import com.szkk.net.delegate.SZHttpRequestDelegate;
import com.szkk.utils.LocalUtil;

public class SZHttpRequest {
	private SZHttpRequestDelegate delegate;
	private HttpUriRequest request;
	private HttpResponse response;
	public byte[] receiveDataBuffer;

	public SZHttpRequest(SZHttpRequestDelegate delegate) {
		this.delegate = delegate;
	}

	/*
	 * { "fail_body":{ "message":"failed", "error_code":50111 } };
	 */
	public void requestOk() {
		try {
			if (response != null) {
				String jsonStr = new String(receiveDataBuffer, "UTF-8");
				JSONObject obj = new JSONObject(jsonStr);   
				if (obj.has("fail_body")) {
					JSONObject bodyObj = obj.optJSONObject("fail_body");
					requestFailed(bodyObj.optString("message"), bodyObj.optInt("code"));
				} else {  
					delegate.requestSuccess(this);
				}
			}
		} catch (Exception e) {

		}
	}

	public void requestFailed(String msg, int error) {
		LocalUtil.showTipDialog(msg + "   " + error);
		delegate.requestFailed(error);
	}

	public void requestGet(String url) {
		request = maketGetReqeust(url);
		new SZHttpRequestExecutor().addRequest(this);
	}
	
	public void requestGet(String url, Map<Object, Object> params){
		String fullUrl = makeURL(url, params);
		request = maketGetReqeust(fullUrl);
		new SZHttpRequestExecutor().addRequest(this);
	}

	private String makeURL(String url, Map<Object, Object> params){
		String result = "";
		for (Object key : params.keySet()) {
			String strValue = (String) params.get(key);
			if (result.length() == 0) {
				result = result.concat(String.format("?%s=%s", key, strValue));
			} else {
				result = result.concat(String.format("&%s=%s", key, strValue));
			}
		}
		return url.concat(result);
	}
	
	private HttpUriRequest maketGetReqeust(String url) {
		HttpUriRequest request = new HttpGet(url);
		return request;
	}

	private HttpPost makePostRequest(String url) {
		String encodedUrl = url;
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(encodedUrl);
			httpPost.addHeader(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_5; en-us) AppleWebKit/526.11 (KHTML, like Gecko)");
			// httpPost.addHeader("Accept-Encoding", "gzip");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpPost;
	}

	public void requestPOST(String url, String body) {
		request = makePostRequest(url);
		if (body != null) {
			try {
				ByteArrayEntity entity = new ByteArrayEntity(
						body.getBytes("UTF-8"));
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/x-www-form-urlencoded");
				((HttpPost) request).setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		new SZHttpRequestExecutor().addRequest(this);
	}

	public String makeBody(Map<String, Object> params) {
		if (params == null | params.size() == 0) {
			return null;
		}
		String postString = new String();
		Iterator<String> allKeysIt = params.keySet().iterator();

		try {
			while (allKeysIt.hasNext()) {
				String paramName = allKeysIt.next();
				String paramValue = "";
				paramValue = params.get(paramName).toString();
				if (allKeysIt.hasNext()) {
					postString = postString.concat(String.format("%s=%s&",
							paramName, paramValue, Base64.DEFAULT));
				} else {
					postString = postString.concat(String.format("%s=%s",
							paramName, paramValue, Base64.DEFAULT));
				}
			}
		} catch (Exception e) {
		}
		return postString;
	}

	public HttpUriRequest getRequest() {
		return request;
	}

	public void setRequest(HttpUriRequest request) {
		this.request = request;
	}

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}
}
