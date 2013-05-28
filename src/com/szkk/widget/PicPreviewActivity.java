package com.szkk.widget;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.szkk.utils.ViewUtil;
import com.szkk.www.R;
import com.szkk.www.SZActivity;

public class PicPreviewActivity extends SZActivity {
	private ImageView imgView;
	private Bitmap bm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pic_preview);
		initView();
	}

	private void initView() {
		ViewGroup view = (ViewGroup) findViewById(R.id.main_layout);
		ViewUtil.zoomView(view);
		imgView = (ImageView) findViewById(R.id.pic);
		
		try {
			String path = (String) getIntent().getStringExtra("pic_path");
			BitmapFactory.Options ops = new BitmapFactory.Options();
			ops.inSampleSize = 2;
			bm = BitmapFactory.decodeFile(path, ops);
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
			bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
			imgView.setImageBitmap(bm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (bm == null) {
			return;
		}
		bm.recycle();
		bm = null;
		imgView.setImageBitmap(null);
	}
}
