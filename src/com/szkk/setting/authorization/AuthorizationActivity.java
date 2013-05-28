package com.szkk.setting.authorization;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szkk.utils.ViewUtil;
import com.szkk.www.R;
import com.szkk.www.SZActivity;

public class AuthorizationActivity extends SZActivity {
	private TextView detailTView;
	private TextView namelTView;
	private TextView vertionTView;
	private TextView updateTimeTView;
	private TextView authorizeToTView;
	private TextView accountTView;
	private TextView roleTView;
	private TextView managerTView;
	private TextView developerTView;
	private TextView officialWebTView;
	private TextView techSupportTView;
	private TextView hotLineTView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_authorization);
		initView();
	}

	private void initView() {
		ViewGroup view = (ViewGroup) findViewById(R.id.main_layout);
		ViewUtil.zoomView(view);
		
		detailTView = (TextView) findViewById(R.id.auth_detail_tview);
		detailTView.setMovementMethod(ScrollingMovementMethod.getInstance());
		namelTView = (TextView) findViewById(R.id.auth_name_tview_value);
		vertionTView = (TextView) findViewById(R.id.auth_vertion_tview_value);
		updateTimeTView = (TextView) findViewById(R.id.auth_update_time_tview_value);
		authorizeToTView = (TextView) findViewById(R.id.auth_authorize_to_tview_value);
		accountTView = (TextView) findViewById(R.id.auth_account_tview_value);
		roleTView = (TextView) findViewById(R.id.auth_role_tview_value);
		managerTView = (TextView) findViewById(R.id.auth_manager_tview_value);
		developerTView = (TextView) findViewById(R.id.auth_develoerer_tview_value);
		officialWebTView = (TextView) findViewById(R.id.auth_official_website_tview_value);
		techSupportTView = (TextView) findViewById(R.id.auth_tech_suppor_tview_value);
		hotLineTView = (TextView) findViewById(R.id.auth_hot_line_tview_value);
	}
	
	private void initValue(){
		
	}
}
