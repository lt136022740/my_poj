package com.szkk.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.szkk.www.R;
import com.szkk.www.SZActivity;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewUtil {
	public static float strethRate = 0;
	private static float standard_width = 480.0f;
	private static float standard_height = 800.0f;
	public static List<Activity> activityList = new ArrayList<Activity>();
	public static Context context;
	public static SZActivity shareActivity;

	public static double getSuitableStrethRate() {
		Display display = shareActivity.getWindowManager().getDefaultDisplay();
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();
		int curWidth = screenWidth;
		int curHeight = screenHeight;
		float height = standard_height;
		float weight = standard_width;
		if (height * curWidth / weight > curHeight) {
			strethRate = curHeight / height;
		} else {
			strethRate = curWidth / weight;
		}
		if (strethRate == 0) return 1;
		return strethRate;
	}

	public static boolean verifyZoomRate() {
		if (Math.abs(strethRate - 1) < 0.000001 || strethRate <= 0.000001) {
			checkZoomRate();
			if (Math.abs(strethRate - 1) < 0.000001 || strethRate <= 0.000001) {
				return false;
			}
		}
		return true;
	}

	public static void checkZoomRate() {
		if (shareActivity != null) {
			checkZoomRate(shareActivity);
		}
	}

	public static int convertToVerticeScreen(int num) {
		if (strethRate == 0)
			return num;
		return (int) (getRateHeight() * num);
	}
	
	public static int convertToCurScreen(int num) {
		if (strethRate == 0)
			return num;
		return (int) (strethRate * num);
	}

	public static int convertMarginHorizontal(int num) {
		double h_rate = strethRate;
		return (int) (h_rate * num);
	}

	public static int convertMarginVertical(int num) {
		double v_rate = strethRate;
		return (int) (v_rate * num);
	}

	public static enum ConvertType {
		WEIGHT, HEIGHT
	}

	public static int convertToCurScreen(int num, ConvertType type) {
		/*
		 * if (type == ConvertType.WEIGHT) { if (DeviceInfo.screenWidth > 600) {
		 * return num * DeviceInfo.screenWidth / 720; } else { return num *
		 * DeviceInfo.screenWidth / 480; } } else { if (DeviceInfo.screenWidth >
		 * 600) { return num * DeviceInfo.screenHeight / 1280; } else { return
		 * num * DeviceInfo.screenHeight / 800; } }
		 */
		if (strethRate == 0)
			return num;
		return (int) (strethRate * num);
	}

	public static void checkZoomRate(Activity activity) {
		// if (convertToCurScreen(100) == 0) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();

		/*
		 * if(screenWidth > screenHeight) { DeviceInfo.screenWidth =
		 * screenHeight; DeviceInfo.screenHeight = screenWidth; }else {
		 * DeviceInfo.screenWidth = screenWidth; DeviceInfo.screenHeight =
		 * screenHeight; }
		 */
		// }
		getSuitableStrethRate();
	}

	private static void setLayoutParam(View view, ViewGroup.LayoutParams lp) {

		view.setPadding(convertToCurScreen(view.getPaddingLeft()),
				convertToCurScreen(view.getPaddingTop()),
				convertToCurScreen(view.getPaddingRight()),
				convertToCurScreen(view.getPaddingBottom()));

		if (lp instanceof RelativeLayout.LayoutParams) {
			RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) lp;
			setRelativeLayoutParam(view, rlp);
		} else if (lp instanceof LinearLayout.LayoutParams) {
			LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) lp;
			setLinerLayoutParam(view, llp);
		} else {
			setNormalLayoutParam(view);
		}
	}

	private static void setLoadingViewParam(View view,
			RelativeLayout.LayoutParams rlp) {
		rlp.bottomMargin = convertMarginVertical(rlp.bottomMargin);
		rlp.leftMargin = convertMarginHorizontal(rlp.leftMargin);
		rlp.topMargin = convertMarginVertical(rlp.topMargin);
		rlp.rightMargin = convertMarginHorizontal(rlp.rightMargin);
		view.setLayoutParams(rlp);
	}

	private static void setNormalLayoutParam(View view) {
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		if (lp == null)
			return;
		if (lp.height > 0) {
			lp.height = convertToCurScreen(lp.height);
		}
		if (lp.width > 0) {
			lp.width = convertToCurScreen(lp.width);
		}
		view.setLayoutParams(lp);
	}

	private static void strechWithRealWidth(ViewGroup view,
			RelativeLayout.LayoutParams rlp) {
		if (rlp.height > 0) {
			rlp.height = convertToCurScreen(rlp.height);
		}
		if (rlp.width > 0) {
			rlp.width = (int) (rlp.width * getRateWidth());
		}

		rlp.bottomMargin = convertMarginVertical(rlp.bottomMargin);
		rlp.leftMargin = convertMarginHorizontal(rlp.leftMargin);
		rlp.topMargin = convertMarginVertical(rlp.topMargin);
		rlp.rightMargin = convertMarginHorizontal(rlp.rightMargin);
		view.setLayoutParams(rlp);
	}

	private static void setRelativeLayoutParam(View view,
			RelativeLayout.LayoutParams rlp) {
		if (rlp.height > 0) {
		
				rlp.height = convertToCurScreen(rlp.height);
			
		}
		if (rlp.width > 0) {
			rlp.width = convertToCurScreen(rlp.width);
		}
		// setNorLayParam(view);
		/*
		 * rlp.bottomMargin =convertToCurScreen(rlp.bottomMargin);
		 * rlp.leftMargin = convertToCurScreen(rlp.leftMargin); rlp.topMargin =
		 * convertToCurScreen(rlp.topMargin); rlp.rightMargin =
		 * convertToCurScreen(rlp.rightMargin);
		 */
		rlp.bottomMargin = convertMarginVertical(rlp.bottomMargin);
		rlp.leftMargin = convertMarginHorizontal(rlp.leftMargin);
		rlp.topMargin = convertMarginVertical(rlp.topMargin);
		rlp.rightMargin = convertMarginHorizontal(rlp.rightMargin);
		view.setLayoutParams(rlp);
	}

	private static void setLinerLayoutParam(View view,
			LinearLayout.LayoutParams llp) {
		if (llp.height > 0) {
			llp.height = convertToCurScreen(llp.height);
		}
		llp.width = convertToCurScreen(llp.width);
		// setNorLayParam(view);
		/*
		 * llp.bottomMargin = convertToCurScreen(llp.bottomMargin);
		 * llp.leftMargin = convertToCurScreen(llp.leftMargin); llp.topMargin =
		 * convertToCurScreen(llp.topMargin); llp.rightMargin =
		 * convertToCurScreen(llp.rightMargin);
		 */
		llp.bottomMargin = convertMarginVertical(llp.bottomMargin);
		llp.leftMargin = convertMarginHorizontal(llp.leftMargin);
		llp.topMargin = convertMarginVertical(llp.topMargin);
		llp.rightMargin = convertMarginHorizontal(llp.rightMargin);
		view.setLayoutParams(llp);
	}

	public static void setFullScreen(ViewGroup rlayout) {
		ViewGroup.LayoutParams lp = rlayout.getLayoutParams();
		if (lp == null) {
			return;
		}
		setFullScreen(rlayout, lp);
	}

	public static void stechWithRealSize(View rlayout) {
		ViewGroup.LayoutParams lp = rlayout.getLayoutParams();
		if (lp == null) {
			return;
		}
		stechWithRealSize(rlayout, lp);
	}

	private static void stechWithRealSize(View view, ViewGroup.LayoutParams rlp) {
		rlp.width = (int) (getRateWidth() * rlp.width);
		rlp.height = (int) (getRateHeight() * rlp.height);
		view.setLayoutParams(rlp);
	}

	private static void setFullScreen(ViewGroup view, ViewGroup.LayoutParams rlp) {
		// if (rlp.height > 0) {
		// rlp.height = convertToCurScreen(rlp.height);
		// }
		// if (rlp.width > 0) {
		// rlp.width = convertToCurScreen(rlp.width);
		// }  
		// setNorLayParam(view);
		// rlp.bottomMargin =convertToCurScreen(rlp.bottomMargin);
		// rlp.leftMargin = convertToCurScreen(rlp.leftMargin);
		// rlp.topMargin = convertToCurScreen(rlp.topMargin);
		// rlp.rightMargin = convertToCurScreen(rlp.rightMargin);
		rlp.width = (int) (getRateWidth() * rlp.width);
		rlp.height = (int) (getRateHeight() * rlp.height);
		view.setLayoutParams(rlp);
	}

	public static double getRateWidth() {
		Display display = shareActivity.getWindowManager().getDefaultDisplay();
		int screenWidth = display.getWidth();
		float rate = screenWidth / 480.0f;
		return rate;
	}

	public static double getRateHeight() {
		Display display = shareActivity.getWindowManager().getDefaultDisplay();
		int screenHeight = display.getHeight();
		float rate = screenHeight / 800.0f;
		return rate;
	}

	public static void handleText(View view) {
		float textSize = 0;
		TextView tView = null;
		EditText eText = null;
		float density = shareActivity.getResources().getDisplayMetrics().density;
		if (view instanceof TextView) {
			tView = ((TextView) view);
			textSize = tView.getTextSize();
			tView.setTextSize((int) convertToCurScreen((int) (textSize / density)));
		} else if (view instanceof EditText) {
			eText = ((EditText) view);
			textSize = eText.getTextSize();
			eText.setTextSize((int) convertToCurScreen((int) (textSize / density)));
		}
	}

	/**
	 * 获取网络图片的数据
	 * 
	 * @param path
	 *            网络图片路径
	 * @return
	 */
	public static byte[] getImage(String path) throws Exception {
		URL url = new URL(path); 
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 基于HTTP协议连接对象
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			InputStream inStream = conn.getInputStream();
			return read(inStream);
		}
		return null;
	}

	/**
	 * 读取流中的数据
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] read(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	private static void setLinerLayoutParamWithEqualPropertion(View view,
			LinearLayout.LayoutParams llp) {
		if (llp.height > 0) {
			llp.height = convertToCurScreen(llp.height);
		}
		if (llp.width > 0) {
			llp.width = convertToCurScreen(llp.width);
		}

		llp.bottomMargin = convertToCurScreen(llp.bottomMargin);
		llp.leftMargin = convertToCurScreen(llp.leftMargin);
		llp.topMargin = convertToCurScreen(llp.topMargin);
		llp.rightMargin = convertToCurScreen(llp.rightMargin);

		view.setLayoutParams(llp);
	}

	private static void setRelativeLayoutParamWithEqualPropertion(View view,
			RelativeLayout.LayoutParams rlp) {
		if (rlp.height > 0) {
			rlp.height = convertToCurScreen(rlp.height);
		}
		if (rlp.width > 0) {
			rlp.width = convertToCurScreen(rlp.width);
		}

		rlp.bottomMargin = convertToCurScreen(rlp.bottomMargin);
		rlp.leftMargin = convertToCurScreen(rlp.leftMargin);
		rlp.topMargin = convertToCurScreen(rlp.topMargin);
		rlp.rightMargin = convertToCurScreen(rlp.rightMargin);

		view.setLayoutParams(rlp);
	}

	private static void setLayoutParamWithEqualPropertion(View view,
			ViewGroup.LayoutParams lp) {

		view.setPadding(convertToCurScreen(view.getPaddingLeft()),
				convertToCurScreen(view.getPaddingTop()),
				convertToCurScreen(view.getPaddingRight()),
				convertToCurScreen(view.getPaddingBottom()));

		if (lp instanceof RelativeLayout.LayoutParams) {
			RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) lp;
			setRelativeLayoutParamWithEqualPropertion(view, rlp);
		} else if (lp instanceof LinearLayout.LayoutParams) {
			LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) lp;
			setLinerLayoutParamWithEqualPropertion(view, llp);
		} else {
			setNormalLayoutParam(view);
		}
	}

	public static void zoomViewWithEqualPropertion(ViewGroup viewGroup) {
		if (!verifyZoomRate())
			if (getRateHeight() < 1.00000 || getRateWidth() < 1.00000)
				return;
		if (viewGroup == null)
			return;
		ViewGroup.LayoutParams lp = viewGroup.getLayoutParams();
		setLayoutParamWithEqualPropertion(viewGroup, lp);
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View view = viewGroup.getChildAt(i);
			lp = view.getLayoutParams();
			if (view instanceof ViewGroup) {
				zoomViewWithEqualPropertion((ViewGroup) view);
			} else {
				setLayoutParamWithEqualPropertion(view, lp);
				handleText(view);
			}
		}
	}

	public static void zoomView(ViewGroup viewGroup) {
		if (!verifyZoomRate()) return;
		if (viewGroup == null)
			return;
		ViewGroup.LayoutParams lp = viewGroup.getLayoutParams();
		if(
				viewGroup.getId() == R.id.main_layout         				    ||
				viewGroup.getId() == R.id.main_list_view        			    ||
				viewGroup.getId() == R.id.activity_main_banner     				||
				viewGroup.getId() == R.id.activity_main_catalog_layout          ||
				viewGroup.getId() == R.id.activity_main_title_tview
			){
			setFullScreen(viewGroup);
		}else{
			setLayoutParam(viewGroup, lp);
		}
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			final View view = viewGroup.getChildAt(i);
			lp = view.getLayoutParams();
			if (view instanceof ViewGroup) {
				zoomView((ViewGroup) view);
			} else {
				setLayoutParam(view, lp);
				handleText(view);
			}
		}
	}
}
