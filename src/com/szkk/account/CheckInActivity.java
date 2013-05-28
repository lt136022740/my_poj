package com.szkk.account;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.szkk.net.SZHttpRequestManager;
import com.szkk.net.delegate.CheckInTaskDelegate;
import com.szkk.net.delegate.FileUploadTaskDelegate;
import com.szkk.utils.LocalUtil;
import com.szkk.utils.ViewUtil;
import com.szkk.widget.DialogCommon;
import com.szkk.widget.PicPreviewActivity;
import com.szkk.www.R;
import com.szkk.www.SZActivity;

public class CheckInActivity extends SZActivity implements CheckInTaskDelegate,
		FileUploadTaskDelegate {
	private EditText nameEdit;
	private RadioGroup radioGroup;
	private TextView ageEdit;
	private Spinner temperatureSpinner;
	private Spinner occupationSpinner;
	private Spinner province_spinner;
	private Spinner city_spinner;
	private Spinner county_spinner;

	private Button optionFillBtn1;
	private Button optionFillBtn2;
	private Button optionFillBtn3;
	private RelativeLayout optionFillLayout1;
	private RelativeLayout optionFillLayout2;
	private RelativeLayout optionFillLayout3;

	private Spinner optionFillLayout1HealthhistorySpinner;
	private Spinner optionFillLayout1EnvironmentSpinner;
	private EditText optionFillLayout1EmailEdit;
	private EditText optionFillLayout1PhoneEdit;
	private EditText optionFillLayout1QQEdit;

	private CheckBox optionFillLayout2Cbzls;
	private CheckBox optionFillLayout2Cbwgfzs;
	private CheckBox optionFillLayout2Cbsbzss;
	private CheckBox optionFillLayout2Cbgxys;
	private CheckBox optionFillLayout2Cbjsbs;
	private CheckBox optionFillLayout2Cbtnbs;
	private CheckBox optionFillLayout2Cbzdsss;
	private CheckBox optionFillLayout2Cbgdttbws;
	private CheckBox optionFillLayout2Cbfslfss;
	private CheckBox optionFillLayout2Cbzdgzsss;

	private EditText optionFillLayout3Editszy;
	private EditText optionFillLayout3Editssy;
	private EditText optionFillLayout3Editlv;
	private EditText optionFillLayout3Edithx;
	private EditText optionFillLayout3Editsg;
	private EditText optionFillLayout3Edittz;

	private Button btnLeftRotate;
	private Button btnRightRotate;
	private Button picPreview;
	private Button btnFromDev;
	private Button btnFromMem;
	private Button btnSend;
	private Button btnClear;

	private ImageView imgView;
	private int gender;
	private String picPath = "";
	private int degrees = 90;
	private Uri mImageUri;
	private Bitmap bitmap;
	private String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_in);

		ViewGroup view = (ViewGroup) findViewById(R.id.main_layout);
		ViewUtil.zoomView(view);

		nameEdit = (EditText) findViewById(R.id.edit_customer_name);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		ageEdit = (TextView) findViewById(R.id.edit_customer_age);
		temperatureSpinner = (Spinner) findViewById(R.id.temperature_spinner);
		occupationSpinner = (Spinner) findViewById(R.id.occupation_spinner);
		province_spinner = (Spinner) findViewById(R.id.address_spinner1);
		city_spinner = (Spinner) findViewById(R.id.address_spinner2);
		county_spinner = (Spinner) findViewById(R.id.address_spinner3);

		optionFillBtn1 = (Button) findViewById(R.id.btn1);
		optionFillBtn2 = (Button) findViewById(R.id.btn2);
		optionFillBtn3 = (Button) findViewById(R.id.btn3);
		optionFillLayout1 = (RelativeLayout) findViewById(R.id.layout_1);
		optionFillLayout2 = (RelativeLayout) findViewById(R.id.layout_2);
		optionFillLayout3 = (RelativeLayout) findViewById(R.id.layout_3);

		optionFillLayout1HealthhistorySpinner = (Spinner) findViewById(R.id.spinner1);
		optionFillLayout1EnvironmentSpinner = (Spinner) findViewById(R.id.spinner2);
		optionFillLayout1EmailEdit = (EditText) findViewById(R.id.edit3);
		optionFillLayout1PhoneEdit = (EditText) findViewById(R.id.edit4);
		optionFillLayout1QQEdit = (EditText) findViewById(R.id.edit5);

		optionFillLayout2Cbzls = (CheckBox) findViewById(R.id.ch1);
		optionFillLayout2Cbwgfzs = (CheckBox) findViewById(R.id.ch2);
		optionFillLayout2Cbsbzss = (CheckBox) findViewById(R.id.ch3);
		optionFillLayout2Cbgxys = (CheckBox) findViewById(R.id.ch4);
		optionFillLayout2Cbjsbs = (CheckBox) findViewById(R.id.ch5);
		optionFillLayout2Cbtnbs = (CheckBox) findViewById(R.id.ch6);
		optionFillLayout2Cbzdsss = (CheckBox) findViewById(R.id.ch7);
		optionFillLayout2Cbgdttbws = (CheckBox) findViewById(R.id.ch8);
		optionFillLayout2Cbfslfss = (CheckBox) findViewById(R.id.ch9);
		optionFillLayout2Cbzdgzsss = (CheckBox) findViewById(R.id.ch10);

		optionFillLayout3Editszy = (EditText) findViewById(R.id.d3_edit1);
		optionFillLayout3Editssy = (EditText) findViewById(R.id.d3_edit2);
		optionFillLayout3Editlv = (EditText) findViewById(R.id.d3_edit3);
		optionFillLayout3Edithx = (EditText) findViewById(R.id.d3_edit4);
		optionFillLayout3Editsg = (EditText) findViewById(R.id.d3_edit5);
		optionFillLayout3Edittz = (EditText) findViewById(R.id.d3_edit6);

		btnLeftRotate = (Button) findViewById(R.id.rotate_left);
		btnRightRotate = (Button) findViewById(R.id.rotate_right);
		picPreview = (Button) findViewById(R.id.pic_preview);
		btnFromDev = (Button) findViewById(R.id.pic_from_dev);
		btnFromMem = (Button) findViewById(R.id.pic_from_m);
		btnSend = (Button) findViewById(R.id.pic_send);
		btnClear = (Button) findViewById(R.id.pic_remove);

		imgView = (ImageView) findViewById(R.id.pic_img);

		btnLeftRotate.setOnClickListener(listener);
		btnRightRotate.setOnClickListener(listener);
		picPreview.setOnClickListener(listener);
		btnFromDev.setOnClickListener(listener);
		btnFromMem.setOnClickListener(listener);
		btnSend.setOnClickListener(listener);
		btnClear.setOnClickListener(listener);

		optionFillBtn1.setFocusable(true);
		optionFillBtn1.setFocusableInTouchMode(true);
		optionFillBtn1.requestFocus();
		optionFillBtn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				optionFillLayout1.setVisibility(View.VISIBLE);
				optionFillLayout2.setVisibility(View.GONE);
				optionFillLayout3.setVisibility(View.GONE);
				optionFillBtn1.setFocusable(true);
				optionFillBtn1.setFocusableInTouchMode(true);
				optionFillBtn1.requestFocus();

				optionFillBtn2.setFocusable(false);
				optionFillBtn2.setFocusableInTouchMode(false);
				optionFillBtn2.requestFocus();

				optionFillBtn3.setFocusable(false);
				optionFillBtn3.setFocusableInTouchMode(false);
				optionFillBtn3.requestFocus();
			}
		});
		optionFillBtn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				optionFillLayout2.setVisibility(View.VISIBLE);
				optionFillLayout1.setVisibility(View.GONE);
				optionFillLayout3.setVisibility(View.GONE);
				optionFillBtn2.setFocusable(true);
				optionFillBtn2.setFocusableInTouchMode(true);
				optionFillBtn2.requestFocus();

				optionFillBtn1.setFocusable(false);
				optionFillBtn1.setFocusableInTouchMode(false);
				optionFillBtn1.requestFocus();

				optionFillBtn3.setFocusable(false);
				optionFillBtn3.setFocusableInTouchMode(false);
				optionFillBtn3.requestFocus();
			}
		});
		optionFillBtn3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				optionFillLayout3.setVisibility(View.VISIBLE);
				optionFillLayout1.setVisibility(View.GONE);
				optionFillLayout2.setVisibility(View.GONE);

				optionFillBtn3.setFocusable(true);
				optionFillBtn3.setFocusableInTouchMode(true);
				optionFillBtn3.requestFocus();

				optionFillBtn1.setFocusable(false);
				optionFillBtn1.setFocusableInTouchMode(false);
				optionFillBtn1.requestFocus();

				optionFillBtn2.setFocusable(false);
				optionFillBtn2.setFocusableInTouchMode(false);
				optionFillBtn2.requestFocus();
			}
		});

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radioMale) {
					gender = 1;
				} else if (checkedId == R.id.radioFemale) {
					gender = 0;
				}
			}
		});

		LocalUtil.instance.select(temperatureSpinner, R.array.temperature_item);
		LocalUtil.instance.select(occupationSpinner, R.array.occupation_item);
		LocalUtil.instance.select(optionFillLayout1HealthhistorySpinner,
				R.array.health_item);
		LocalUtil.instance.select(optionFillLayout1EnvironmentSpinner,
				R.array.environment_item);
		LocalUtil.instance.select(province_spinner, R.array.province_item);
		LocalUtil.instance.initPCCSpinners(province_spinner, city_spinner,
				county_spinner);
	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.rotate_left:
				rotateCW();
				break;
			case R.id.rotate_right:
				rotateCCW();
				break;
			case R.id.pic_preview:
				picPreview();
				break;
			case R.id.pic_from_dev:
				capturePicFromCamera();
				break;
			case R.id.pic_from_m:
				capturePicFromMem();
				break;
			case R.id.pic_send:
				send();
				break;
			case R.id.pic_remove:
				remove();
				break;
			}
		}
	};

	private void send() {
		// /一
		String name = nameEdit.getText().toString();
		String age = ageEdit.getText().toString();
		String temper = temperatureSpinner.getSelectedItem().toString();
		String occupation = occupationSpinner.getSelectedItem().toString();
		String province = province_spinner.getSelectedItem().toString();
		String city = city_spinner.getSelectedItem().toString();
		String country = county_spinner.getSelectedItem().toString();

		// /二
		StringBuffer sbHealthHis = new StringBuffer();
		String healthHistory = optionFillLayout1HealthhistorySpinner
				.getSelectedItem().toString();
		String env = optionFillLayout1EnvironmentSpinner.getSelectedItem()
				.toString();
		String email = optionFillLayout1EmailEdit.getText().toString();
		String phone = optionFillLayout1PhoneEdit.getText().toString();
		String qq = optionFillLayout1QQEdit.getText().toString();

		sbHealthHis.append(healthHistory + "," + env + ",");
		if (email != null && !email.equals("")) {
			sbHealthHis.append(email + ",");
		} else {
			sbHealthHis.append("0,");
		}
		if (phone != null && !phone.equals("")) {
			sbHealthHis.append(phone + ",");
		} else {
			sbHealthHis.append("0,");
		}
		if (qq != null && !qq.equals("")) {
			sbHealthHis.append(qq + ",");
		} else {
			sbHealthHis.append("0");
		}

		// /三
		StringBuffer sbDiagnosis = new StringBuffer();
		if (optionFillLayout2Cbzls.isChecked()) {
			sbDiagnosis.append("肿瘤史,");
		} else {
			sbDiagnosis.append("0,");
		}
		if (optionFillLayout2Cbwgfzs.isChecked()) {
			sbDiagnosis.append("外感发热,");
		} else {
			sbDiagnosis.append("0,");
		}
		if (optionFillLayout2Cbsbzss.isChecked()) {
			sbDiagnosis.append("手部损伤,");
		} else {
			sbDiagnosis.append("0,");
		}
		if (optionFillLayout2Cbgxys.isChecked()) {
			sbDiagnosis.append("高血压史,");
		} else {
			sbDiagnosis.append("0,");
		}
		if (optionFillLayout2Cbjsbs.isChecked()) {
			sbDiagnosis.append("精神病史,");
		} else {
			sbDiagnosis.append("0,");
		}
		if (optionFillLayout2Cbtnbs.isChecked()) {
			sbDiagnosis.append("糖尿病史,");
		} else {
			sbDiagnosis.append("0,");
		}
		if (optionFillLayout2Cbzdsss.isChecked()) {
			sbDiagnosis.append("重大手术史,");
		} else {
			sbDiagnosis.append("0,");
		}
		if (optionFillLayout2Cbgdttbws.isChecked()) {
			sbDiagnosis.append("固定疼痛部位,");
		} else {
			sbDiagnosis.append("0,");
		}
		if (optionFillLayout2Cbfslfss.isChecked()) {
			sbDiagnosis.append("风湿和类风湿史,");
		} else {
			sbDiagnosis.append("0,");
		}
		if (optionFillLayout2Cbzdgzsss.isChecked()) {
			sbDiagnosis.append("重大骨折损伤史,");
		} else {
			sbDiagnosis.append("0");
		}

		// /四
		StringBuffer sbTest = new StringBuffer();
		String szy = optionFillLayout3Editszy.getText().toString();
		String ssy = optionFillLayout3Editssy.getText().toString();
		String xl = optionFillLayout3Editlv.getText().toString();
		String hx = optionFillLayout3Edithx.getText().toString();
		String sg = optionFillLayout3Editsg.getText().toString();
		String tz = optionFillLayout3Edittz.getText().toString();

		if (szy != null && !szy.equals("")) {
			sbTest.append(szy + ",");
		} else {
			sbTest.append("0,");
		}
		if (ssy != null && !ssy.equals("")) {
			sbTest.append(ssy + ",");
		} else {
			sbTest.append("0,");
		}
		if (xl != null && !xl.equals("")) {
			sbTest.append(xl + ",");
		} else {
			sbTest.append("0,");
		}
		if (hx != null && !hx.equals("")) {
			sbTest.append(hx + ",");
		} else {
			sbTest.append("0,");
		}
		if (sg != null && !sg.equals("")) {
			sbTest.append(sg + ",");
		} else {
			sbTest.append("0,");
		}
		if (tz != null && !tz.equals("")) {
			sbTest.append(tz + ",");
		} else {
			sbTest.append("0");
		}

		if (name == null       || name.equals("")       || 
			age == null        || age.equals("")        || 
			temper == null     || temper.equals("")     ||
			occupation == null || occupation.equals("") ||
			province == null   || province.equals("")   || 
			city == null       || city.equals("")       || 
			country == null    || country.equals("")) 
		{
			LocalUtil.showTipDialog(R.string.must_fill_not_to_be_null);
			return;
		}
		if (bitmap == null) {
			LocalUtil.showTipDialog(R.string.must_fill_pic_not_to_be_null);
			return;
		}
		// 处理

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
		try {
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			JSONObject data = new JSONObject();
			data.put("name", name);
			data.put("age", 13);
			data.put("current_env_temp", temper);
			data.put("gender", 0);
			data.put("occupation", occupation);
			data.put("address", province + "," + city + "," + country);
			data.put("base_info", sbHealthHis);
			data.put("diagnose_info", sbDiagnosis);
			data.put("test", sbTest);
			dialog = LocalUtil.showLoadingDialog(R.string.submitting_data);
			SZHttpRequestManager.sharedManager().checkIn(data.toString(), this);
		} catch (JSONException e) {
		}
	}

	private DialogCommon dialog;

	private void remove() {
		nameEdit.setText("");
		ageEdit.setText("");

		optionFillLayout1EmailEdit.setText("");
		optionFillLayout1PhoneEdit.setText("");
		optionFillLayout1QQEdit.setText("");

		optionFillLayout2Cbzls.setChecked(false);
		optionFillLayout2Cbwgfzs.setChecked(false);
		optionFillLayout2Cbsbzss.setChecked(false);
		optionFillLayout2Cbgxys.setChecked(false);
		optionFillLayout2Cbjsbs.setChecked(false);
		optionFillLayout2Cbtnbs.setChecked(false);
		optionFillLayout2Cbzdsss.setChecked(false);
		optionFillLayout2Cbgdttbws.setChecked(false);
		optionFillLayout2Cbfslfss.setChecked(false);
		optionFillLayout2Cbzdgzsss.setChecked(false);

		optionFillLayout3Editszy.setText("");
		optionFillLayout3Editssy.setText("");
		optionFillLayout3Editlv.setText("");
		optionFillLayout3Edithx.setText("");
		optionFillLayout3Editsg.setText("");
		optionFillLayout3Edittz.setText("");
		if(bitmap != null){
			bitmap.recycle();
			bitmap = null;
		}
		imgView.setImageBitmap(null);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if(data == null){
		// return;
		// }
		Animation anim_go = AnimationUtils.loadAnimation(this,
				R.anim.anim_alpha_go);
		final Animation anim_come = AnimationUtils.loadAnimation(this,
				R.anim.anim_alpha_come);
		anim_go.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				imgView.setImageBitmap(bitmap);
				imgView.startAnimation(anim_come);
			}
		});
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 99:
				grabImage();
				break;
			case 100:
				storePicFromMem(data);
				break;
			}
			imgView.startAnimation(anim_go);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (bitmap == null)
			return;
		bitmap.recycle();
		bitmap = null;
	}

	private void capturePicFromCamera() {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		File photo = null;
		try {
			photo = createTemporaryFile("picture", ".jpg");
			photo.delete();
		} catch (Exception e) {
		}
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			LocalUtil.showTipDialog(R.string.sdcard_not_ready);
			return;
		}
		mImageUri = Uri.fromFile(photo);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
		startActivityForResult(intent, 99);
	}

	private void capturePicFromMem() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, 100);
	}

	private void storePicFromMem(Intent data) {
		Uri uri = data.getData();
		ContentResolver cr = this.getContentResolver();
		Cursor c = cr.query(uri, null, null, null, null);
		c.moveToFirst();
		path = c.getString(c.getColumnIndex("_data"));
		BitmapFactory.Options ops = new BitmapFactory.Options();
		ops.inSampleSize = 4;
		String subfex = path
				.substring(path.lastIndexOf(".") + 1, path.length());
		if (subfex != null) {
			if (!subfex.equals("jpg") && !subfex.equals("png")
					&& !subfex.equals("jpeg")) {
				LocalUtil.showTipDialog(R.string.pic_format);
				return;
			}
		}
		bitmap = BitmapFactory.decodeFile(path, ops);
	}

	private void rotateCW() {
		if (bitmap == null)
			return;
		Matrix m = new Matrix();
		m.setRotate(degrees);
		Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), m, true);
		bitmap.recycle();
		bitmap = null;
		bitmap = b2;
		imgView.setImageBitmap(bitmap);
	}

	private void rotateCCW() {
		if (bitmap == null)
			return;
		Matrix m = new Matrix();
		m.setRotate(-degrees);
		Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), m, true);
		bitmap.recycle();
		bitmap = null;
		bitmap = b2;
		imgView.setImageBitmap(bitmap);
	}

	private void picPreview() {
		if (bitmap == null) {
			return;
		}
		Intent intent = new Intent(this, PicPreviewActivity.class);
		// String path = mImageUri.toString();
		// path = path.substring(7, path.length());
		intent.putExtra("pic_path", path);
		startActivity(intent);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	public void grabImage() {
		this.getContentResolver().notifyChange(mImageUri, null);
		try {
			// bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr,
			// mImageUri);
			BitmapFactory.Options ops = new BitmapFactory.Options();
			ops.inSampleSize = 2;
			path = mImageUri.toString();
			path = path.substring(7, path.length());
			if (bitmap != null) {
				bitmap.recycle();
				bitmap = null;
			}
			bitmap = BitmapFactory.decodeFile(path, ops);
		} catch (Exception e) {
		}
	}

	private File createTemporaryFile(String part, String ext) throws Exception {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			File tempDir = Environment.getExternalStorageDirectory();
			tempDir = new File(tempDir.getAbsolutePath() + "/temp/");
			if (!tempDir.exists()) {
				tempDir.mkdir();
			}
			return File.createTempFile(part, ext, tempDir);
		} else {
			return null;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mImageUri != null)
			outState.putString("mImageUri", mImageUri.toString());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		try {
			String mImageUri = savedInstanceState.getString("mImageUri");
			if (mImageUri != null && !mImageUri.equals("")) {
				BitmapFactory.Options ops = new BitmapFactory.Options();
				ops.inSampleSize = 2;
				String tempp = mImageUri.toString();
				path = tempp.substring(7, tempp.length());
				bitmap = BitmapFactory.decodeFile(path, ops);
				ExifInterface exif = new ExifInterface(path);
				int tag = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
						-1);
				int angle = 0;
				if (tag == ExifInterface.ORIENTATION_ROTATE_90) {
					angle = 90;
				} else if (tag == ExifInterface.ORIENTATION_ROTATE_180) {
					angle = 180;
				} else if (tag == ExifInterface.ORIENTATION_ROTATE_270) {
					angle = 270;
				}
				Matrix m = new Matrix();
				m.setRotate(angle);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), m, true);
				imgView.setImageBitmap(bitmap);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private DialogCommon dialog2;

	@Override
	public void didFinishCheckIn(int id) {
		dialog.dismiss();
		dialog2 = LocalUtil.showLoadingDialog(R.string.submitting_pic);
		SZHttpRequestManager.sharedManager().uploadFile(this, id,
				new File(path));
	}

	@Override
	public void didFailedCheckIn() {
		dialog.dismiss();
		LocalUtil.showTipDialog(R.string.common_net_error);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			DialogCommon dm = LocalUtil
					.showTipDialog(R.string.check_in_success);
			dm.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					setResult(100);
					finish();
					overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);
				}
			});
		}

	};

	@Override
	public void didFinishUoload() {
		dialog2.dismiss();
		handler.sendEmptyMessage(0);
	}

	private Handler handler2 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			dialog2.dismiss();
			LocalUtil.showTipDialog(R.string.check_in_pic_upload_failed);
		}

	};

	@Override
	public void didFailedUpload() {
		handler2.sendEmptyMessage(0);
	}
}
