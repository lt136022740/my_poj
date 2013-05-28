package com.szkk.widget;import android.app.Dialog;import android.content.Context;import android.view.View;import android.view.ViewGroup;import android.widget.Button;import android.widget.EditText;import android.widget.Spinner;import android.widget.TextView;import com.szkk.utils.ViewUtil;import com.szkk.www.R;public class DialogConsultSearch extends Dialog{	private Context context;	private EditText titleEdit;	private EditText contentEdit;	private Spinner catalogSpinner;	private Button confirmBtn;		public DialogConsultSearch(Context context, int theme) {		super(context, theme);		this.context = context;		setContentView(R.layout.dialog_consult_search);		initView();	}		private void initView(){		ViewGroup view = (ViewGroup) findViewById(R.id.main_layout);		ViewUtil.zoomView(view);				titleEdit = (EditText) findViewById(R.id.edit_search_title);		contentEdit = (EditText) findViewById(R.id.edit_search_content);		catalogSpinner = (Spinner) findViewById(R.id.spinner_search_catalog);		confirmBtn = (Button) findViewById(R.id.btn_search);		confirmBtn.setOnClickListener(new View.OnClickListener() {						@Override			public void onClick(View v) {							}		});	}}