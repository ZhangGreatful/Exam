package com.wanzheng.driver.util;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;


/**
 * 加载页面，包括页面的跳转，intent,dialog的创建 举了两个基本的跳转例子
 * 
 * @author Xice
 * 
 */
public class UIHelper {

	/***
	 * Create a file name for the icon photo using current time.创建图片名称
	 */
	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	/*** The launch code when picking a photo and the raw data is returned */
	public static final int PHOTO_PICKED_WITH_DATA = 3021;

	/*** The launch code when taking a picture */
	public static final int CAMERA_WITH_DATA = 3023;

	public static final int PHOTO_CUT_WITH_DATA = 3024;

	public static final File PHOTO_DIR = new File(
			Environment.getExternalStorageDirectory() + "/yellowpage/Camera");
	public static final File PHOTO_DIR_NO_CARD = new File(
			"/data/data/com.cetetek.vlife/files");

	public static File saveToLocal(Context context, Bitmap mBitmap) {
		File save2File;
		if (SDCardUtil.checkSDCard()) {
			UIHelper.PHOTO_DIR.mkdirs();
			save2File = new File(UIHelper.PHOTO_DIR.getPath() + "/"
					+ UIHelper.getPhotoFileName());

			try {
				ImageUtils.saveImageToSD(save2File.getPath(), mBitmap, 100);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			UIHelper.PHOTO_DIR_NO_CARD.mkdirs();
			String fileName = UIHelper.getPhotoFileName();
			save2File = new File(UIHelper.PHOTO_DIR_NO_CARD.getPath() + "/"
					+ fileName);
			try {
				ImageUtils.saveImage(context, fileName, mBitmap);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return save2File;
	}

	/***
	 * Constructs an intent for capturing a photo and storing it in a temporary
	 * file. 跳转到照相界面
	 */
	public static Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	/***
	 * Constructs an intent for picking a photo from Gallery, cropping it and
	 * returning the bitmap. //跳转gallery选择图片
	 */
	public static Intent getPhotoPickIntent(File f, int mWidth) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", mWidth);
		intent.putExtra("outputY", mWidth);
		intent.putExtra("return-data", true);
		return intent;
	}

	/***
	 * Constructs an intent for picking a photo from Gallery, cropping it and
	 * returning the bitmap. //跳转gallery选择图片
	 */
	public static Intent getPhotoPickIntent(File f) {
		Intent intent = new Intent("android.intent.action.PICK");
		intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
				"image/*");
		// intent.putExtra("output", Uri.fromFile(f));
		return intent;
	}

	/**
	 * Constructs an intent for image cropping. 调用图片剪辑程序 剪裁后的图片跳转到新的界面
	 */
	public static Intent getCropImageIntent(Uri photoUri, int mWidth) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(photoUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", mWidth);
		intent.putExtra("outputY", mWidth);
		intent.putExtra("return-data", true);
		return intent;
	}

}
