package com.szkk.net.delegate;

import java.util.List;

import com.szkk.data.KK;

public interface KKSearchTaskDelegate {
	public void didFinishSearch(List<KK> list);
	public void didFailedSearch();
}
