package com.wanzheng.driver.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 * SD卡工具类
 */
public class SDCardUtil {
	private static final String TAG = SDCardUtil.class.getSimpleName();

	/** 图片存储根目录 */
	private static final String PIC_ROOT_PATH = "/cetetek/pic/";
	/** 1M */
	private static final long MB = 1024 * 1024;
	/** 图片存储空间总大小 20M */
	private static final long PIC_CACHE_SIZE = 20;
	/** 图片存储数量 100个 */
	private static final long PIC_CACHE_COUNT = 100;

	static {
		// 初始化图片保存路径
		checkPicPath(getPicRootPath());
	}

	/**
	 * 保存图片信息到SD卡
	 */
	public static void savePicToSd(Bitmap bm, String imageUrl) {
		// 判断图片存储目录是否存在
		if (!checkPicPath(getPicRootPath()))
			return;

		if (bm == null || "".equals(imageUrl))
			return;

		// 判断sdcard是否可用
		if (!checkSDCard())
			return;
		// 如果文件夹大小超过限制则清空图片缓存文件夹
		File picRootPath = new File(getPicRootPath());
		if (PIC_CACHE_COUNT < fileCount(picRootPath))
			cleanFolder(picRootPath);

		// 判断sdcard上的空间是否够用
		if (PIC_CACHE_SIZE > getFreeSpaceOnSd())
			return;

		OutputStream outStream = null;
		try {
			String picName = convertUrlToFileName(imageUrl);
			File file = new File(getPicRootPath() + picName);
			outStream = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			outStream.flush();
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage(), e);

		} finally {
			closeOutputStream(outStream);
		}
	}

	/**
	 * 根据图片URL， 获取图片对象
	 */
	public static Bitmap getPicToSd(String picUrl) {
		try {
			if ("".equals(picUrl))
				return null;

			// 根据图片url获取图片路径
			String picPath = getPicPath(picUrl);
			if (!checkPicExists(picPath))
				return null;

			// 修改图片最后访问时间
			updateFileTime(picPath);
			return BitmapFactory.decodeFile(picPath);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 判断手机 SD卡是否可用
	 */
	public static boolean checkSDCard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取图片存储根目录
	 */
	private static String getPicRootPath() {
		return Environment.getExternalStorageDirectory().getPath() + PIC_ROOT_PATH;
	}

	/**
	 * 获取SD卡上的空闲空间大小 单位M
	 */
	private static int getFreeSpaceOnSd() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB;
		return (int) sdFreeMB;
	}

	/**
	 * 对图片URL进行MD5加密 作为图片名
	 */
	private static String convertUrlToFileName(String picUrl) throws Throwable {
		return getMD5(picUrl);
	}

	/**
	 * 获得对字符串进行MD5加密后的结果字符串
	 */
	private static String getMD5(String value) {
		if ("".equals(value))
			return null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(value.getBytes("UTF-8"));
			return toHexString(md.digest());
		} catch (Throwable e) {
			return null;
		}
	}

	/**
	 * 获得指定byte[]对象中的所有byte值的16进制形式的结果字符串
	 */
	private static String toHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Character.forDigit((bytes[i] & 0XF0) >> 4, 16));
			sb.append(Character.forDigit(bytes[i] & 0X0F, 16));
		}
		return sb.toString();
	}

	/**
	 * 根据图片URL生成图片存储路径
	 */
	private static String getPicPath(String picUrl) throws Throwable {
		return getPicRootPath() + convertUrlToFileName(picUrl);
	}

	/**
	 * 检查图片目录是否存在 如不存在则创建目录
	 */
	private static boolean checkPicPath(String picPath) {
		File file = new File(picPath);
		if (!file.exists())
			file.mkdirs();
		return file.exists();
	}

	/**
	 * 检查图片是否存在
	 */
	private static boolean checkPicExists(String picPath) {
		File file = new File(picPath);
		return file.exists();
	}

	/**
	 * 修改文件的最后修改时间
	 */
	private static void updateFileTime(String filePath) {
		File file = new File(filePath);
		long newModifiedTime = System.currentTimeMillis();
		file.setLastModified(newModifiedTime);
	}

	/**
	 * 获得文件个数
	 */
	private static long fileCount(File f) {
		if (f.exists() && f.isDirectory()) {
			return f.list().length;
		}
		return 0;
	}

	/**
	 * 清空文件夹里的文件
	 */
	private static void cleanFolder(File f) {
		if (f.exists() && f.isDirectory()) {
			File[] files = f.listFiles();
			for (File file : files)
				file.delete();
		}
	}

	/**
	 * 关闭输出流
	 */
	public static void closeOutputStream(OutputStream os) {
		if (os == null)
			return;
		try {
			os.close();
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
}