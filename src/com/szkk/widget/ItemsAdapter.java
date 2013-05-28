package com.szkk.widget;import android.content.Context;import android.view.View;import android.view.ViewGroup;import android.widget.BaseAdapter;public class ItemsAdapter extends BaseAdapter{	private Context context;	private int type;		public ItemsAdapter(Context context, int type){		this.context = context;		this.type = type;	}		@Override	public int getCount() {		return 10;	}	@Override	public Object getItem(int position) {		return null;	}	@Override	public long getItemId(int position) {		return 0;	}	@Override	public View getView(int position, View convertView, ViewGroup parent) {		if(11 == type){			ItemConsultManager item = null;			if(convertView == null){				item = new ItemConsultManager(context);			}else{				item = (ItemConsultManager)convertView;			}			return item;		}else if(12 == type){			ItemComplainManager item = null;			if(convertView == null){				item = new ItemComplainManager(context);			}else{				item = (ItemComplainManager)convertView;			}			return item;		}else{			ItemNotificationManager item = null;			if(convertView == null){				item = new ItemNotificationManager(context);			}else{				item = (ItemNotificationManager)convertView;			}			return item;		}	}}