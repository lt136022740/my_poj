package com.szkk.www;

import java.util.List;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;
import com.szkk.account.CheckInActivity;
import com.szkk.data.KK;
import com.szkk.data.User;
import com.szkk.net.SZHttpRequestManager;
import com.szkk.net.delegate.ArchivesTaskDelegate;
import com.szkk.net.delegate.DialogDelegate;
import com.szkk.net.delegate.KKListByTypeTaskDelegate;
import com.szkk.service.NotificationService;
import com.szkk.setting.SettingActivity;
import com.szkk.setting.help.NavigationActivity;
import com.szkk.system.ApplicationManager;
import com.szkk.system.KKListByType;
import com.szkk.utils.LocalUtil;
import com.szkk.utils.ViewUtil;
import com.szkk.widget.ContentAdapter;
import com.szkk.widget.DiagnosisActivity;
import com.szkk.widget.KKSearchActivity;
import com.szkk.widget.LoadingView;

public class MainActivity extends SZActivity implements
		KKListByTypeTaskDelegate, DialogDelegate, ArchivesTaskDelegate {
	private ListView listView;
	private ContentAdapter adapter;
	private TextView mainBannerTVIew;
	private ImageButton loginBtn;
	private ImageButton anledBtn;
	private ImageButton waitBtn;
	private ImageButton alreadyhandleBtn;
	private ImageButton allBtn;
	private ImageButton askBtn;
	private ImageButton newBtn;
	private ImageButton settingBtn;
	private ImageButton searchBtn;

	private RelativeLayout layout1;
	private RelativeLayout layout2;
	private RelativeLayout layout3;
	private RelativeLayout layout4;
	private RelativeLayout layout5;
	private RelativeLayout layout6;
	private RelativeLayout layout12;
	private RelativeLayout layout13;
	private RelativeLayout layout14;
	private RelativeLayout layout15;
	private KKListByType currentType = KKListByType.KKLIST_ALL;;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//initGCM();
		initView();
		initValue();
		//startService();
	}

	private void startService(){
		Intent intent = new Intent(this, NotificationService.class);
		intent.putExtra("user_id", ApplicationManager.sharedManager().currentUser.getId());
		startService(intent);
	}
	
	private void initValue() {
		User user = ApplicationManager.sharedManager().currentUser;
		List<KK> list = user.getKkList();
		if (list == null || list.size() <= -1) {
			return;
		}
		String str = String.format(getResources().getString(R.string.main_banner), user.getName(), list.size());
		mainBannerTVIew.setText(str);
		if (adapter != null) {
			adapter = null;
		}
		adapter = new ContentAdapter(this, list);
		listView.setAdapter(adapter);
	}
    
	private void initView() {
		ViewGroup view = (ViewGroup) findViewById(R.id.main_layout);
		ViewUtil.zoomView(view);

		listView = (ListView) findViewById(R.id.main_list_view);
		mainBannerTVIew = (TextView) findViewById(R.id.activity_main_title_tview);
		loginBtn = (ImageButton) findViewById(R.id.img1);
		anledBtn = (ImageButton) findViewById(R.id.img2);
		waitBtn = (ImageButton) findViewById(R.id.img3);
		alreadyhandleBtn = (ImageButton) findViewById(R.id.img5);
		allBtn = (ImageButton) findViewById(R.id.img6);
		askBtn = (ImageButton) findViewById(R.id.img12);
		newBtn = (ImageButton) findViewById(R.id.img13);
		settingBtn = (ImageButton) findViewById(R.id.img14);
		searchBtn = (ImageButton) findViewById(R.id.img15);
		loadingViewCommon = (LoadingView) findViewById(R.id.loading_view_common);

		loginBtn.setOnClickListener(listener);
		anledBtn.setOnClickListener(listener);
		waitBtn.setOnClickListener(listener);
		alreadyhandleBtn.setOnClickListener(listener);
		allBtn.setOnClickListener(listener);
		askBtn.setOnClickListener(listener);
		newBtn.setOnClickListener(listener);
		settingBtn.setOnClickListener(listener);
		searchBtn.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = null;
			int requestCode = -1;
			switch (v.getId()) {
			case R.id.img1:
				intent = new Intent(MainActivity.this, CheckInActivity.class);
				requestCode = 100;
				break;
			case R.id.img2: // anledBtn
				if (currentType == KKListByType.KKLIST_ANALYSED)
					return;
				currentType = KKListByType.KKLIST_ANALYSED;
				requesDataByteType(KKListByType.KKLIST_ANALYSED);
				break;
			case R.id.img3: // waitBtn
				if (currentType == KKListByType.KKLIST_NOT_ANALYSED)
					return;
				currentType = KKListByType.KKLIST_NOT_ANALYSED;
				requesDataByteType(KKListByType.KKLIST_NOT_ANALYSED);
				break;
			case R.id.img5: // alreadyhandleBtn
				if (currentType == KKListByType.KKLIST_HANDLED)
					return;
				currentType = KKListByType.KKLIST_HANDLED;
				requesDataByteType(KKListByType.KKLIST_HANDLED);
				break;
			case R.id.img6:// allBtn
				if (currentType == KKListByType.KKLIST_ALL)
					return;
				currentType = KKListByType.KKLIST_ALL;
				requesDataByteType(KKListByType.KKLIST_ALL);
				break;
			case R.id.img12:
				intent = new Intent(MainActivity.this, NavigationActivity.class);
				break;
			case R.id.img13:

				break;
			case R.id.img14:
				intent = new Intent(MainActivity.this, SettingActivity.class);
				break;
			case R.id.img15:
				intent = new Intent(MainActivity.this, KKSearchActivity.class);
				requestCode = 11;
				break;
			}
			if (intent == null)
				return;
			MainActivity.this.startActivityForResult(intent, requestCode);
			MainActivity.this.overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		}
	};

	@Override
	public void didFinishListByType() {
		stopLoading();
		initValue();
	}

	@Override
	public void didFailedListByType() {
		stopLoading();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 11) {
			initValue();
		}else if(resultCode == 100){
			requesDataByteType(KKListByType.KKLIST_ALL);
		}
	}

	private void requesDataByteType(KKListByType type) {
		startLoading();
		SZHttpRequestManager.sharedManager()
				.listByType(type, MainActivity.this);
	}

	@Override
	public void onBackPressed() {
		LocalUtil.showConfirmDialog(R.string.you_are_qiut, this);
	}

	@Override
	public void onDialogCallback() {
		// finish();
		// int pid = android.os.Process.myPid();
		// android.os.Process.killProcess(pid);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public void onArchiveAction(String action, int archive_id) {
		startLoading();
		SZHttpRequestManager.sharedManager().archiveByAction(archive_id, action, this);
	}

	@Override
	public void didFinishDeleteArchive() {
		stopLoading();
		LocalUtil.showTipDialog(R.string.archive_delete_success)
				.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss(DialogInterface dialog) {
						initValue();
					}
				});
	}

	@Override
	public void didFailedDeleteArchive() {
		stopLoading();
	}

	@Override
	public void didFinishGetDiagnosis(String content) {
		stopLoading();
		Intent intent = new Intent(this, DiagnosisActivity.class);
		intent.putExtra("diagnosis", content);
		startActivity(intent);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}   

	@Override
	public void didFailedGetDiagnosis() {
		stopLoading();    
	}
	
	private void initGCM() {
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
		  GCMRegistrar.register(this, "");
		} else {
		  Log.v("tag1", "Already registered");
		}

	}
}
