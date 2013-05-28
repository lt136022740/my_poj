package com.szkk.net.delegate;

import com.szkk.net.SZHttpRequest;

public interface SZHttpRequestDelegate {
	public void requestSuccess(SZHttpRequest request);
	public void requestFailed(int error);
}
