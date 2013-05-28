package com.szkk.setting;import android.content.Intent;import android.graphics.Color;import android.net.Uri;import android.os.Bundle;import android.view.View;import android.view.ViewGroup;import android.widget.Button;import android.widget.ImageView;import android.widget.TabHost;import android.widget.TabWidget;import android.widget.TextView;import com.szkk.account.AccountActivity;import com.szkk.account.RegisteActivity;import com.szkk.account.SecurityActivity;import com.szkk.setting.authorization.AuthorizationActivity;import com.szkk.setting.help.NavigationActivity;import com.szkk.utils.ViewUtil;import com.szkk.widget.BackRecoverActivity;import com.szkk.www.R;import com.szkk.www.SZActivity;public class SettingActivity extends SZActivity {	private TabHost tabHost;	private Button layout1_btn_1;	private Button layout1_btn_2;		private Button layout2_btn_1_recover_file;	private Button layout2_btn_2_backup_pic;	private Button layout2_btn_3_recover_pic;	private Button layout3_btn_1_consult_man;	private Button layout3_btn_2_complain_man;	private Button layout3_btn_3_notification_man;	private Button layout4_btn_1_operation_flow;	private Button layout4_btn_2_vip_login;	private Button layout4_btn_3_sz_navigation;	private Button layout4_btn_5_complaint_center;	private Button layout5_btn_1_kk_authorization;	@Override	public void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		setContentView(R.layout.activity_setting);		try {			tabHost = (TabHost) this.findViewById(R.id.TabHost01);			tabHost.setup();			tabHost.addTab(tabHost					.newTabSpec("tab_1")					.setContent(R.id.LinearLayout1)					.setIndicator(							getResources().getString(									R.string.setting_account_str), null));			tabHost.addTab(tabHost					.newTabSpec("tab_2")					.setContent(R.id.LinearLayout2)					.setIndicator(							getResources().getString(									R.string.setting_file_manager_str), null));			tabHost.addTab(tabHost					.newTabSpec("tab_3")					.setContent(R.id.LinearLayout3)					.setIndicator(							getResources().getString(									R.string.setting_consult_manager_str), null));			tabHost.addTab(tabHost					.newTabSpec("tab_4")					.setContent(R.id.LinearLayout4)					.setIndicator(							getResources().getString(R.string.setting_help_str),							null));			tabHost.addTab(tabHost					.newTabSpec("tab_5")					.setContent(R.id.LinearLayout5)					.setIndicator(							getResources()									.getString(R.string.setting_about_str),							null));			tabHost.setCurrentTab(0);		} catch (Exception ex) {		}		TabWidget tabWidget = tabHost.getTabWidget();		for (int i = 0; i < tabWidget.getChildCount(); i++) {			TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);			tv.setTextColor(getResources().getColor(R.color.ff9900));			//ImageView iv = (ImageView) tabWidget.getChildAt(i).findViewById(android.R.id.icon);		}		initView();	}	private void initView() {		ViewGroup view = (ViewGroup) findViewById(android.R.id.tabcontent);		ViewUtil.zoomView(view);		layout1_btn_1 = (Button) findViewById(R.id.btn_personal_account);		layout1_btn_2 = (Button) findViewById(R.id.btn_personal_security);		layout2_btn_1_recover_file = (Button) findViewById(R.id.btn_hy_da);		layout2_btn_2_backup_pic = (Button) findViewById(R.id.btn_bf_st);		layout2_btn_3_recover_pic = (Button) findViewById(R.id.btn_hy_st);		layout3_btn_1_consult_man = (Button) findViewById(R.id.btn_zx_gl);		layout3_btn_2_complain_man = (Button) findViewById(R.id.btn_ts_gl);		layout3_btn_3_notification_man = (Button) findViewById(R.id.btn_tz_gl);		layout4_btn_1_operation_flow = (Button) findViewById(R.id.btn_4_czlc);		layout4_btn_2_vip_login = (Button) findViewById(R.id.btn_4_hydl);		layout4_btn_3_sz_navigation = (Button) findViewById(R.id.btn_4_szdh);		layout4_btn_5_complaint_center = (Button) findViewById(R.id.btn_4_tszx);		layout5_btn_1_kk_authorization = (Button) findViewById(R.id.btn_4_au);		// //		layout1_btn_1.setOnClickListener(new View.OnClickListener() {			@Override			public void onClick(View v) {				Intent intent = new Intent(SettingActivity.this,						AccountActivity.class);				SettingActivity.this.startActivity(intent);				SettingActivity.this.overridePendingTransition(						R.anim.push_right_in, R.anim.push_right_out);			}		});				layout1_btn_2.setOnClickListener(new View.OnClickListener() {			@Override			public void onClick(View v) {				Intent intent = new Intent(SettingActivity.this,						SecurityActivity.class);				SettingActivity.this.startActivity(intent);				SettingActivity.this.overridePendingTransition(						R.anim.push_right_in, R.anim.push_right_out);			}		});		layout2_btn_1_recover_file				.setOnClickListener(new View.OnClickListener() {					@Override					public void onClick(View v) {						Intent intent = new Intent(SettingActivity.this,								BackRecoverActivity.class);						SettingActivity.this.startActivity(intent);						SettingActivity.this.overridePendingTransition(								R.anim.push_right_in, R.anim.push_right_out);					}				});		layout2_btn_2_backup_pic.setOnClickListener(new View.OnClickListener() {			@Override			public void onClick(View v) {				Intent intent = new Intent(SettingActivity.this,						BackRecoverActivity.class);				SettingActivity.this.startActivity(intent);				SettingActivity.this.overridePendingTransition(						R.anim.push_right_in, R.anim.push_right_out);			}		});		layout2_btn_3_recover_pic				.setOnClickListener(new View.OnClickListener() {					@Override					public void onClick(View v) {						Intent intent = new Intent(SettingActivity.this,								BackRecoverActivity.class);						SettingActivity.this.startActivity(intent);						SettingActivity.this.overridePendingTransition(								R.anim.push_right_in, R.anim.push_right_out);					}				});		layout3_btn_1_consult_man				.setOnClickListener(new View.OnClickListener() {					@Override					public void onClick(View v) {						Intent intent = new Intent(SettingActivity.this,								ConsultActivity.class);						intent.putExtra("type", 11);						SettingActivity.this.startActivity(intent);						SettingActivity.this.overridePendingTransition(								R.anim.push_right_in, R.anim.push_right_out);					}				});		layout3_btn_2_complain_man				.setOnClickListener(new View.OnClickListener() {					@Override					public void onClick(View v) {						Intent intent = new Intent(SettingActivity.this,								ConsultActivity.class);						intent.putExtra("type", 12);						SettingActivity.this.startActivity(intent);						SettingActivity.this.overridePendingTransition(								R.anim.push_right_in, R.anim.push_right_out);					}				});		layout3_btn_3_notification_man				.setOnClickListener(new View.OnClickListener() {					@Override					public void onClick(View v) {						Intent intent = new Intent(SettingActivity.this,								ConsultActivity.class);						intent.putExtra("type", 13);						SettingActivity.this.startActivity(intent);						SettingActivity.this.overridePendingTransition(								R.anim.push_right_in, R.anim.push_right_out);					}				});		layout4_btn_1_operation_flow				.setOnClickListener(new View.OnClickListener() {					@Override					public void onClick(View v) {						Uri uri = Uri.parse(getResources().getString(								R.string.setting_help_operation_flow_url));						Intent intent = new Intent(Intent.ACTION_VIEW, uri);						SettingActivity.this.startActivity(intent);					}				});		layout4_btn_2_vip_login.setOnClickListener(new View.OnClickListener() {			@Override			public void onClick(View v) {				Uri uri = Uri.parse(getResources().getString(						R.string.setting_help_login_url));				Intent intent = new Intent(Intent.ACTION_VIEW, uri);				SettingActivity.this.startActivity(intent);				overridePendingTransition(R.anim.push_left_in,						R.anim.push_left_out);			}		});		layout4_btn_3_sz_navigation				.setOnClickListener(new View.OnClickListener() {					@Override					public void onClick(View v) {						Intent intent = new Intent(SettingActivity.this,								NavigationActivity.class);						SettingActivity.this.startActivity(intent);						overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);					}				});		layout4_btn_5_complaint_center				.setOnClickListener(new View.OnClickListener() {					@Override					public void onClick(View v) {					}				});		layout5_btn_1_kk_authorization				.setOnClickListener(new View.OnClickListener() {					@Override					public void onClick(View v) {						Intent intent = new Intent(SettingActivity.this,								AuthorizationActivity.class);						SettingActivity.this.startActivity(intent);						SettingActivity.this.overridePendingTransition(								R.anim.push_right_in, R.anim.push_right_out);					}				});	}}