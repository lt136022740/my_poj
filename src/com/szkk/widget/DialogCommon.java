package com.szkk.widget;import android.app.Dialog;import android.content.Context;import android.view.View;import android.view.ViewGroup;import android.widget.Button;import android.widget.EditText;import android.widget.Spinner;import android.widget.TextView;import com.szkk.net.delegate.DialogDelegate;import com.szkk.utils.ViewUtil;import com.szkk.www.R;public class DialogCommon extends Dialog {	private Context context;	private String content;	public Button btn;	private DialogDelegate delegate;		public DialogCommon(Context context, int theme, String content) {		super(context, theme);		this.context = context;		this.content = content;		this.getWindow().setBackgroundDrawable(null);	}	public void initTipDialog() {		setContentView(R.layout.dialog_common);		ViewGroup view = (ViewGroup) findViewById(R.id.main_layout);		ViewUtil.zoomView(view);		TextView contentTView = (TextView) findViewById(R.id.content_tview);		contentTView.setText(content);		btn = (Button) findViewById(R.id.btn_close);		btn.setOnClickListener(new View.OnClickListener() {			@Override			public void onClick(View v) {				DialogCommon.this.dismiss();			}		});	}	public void initConfirmDialog() {		setContentView(R.layout.dialog_confirm);		ViewGroup view = (ViewGroup) findViewById(R.id.main_layout);		ViewUtil.zoomView(view);		TextView contentTView = (TextView) findViewById(R.id.content_tview);		contentTView.setText(content);		View.OnClickListener listener = new View.OnClickListener() {			@Override			public void onClick(View v) {				switch(v.getId()){				case R.id.btn_close:					DialogCommon.this.dismiss();					break;				case R.id.btn_confirm:					delegate.onDialogCallback();				}			}		};		Button btnClose = (Button) findViewById(R.id.btn_close);		Button btnConfirm = (Button) findViewById(R.id.btn_confirm);		btnClose.setOnClickListener(listener);		btnConfirm.setOnClickListener(listener);	}	public void initLoadingDialog() {		setContentView(R.layout.dialog_common);		ViewGroup view = (ViewGroup) findViewById(R.id.main_layout);		ViewUtil.zoomView(view);		TextView contentTView = (TextView) findViewById(R.id.content_tview);		contentTView.setText(content);		btn = (Button) findViewById(R.id.btn_close);		btn.setOnClickListener(new View.OnClickListener() {			@Override			public void onClick(View v) {				DialogCommon.this.dismiss();			}		});	}	public void setDelegate(DialogDelegate del) {		this.delegate = del;	}}