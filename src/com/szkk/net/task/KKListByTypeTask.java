package com.szkk.net.task;

import org.json.JSONObject;

import android.util.Log;

import com.szkk.net.SZHttpRequest;
import com.szkk.net.delegate.KKListByTypeTaskDelegate;
import com.szkk.net.delegate.SZHttpRequestDelegate;
import com.szkk.system.ApplicationManager;
import com.szkk.system.AppConfig;
import com.szkk.system.KKListByType;

public class KKListByTypeTask implements SZHttpRequestDelegate{
	private KKListByTypeTaskDelegate delegate;
	
	public KKListByTypeTask(KKListByType listType, KKListByTypeTaskDelegate delegate){
		this.delegate = delegate;
		String url = "";
		int id = ApplicationManager.sharedManager().currentUser.getId();
		if(listType == KKListByType.KKLIST_ALL){ 
			url = String.format("%sarchives/%d/all_archives.json", AppConfig.ROOT_URL, id);
		}else if(listType == KKListByType.KKLIST_ANALYSED){
			url = String.format("%sarchives/%d/handle_archives.json", AppConfig.ROOT_URL, id);
		}else if(listType == KKListByType.KKLIST_HANDLED){
			url = String.format("%sarchives/%d/already_handle_archives.json", AppConfig.ROOT_URL, id);
		}else if(listType == KKListByType.KKLIST_NOT_ANALYSED){
			url = String.format("%sarchives/%d/waiting_archives.json", AppConfig.ROOT_URL, id);
		}  
		SZHttpRequest request = new SZHttpRequest(this);   
		request.requestGet(url);
	}
	
	@Override
	public void requestSuccess(SZHttpRequest request) {
		try {
			String jsonStr = new String(request.receiveDataBuffer, "UTF-8");
			JSONObject jsonObj = new JSONObject(jsonStr);
			Log.i("tag", "archives:   " + jsonStr);
			if(jsonObj.has("archives")){
				ApplicationManager.sharedManager().currentUser.updateWithJSON(jsonObj);
			}
			delegate.didFinishListByType();
		} catch (Exception e) {
		}
		
	}
	@Override
	public void requestFailed(int error) {
		Log.i("tag", "error:   " + error);
		delegate.didFailedListByType();
	}
	
}
