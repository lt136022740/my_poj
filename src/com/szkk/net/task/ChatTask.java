package com.szkk.net.task;

import org.json.JSONObject;

import com.szkk.data.Chat;
import com.szkk.net.SZHttpRequest;
import com.szkk.net.delegate.ChatTaskDelegate;
import com.szkk.net.delegate.SZHttpRequestDelegate;
import com.szkk.system.AppConfig;

public class ChatTask implements SZHttpRequestDelegate{
	private ChatTaskDelegate delegate;
	
	public ChatTask(ChatTaskDelegate delegate, String msg){
		this.delegate = delegate;
		if(msg != null && !msg.equals("")){
			
		}else{
			
		}
		String url = String.format("%s", AppConfig.ROOT_URL);
		SZHttpRequest request = new SZHttpRequest(this);
		request.requestGet(url);
	}
	
	/*
	 * 请求: {"chat" : {"online":true, "content":"hello, how do you do!"}}
	 * 发送: {"send_status":"success"} 
	 */
	@Override
	public void requestSuccess(SZHttpRequest request) {
		try {
			String jsonStr = new String(request.receiveDataBuffer, "utf-8");
			JSONObject jsonObj = new JSONObject(jsonStr);
			Chat chat = null;
			if(jsonObj.has("chat")){
				chat = new Chat(jsonObj.optJSONObject("chat"));
			}
			delegate.didFinishChat(chat);
		} catch (Exception e) {
		}
		
	}

	@Override
	public void requestFailed(int error) {
		delegate.didFailedChat();
	}
}
