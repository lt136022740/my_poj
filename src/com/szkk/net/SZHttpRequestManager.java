package com.szkk.net;

import java.io.File;
import java.util.Map;

import com.szkk.account.CheckInActivity;
import com.szkk.net.delegate.ArchivesTaskDelegate;
import com.szkk.net.delegate.ChatTaskDelegate;
import com.szkk.net.delegate.CheckInTaskDelegate;
import com.szkk.net.delegate.FileUploadTaskDelegate;
import com.szkk.net.delegate.KKListByTypeTaskDelegate;
import com.szkk.net.delegate.LoginTaskDelegate;
import com.szkk.net.delegate.ModifyAccountTaskDelegate;
import com.szkk.net.delegate.NotificationTaskDelegate;
import com.szkk.net.delegate.RegisteTaskDelegate;
import com.szkk.net.delegate.SecurityTaskDelegate;
import com.szkk.net.task.ArchivesTask;
import com.szkk.net.task.ChatTask;
import com.szkk.net.task.CheckInTask;
import com.szkk.net.task.KKListByTypeTask;
import com.szkk.net.task.LoginTask;
import com.szkk.net.task.ModifyAccountTask;
import com.szkk.net.task.NotificationTask;
import com.szkk.net.task.RegisteTask;
import com.szkk.net.task.SecurityTask;
import com.szkk.system.KKListByType;
import com.szkk.utils.FileImageUpload;

public class SZHttpRequestManager {
	private final static SZHttpRequestManager instance = new SZHttpRequestManager();
	
	private SZHttpRequestManager(){
		
	}
	
	public static SZHttpRequestManager sharedManager(){
		return instance;
	}
	
	public void login(String userName, String pwd, LoginTaskDelegate delegate){
		new LoginTask(userName, pwd, delegate);
	}
	
	public void register(String data, RegisteTaskDelegate delegate){
		new RegisteTask(data, delegate);
	}
	
	public void listByType(KKListByType listType, KKListByTypeTaskDelegate delegate){
		new KKListByTypeTask(listType, delegate);
	}
	
	public void checkIn(String data, CheckInTaskDelegate delegate){
		new  CheckInTask(data, delegate);
	}
	
	public void uploadFile(final FileUploadTaskDelegate delegate, final int id, final File file){
		new Thread(new Runnable(){ 
			public void run(){
				new FileImageUpload(delegate).uploadFile(id, file);    
			}
		}).start();
		
	}
	
	public void archiveByAction(int archive_id, String action, ArchivesTaskDelegate delegate){
		new ArchivesTask(archive_id, action, delegate);
	}
	
	public void checkNotification(int user_id, NotificationTaskDelegate delegate){
		new NotificationTask(user_id, delegate);
	}
	
	public void modifyPwd(String old_pwd, String new_pwd, String new_pwd_con, 
			SecurityTaskDelegate delegate){
		new SecurityTask(old_pwd, new_pwd, new_pwd_con, delegate);
	}
	
	public void modifyAccount(String data, ModifyAccountTaskDelegate delegate){
		new ModifyAccountTask(data, delegate);
	}
	
	public void chatSend(ChatTaskDelegate delegate, String msg){
		new ChatTask(delegate, msg);
	}
	
	public void chatRequest(ChatTaskDelegate delegate){
		new ChatTask(delegate, "");
	}
	
}
