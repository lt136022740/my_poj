package com.szkk.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.szkk.net.SZHttpRequestManager;
import com.szkk.net.delegate.LoginTaskDelegate;
import com.szkk.service.NotificationService;
import com.szkk.utils.LocalUtil;
import com.szkk.utils.ViewUtil;
import com.szkk.widget.LoadingView;
import com.szkk.www.MainActivity;
import com.szkk.www.R;
import com.szkk.www.SZActivity;

public class LoginActivity extends SZActivity implements LoginTaskDelegate{
	private Button loginBtn;
	private Button registerBtn;
	private Button findpwBtn;
	private EditText accountEdit;
	private EditText pwdEdit;
     
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}

	private void login(){
		String account = accountEdit.getText().toString();
		String pwd = pwdEdit.getText().toString();
		if (account == null || account.equals("") || pwd == null || pwd.equals("")) {
			LocalUtil.showTipDialog(R.string.acount_pwd_not_alowed_to_null);
			return;
		} 
		startLoading();
		SZHttpRequestManager.sharedManager().login(account, pwd, this);
		
		//Intent intent = new Intent(this, MainActivity.class);
		//startActivity(intent);
		//overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	private void register(){
		Intent intent = new Intent(this, RegisteActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	private void findPwd(){
		
	}
	
	private void initView() {
		ViewGroup view = (ViewGroup) findViewById(R.id.main_layout);
		ViewUtil.zoomView(view);

		loginBtn = (Button) findViewById(R.id.login_btn);
		registerBtn = (Button) findViewById(R.id.register_btn);
		findpwBtn = (Button) findViewById(R.id.find_pw_btn);
		accountEdit = (EditText) findViewById(R.id.account_edit);
		pwdEdit = (EditText) findViewById(R.id.edit_password);
		loadingViewCommon = (LoadingView) findViewById(R.id.loading_view_common);

		loginBtn.setOnClickListener(listener);
		registerBtn.setOnClickListener(listener);
		findpwBtn.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_btn:
				login();
				break;
			case R.id.register_btn:
				register();
				break;
			case R.id.find_pw_btn:
				findPwd();
				break;
			}
		}
	};

	@Override
	public void didFinishLogin() {
		stopLoading();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	public void didFailedLogin() {
		stopLoading();
		//LocalUtil.showTipDialog(R.string.login_failed);
	}
	
}
