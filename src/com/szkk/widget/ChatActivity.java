package com.szkk.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.szkk.data.Chat;
import com.szkk.net.delegate.ChatTaskDelegate;
import com.szkk.www.R;
import com.szkk.www.SZActivity;

public class ChatActivity extends SZActivity implements ChatTaskDelegate{
	private TextView contentTView;
	private TextView msgEdit;
	private TextView onlineTView;
	private Button closeBtn;
	private Button sendBtn;
	private Thread thread;
	private boolean flag = true;
	private static final int INTERVAL = 6000;
	private boolean hasFinish = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		initView();
		initThread();
	}

	private void initValue(Chat chat){
		String online = "";
		if(chat.isOnline()){
			online = getResources().getString(R.string.on_line_status_on);
		}else{
			online = getResources().getString(R.string.on_line_status_off);
		}
		onlineTView.setText(getResources().getString(R.string.on_line_status) + online);
		contentTView.setText(chat.getContent());
	}
	
	private void initThread() {
		thread = new Thread(new Runnable(){
			public void run(){
				while(flag){
					request();
					try {
						Thread.sleep(INTERVAL);
					} catch (InterruptedException e) {
					}
				}
			}
		});
		thread.start();
	}

	private void initView() {
		contentTView = (TextView) findViewById(R.id.chat_content);
		msgEdit = (TextView) findViewById(R.id.chat_msg);
		onlineTView = (TextView) findViewById(R.id.status_online);
		closeBtn = (Button) findViewById(R.id.btn_close);
		sendBtn = (Button) findViewById(R.id.btn_send);
		
		closeBtn.setOnClickListener(listener);
		sendBtn.setOnClickListener(listener);
	}
	
	private View.OnClickListener listener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btn_close:
				ChatActivity.this.finish();
				ChatActivity.this.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				break;
			case R.id.btn_send:
				send();
				break;
			}
		}
	};
	
	private void send(){
		if(!hasFinish) return;
		hasFinish = false;
	}
	
	private void request(){
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		flag = false;
	}

	@Override
	public void didFinishChat(final Chat chat) {
		Runnable action = new Runnable(){
			public void run(){
				initValue(chat);
			}
		};
		runOnUiThread(action);
	}

	@Override
	public void didFailedChat() {

	}
}
