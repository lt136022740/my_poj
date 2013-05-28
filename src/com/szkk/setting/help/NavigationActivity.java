package com.szkk.setting.help;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

import com.szkk.www.R;
import com.szkk.www.SZActivity;

public class NavigationActivity extends SZActivity{
	private TabHost tabHost;
	private WebView serviceWView;
	private WebView qqWView;
	private WebView marketWVIew; 
	private WebView websiteWView;
	private WebView forumWView;
	private WebView kkWebsiteWView;
	
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_help_navigation);
		try {
			tabHost = (TabHost) this.findViewById(R.id.TabHost01);
			tabHost.setup();

			tabHost.addTab(tabHost
					.newTabSpec("tab_1")
					.setContent(R.id.LinearLayout1)
					.setIndicator(
							getResources().getString(R.string.wview_service_str),
							null));
			tabHost.addTab(tabHost
					.newTabSpec("tab_2")
					.setContent(R.id.LinearLayout2)
					.setIndicator(
							getResources().getString(R.string.wview_qq_str),
							null));
			tabHost.addTab(tabHost
					.newTabSpec("tab_3")
					.setContent(R.id.LinearLayout3)
					.setIndicator(
							getResources().getString(R.string.wview_market_str),
							null));
			tabHost.addTab(tabHost
					.newTabSpec("tab_4")
					.setContent(R.id.LinearLayout4)
					.setIndicator(
							getResources().getString(R.string.wview_website_str),
							null));
			tabHost.addTab(tabHost
					.newTabSpec("tab_5")
					.setContent(R.id.LinearLayout5)
					.setIndicator(
							getResources().getString(R.string.wview_forum_str),
							null));
			tabHost.addTab(tabHost
					.newTabSpec("tab_6")
					.setContent(R.id.LinearLayout6)
					.setIndicator(
							getResources().getString(R.string.wview_kk_website_str),
							null));
			tabHost.setCurrentTab(0);
			tabHost.setOnTabChangedListener(new OnTabChangeListener() {
				
				@Override
				public void onTabChanged(String tabId) {
					if(tabId.equals("tab_1")){
						
					}else if(tabId.equals("tab_2")){
						
					}else if(tabId.equals("tab_3")){
						marketWVIew.loadUrl("http://www.rwsz.cn/shop/");
					}else if(tabId.equals("tab_4")){
						websiteWView.loadUrl("http://www.rwsz.cn/index.html");
					}else if(tabId.equals("tab_5")){
						forumWView.loadUrl("http://www.rwsz.org/default.asp");
					}else if(tabId.equals("tab_6")){
						kkWebsiteWView.loadUrl("http://www.hdkk.org");
					}
				}
			});
		} catch (Exception ex) {   

		}
		TabWidget tabWidget = tabHost.getTabWidget(); 
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
			tv.setTextSize(12);
			tv.setTextColor(getResources().getColor(R.color.ff9900));
			//ImageView iv = (ImageView) tabWidget.getChildAt(i).findViewById(android.R.id.icon);
		}
		initView();
	}

	private void initView() {
		serviceWView = (WebView) findViewById(R.id.wview_service); 
		qqWView = (WebView) findViewById(R.id.wview_qq); 
		marketWVIew = (WebView) findViewById(R.id.wview_market); 
		websiteWView = (WebView) findViewById(R.id.wview_website); 
		forumWView = (WebView) findViewById(R.id.wview_forum); 
		kkWebsiteWView = (WebView) findViewById(R.id.wview_kk_website); 
	}
	
}
