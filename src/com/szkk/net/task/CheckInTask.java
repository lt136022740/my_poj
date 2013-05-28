package com.szkk.net.task;

import java.util.LinkedHashMap;

import org.json.JSONObject;

import android.util.Log;

import com.szkk.data.KK;
import com.szkk.net.SZHttpRequest;
import com.szkk.net.delegate.CheckInTaskDelegate;
import com.szkk.net.delegate.SZHttpRequestDelegate;
import com.szkk.system.ApplicationManager;
import com.szkk.system.AppConfig;

public class CheckInTask implements SZHttpRequestDelegate{
	private CheckInTaskDelegate delegate;
	
	public CheckInTask(String data, CheckInTaskDelegate delegate){
		this.delegate = delegate;
		String url = String.format("%susers/%d/check_in.json", AppConfig.ROOT_URL, ApplicationManager.sharedManager().currentUser.getId());
		SZHttpRequest request = new SZHttpRequest(this);
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("data", data);
		String body = request.makeBody(params);
		request.requestPOST(url, body);
	}   
	 
	@Override
	public void requestSuccess(SZHttpRequest request) {   
		try {
			String jsonStr = new String(request.receiveDataBuffer, "UTF-8");
			JSONObject jsonObj = new JSONObject(jsonStr);
			if(jsonObj.has("id")){
				delegate.didFinishCheckIn(jsonObj.optInt("id"));
			}else{
				delegate.didFailedCheckIn();	
			}
		} catch (Exception e) {
			delegate.didFailedCheckIn();
		}
		
	}
	@Override
	public void requestFailed(int error) {
		delegate.didFailedCheckIn();
	}
}
