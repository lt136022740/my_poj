//#ifdef PACKAGE_NAME
//#expand package %PACKAGE_NAME%.utils;
//@package %PACKAGE_NAME%.utils;
//#else
package com.szkk.utils;
//#endif

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;


public class ImageDownloader {
	private static String path;
	private static ImageDownloaderDelegate delegate;
	private String imageName;
	private String imagePath;
	private static ImageDownloader instance = new ImageDownloader();
	
	
	public static ImageDownloader getInstance(String path, ImageDownloaderDelegate delegate){
		ImageDownloader.path = path;
		ImageDownloader.delegate = delegate;
		instance.getImageName();
		instance.getImagePath();
		return instance;
	}
	
	private ImageDownloader() {
	}

	public void start() {
		Drawable drawable = readFile(imagePath);
		if(drawable != null) {
			delegate.didFinishedDownImage(drawable);
		}else {
			Thread thread = new Thread(runnable);
			thread.start();
		}
	}
	

	public static void clearAllImage() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			String Path = sdDir.toString() + "/tap4fun/kings_empire";
			delAllFile(Path);
		}
	}

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			try {
				Bitmap bitmap = null;
				Drawable drawable = null;
				byte[] data = getImage();
				
				bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				drawable = new BitmapDrawable(bitmap);
				delegate.didFinishedDownImage(drawable);
				
				writeFile(imagePath, data);
			} catch (Exception e) {
				Log.i("tag1", "e" + e);
				delegate.didFailedDownImage(-1);
				e.printStackTrace();
			} 
		}
	};

	public void writeFile(String path, byte[] content) {  
		  
        FileOutputStream fos;
		try {
			fos = new FileOutputStream(path);
			fos.write(content);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
    } 
	
	/**
	 * 获取网络图片的数据
	 * 
	 * @param path
	 *            网络图片路径
	 * @return
	 * @throws IOException 
	 */
	private byte[] getImage() throws IOException , Exception{
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
	private byte[] read(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	private Drawable readFile(String path) {
		if(path == null || path.equals("")){
			return null;
		}
		File file = new File(path);
		Drawable drawable = null;
		if (file.exists()) {
			if(file.length() > 0) {
				Bitmap bitmap = BitmapFactory.decodeFile(path);
				drawable = new BitmapDrawable(bitmap);
			}
			
		} else {
		}
		return drawable;
	}

	private void getImageName() {
		if (path != null && !path.equals("")) {
			imageName = path.substring(path.lastIndexOf("/"));
			if(imageName.contains("?")) {
				imageName = imageName.substring(0, imageName.lastIndexOf("?"));
			}
		}
	}

	private void getImagePath() {
		if (imageName != null && !imageName.equals("")) {
			File sdDir = null;
			boolean sdCardExist = Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
			if (sdCardExist) {
				sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
				createDir(sdDir.toString() + "/tap4fun");
				createDir(sdDir.toString() + "/tap4fun/kings_empire");
				imagePath = sdDir.toString() + "/tap4fun/kings_empire" +imageName;
				try {
					createFile(imagePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void createFile(String path) throws IOException{       
    	File file=new File(path);
        if(!file.exists())
            file.createNewFile();
    }
    
	private void createDir(String path){
        File dir=new File(path);
        if(!dir.exists())
            dir.mkdirs();
    }
	
	private static void delFolder(String folderPath) {
    	try {
    		delAllFile(folderPath); 
    		
    		String filePath = folderPath;
    		filePath = filePath.toString();
    		java.io.File myFilePath = new java.io.File(filePath);
    		myFilePath.delete(); 
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
	
	private static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			}
			else{
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path+"/"+ tempList[i]);
				delFolder(path+"/"+ tempList[i]);
			}
		}
	}
}
