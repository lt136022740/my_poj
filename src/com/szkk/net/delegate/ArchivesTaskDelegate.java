package com.szkk.net.delegate;

public interface ArchivesTaskDelegate {
	public static final String DEL = "del";
	public static final String DIA = "diagnosis";
	
	public void didFinishDeleteArchive();
	public void didFailedDeleteArchive();
	
	public void didFinishGetDiagnosis(String content);
	public void didFailedGetDiagnosis();
}
