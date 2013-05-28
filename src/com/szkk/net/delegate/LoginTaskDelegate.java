package com.szkk.net.delegate;

import com.szkk.net.SZHttpRequest;

public interface LoginTaskDelegate {
	public void didFinishLogin();
	public void didFailedLogin();
}
