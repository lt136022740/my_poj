package com.szkk.data;

import org.json.JSONObject;

public class Chat extends ApplicationObject{
	private String content;
	private boolean online;
	
	public Chat(JSONObject jsonObj){
		if(jsonObj == null) return;
		updateWithJSON(jsonObj);
	}
	
	public void updateWithJSON(JSONObject jsonObj){
		super.updateWithJSON(jsonObj);
		if(jsonObj.has("content")){
			content = jsonObj.optString("content");
		}
		
		if(jsonObj.has("online")){
			online = jsonObj.optBoolean("online");
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
	
	
}
