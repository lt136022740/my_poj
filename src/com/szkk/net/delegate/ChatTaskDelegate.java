package com.szkk.net.delegate;

import com.szkk.data.Chat;

public interface ChatTaskDelegate {
	public void didFinishChat(Chat chat);
	public void didFailedChat();
}
