package com.szkk.net.task;

import java.util.LinkedHashMap;

import org.json.JSONObject;

import android.bluetooth.BluetoothClass.Device;
import android.util.Log;

import com.szkk.data.User;
import com.szkk.net.SZHttpRequest;
import com.szkk.net.delegate.LoginTaskDelegate;
import com.szkk.net.delegate.SZHttpRequestDelegate;
import com.szkk.system.ApplicationManager;
import com.szkk.system.AppConfig;

public class LoginTask implements SZHttpRequestDelegate{
	private LoginTaskDelegate delegate;
	
	public LoginTask(String userName, String pwd, LoginTaskDelegate delegate){
		this.delegate = delegate;
		String url = String.format("%susers/login.json", AppConfig.ROOT_URL);
		SZHttpRequest request = new SZHttpRequest(this);     
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("user_name", userName);
		params.put("password", pwd);
		String body = request.makeBody(params);
		  
		request.requestPOST(url, body);
	}  
	 
	@Override
	public void requestSuccess(SZHttpRequest request) {
		try {   
			String jsonStr = new String(request.receiveDataBuffer, "UTF-8");
			JSONObject jsonObj = new JSONObject(jsonStr);
			Log.i("tag1", "json " + jsonStr);
			ApplicationManager.sharedManager().currentUser = new User(jsonObj.optJSONObject("user"));
			delegate.didFinishLogin();
		} catch (Exception e) {   
		}
	}     
	@Override
	public void requestFailed(int error) {
		delegate.didFailedLogin();
	}
}
