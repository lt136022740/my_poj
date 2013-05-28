package com.szkk.net.task;

import java.util.LinkedHashMap;

import org.json.JSONObject;

import android.util.Log;

import com.szkk.data.User;
import com.szkk.net.SZHttpRequest;
import com.szkk.net.delegate.RegisteTaskDelegate;
import com.szkk.net.delegate.SZHttpRequestDelegate;
import com.szkk.system.ApplicationManager;
import com.szkk.system.AppConfig;

public class RegisteTask implements SZHttpRequestDelegate{
	private RegisteTaskDelegate delegate;
	
	public RegisteTask(String data, RegisteTaskDelegate delegate){
		this.delegate = delegate;
		String url = String.format("%susers/registe_user.json", AppConfig.ROOT_URL);
		SZHttpRequest request = new SZHttpRequest(this);
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("user_info", data);
		String body = request.makeBody(params);
		request.requestPOST(url, body);
	}
	 
	@Override
	public void requestSuccess(SZHttpRequest request) {
		try {
			String jsonStr = new String(request.receiveDataBuffer, "UTF-8");
			JSONObject jsonObj = new JSONObject(jsonStr);
			ApplicationManager.sharedManager().currentUser = new User(jsonObj);
			delegate.didFinishRegisteOrUpdate();
		} catch (Exception e) {
			Log.i("tag1",  "e " + e);
			delegate.didFailedRegisteOrUpdate();
		}
	}
	@Override
	public void requestFailed(int error) {
		Log.i("tag1",  "error " + error);
		delegate.didFailedRegisteOrUpdate();
	}
}
