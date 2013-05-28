package com.szkk.setting;import android.os.Bundle;import android.view.View;import android.view.ViewGroup;import android.widget.Button;import android.widget.ListView;import android.widget.RelativeLayout;import com.szkk.utils.ViewUtil;import com.szkk.widget.DialogConsultSearch;import com.szkk.widget.ItemsAdapter;import com.szkk.www.R;import com.szkk.www.SZActivity;public class ConsultActivity extends SZActivity{	private final static int CONSULT = 11;	private final static int COMPLAIN = 12;	private final static int NOTIFY = 13;		private ListView listView;	private RelativeLayout layoutConsult;	private RelativeLayout layoutComplain;	private RelativeLayout layoutNotify;		private Button searchBtn;		@Override	protected void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		setContentView(R.layout.activity_consult);		initView();	}	private void initView() {		ViewGroup view = (ViewGroup) findViewById(R.id.main_layout);		ViewUtil.zoomView(view);				layoutConsult = (RelativeLayout) findViewById(R.id.layout_consult_manager_banner);		layoutComplain = (RelativeLayout) findViewById(R.id.layout_complain_manager_banner);		layoutNotify = (RelativeLayout) findViewById(R.id.layout_notification_manager_banner);		listView = (ListView) findViewById(R.id.lisview);		searchBtn = (Button) findViewById(R.id.btn_search); 		layoutConsult.setVisibility(View.GONE);		layoutComplain.setVisibility(View.GONE);		layoutNotify.setVisibility(View.GONE);				int type = getIntent().getIntExtra("type", -1);		if(type == 11){			layoutConsult.setVisibility(View.VISIBLE);		}else if(type == 12){			layoutComplain.setVisibility(View.VISIBLE);		}else{			layoutNotify.setVisibility(View.VISIBLE);		}				ItemsAdapter adapter = new ItemsAdapter(this, type);		listView.setAdapter(adapter);				searchBtn.setOnClickListener(new View.OnClickListener() {						@Override			public void onClick(View v) {				DialogConsultSearch dialog = new DialogConsultSearch(ConsultActivity.this, R.style.alert_dialog);				dialog.show();			}		});	}	}