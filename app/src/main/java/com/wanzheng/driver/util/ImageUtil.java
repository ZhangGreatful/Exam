package com.wanzheng.driver.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.util.Base64;
import android.util.Log;

/**
 * 图片工具类，对图片进行一些处理
 * 
 * @author renyangyang
 * 
 */
public class ImageUtil {

	/**
	 * 图片旋转
	 * 
	 * @param bmp
	 * @param degree
	 * @return
	 */
	public static Bitmap postRotateBitamp(Bitmap bmp, float degree) {
		// 获得Bitmap的高和宽
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		// 产生resize后的Bitmap对象
		Matrix matrix = new Matrix();
		matrix.setRotate(degree);
		Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight,
				matrix, true);
		return resizeBmp;
	}

	/**
	 * 图片放大缩小
	 * 
	 * @param bmp
	 * @param degree
	 * @return
	 */
	public static Bitmap postScaleBitamp(Bitmap bmp, float sx, float sy) {
		// 获得Bitmap的高和宽
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		int h = (int) (bmp.getHeight() * (sx / bmpWidth));
		// System.out.println("before+w+h:::::::::::"+bmpWidth+","+bmpHeight);
		// 产生resize后的Bitmap对象
		Matrix matrix = new Matrix();
		matrix.setScale(sx, sy);
		Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight,
				matrix, true);
		// System.out.println("after+w+h:::::::::::"+resizeBmp.getWidth()+","+resizeBmp.getHeight());
		return resizeBmp;
	}
	/** 
	    * 质量压缩方法 
	    * 
	    * @param image 
	    * @return 
	    */  
	   public static Bitmap compressImage(Bitmap image) {  
	   
	       ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	       image.compress(CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
	       int options = 100;  
	       while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩  
	           baos.reset();//重置baos即清空baos  
	           //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流  
	           image.compress(CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
	           options -= 10;//每次都减少10  
	       }  
	       ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
	       Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
	       return bitmap;  
	   }  
	/**
	 * 图片 亮度调整
	 * 
	 * @param bmp
	 *            huevalue亮度调整黑白
	 * @param sx
	 * @param sy
	 * @return
	 */
	public static Bitmap postColorRotateBitamp(int hueValue, int lumValue,
			Bitmap bm) {
		// 获得Bitmap的高和宽
		// System.out.println(bm.getWidth()+","+bm.getHeight()+"------before");
		Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
				Bitmap.Config.ARGB_8888);
		// 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
		Canvas canvas = new Canvas(bmp); // 得到画笔对象
		Paint paint = new Paint(); // 新建paint
		paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理

		// 产生resize后的Bitmap对象
		ColorMatrix mAllMatrix = new ColorMatrix();
		ColorMatrix mLightnessMatrix = new ColorMatrix();
		ColorMatrix mSaturationMatrix = new ColorMatrix();
		ColorMatrix mHueMatrix = new ColorMatrix();

		float mHueValue = hueValue * 1.0F / 127; // 亮度
		mHueMatrix.reset();
		mHueMatrix.setScale(mHueValue, mHueValue, mHueValue, 1); // 红、绿、蓝三分量按相同的比例,最后一个参数1表示透明度不做变化，此函数详细说明参考

		float mSaturationValue = 127 * 1.0F / 127;// 饱和度
		mSaturationMatrix.reset();
		mSaturationMatrix.setSaturation(mSaturationValue);

		float mLumValue = (lumValue - 127) * 1.0F / 127 * 180; // 色相
		mLightnessMatrix.reset(); // 设为默认值
		mLightnessMatrix.setRotate(0, mLumValue); // 控制让红色区在色轮上旋转的角度
		mLightnessMatrix.setRotate(1, mLumValue); // 控制让绿红色区在色轮上旋转的角度
		mLightnessMatrix.setRotate(2, mLumValue); // 控制让蓝色区在色轮上旋转的角度

		mAllMatrix.reset();
		mAllMatrix.postConcat(mHueMatrix);
		mAllMatrix.postConcat(mSaturationMatrix); // 效果叠加
		mAllMatrix.postConcat(mLightnessMatrix); // 效果叠加

		paint.setColorFilter(new ColorMatrixColorFilter(mAllMatrix));// 设置颜色变换效果
		canvas.drawBitmap(bm, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
		// System.out.println(bmp.getWidth()+","+bmp.getHeight()+"------after");
		return bmp;
	}

	/**
	 * 读取资源图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 图片锐化（拉普拉斯变换）
	 * 
	 * @param bmp
	 * @return
	 */
	public static Bitmap sharpenImageAmeliorate(Bitmap bmp) {

		long start = System.currentTimeMillis();
		// 拉普拉斯矩阵
		int[] laplacian = new int[] { -1, -1, -1, -1, 9, -1, -1, -1, -1 };

		int width = bmp.getWidth();
		int height = bmp.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);

		int pixR = 0;
		int pixG = 0;
		int pixB = 0;

		int pixColor = 0;

		int newR = 0;
		int newG = 0;
		int newB = 0;

		int idx = 0;
		float alpha = 0.3F;
		int[] pixels = new int[width * height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 1, length = height - 1; i < length; i++) {
			for (int k = 1, len = width - 1; k < len; k++) {
				idx = 0;
				for (int m = -1; m <= 1; m++) {
					for (int n = -1; n <= 1; n++) {
						pixColor = pixels[(i + n) * width + k + m];
						pixR = Color.red(pixColor);
						pixG = Color.green(pixColor);
						pixB = Color.blue(pixColor);

						newR = newR + (int) (pixR * laplacian[idx] * alpha);
						newG = newG + (int) (pixG * laplacian[idx] * alpha);
						newB = newB + (int) (pixB * laplacian[idx] * alpha);
						idx++;
					}
				}

				newR = Math.min(255, Math.max(0, newR));
				newG = Math.min(255, Math.max(0, newG));
				newB = Math.min(255, Math.max(0, newB));

				pixels[i * width + k] = Color.argb(255, newR, newG, newB);
				newR = 0;
				newG = 0;
				newB = 0;
			}
		}

		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		long end = System.currentTimeMillis();
		Log.e("sharpenImageAmeliorate", "used time=" + (end - start));

		return bitmap;
	}

	// 黑白效果函数
	public static Bitmap changeToGray(Bitmap bitmap) {
		int width, height;
		width = bitmap.getWidth();
		height = bitmap.getHeight();

		Bitmap grayBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(grayBitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true); // 设置抗锯齿

		ColorMatrix colorMatrix = new ColorMatrix();
		colorMatrix.setSaturation(0);

		ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);

		paint.setColorFilter(filter);
		canvas.drawBitmap(bitmap, 0, 0, paint);

		return grayBitmap;
	}

	// 油画效果函数
	public static Bitmap changeToOil(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Bitmap returnBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);

		int color = 0;
		int radio = 0;

		Random random = new Random();
		int iModel = 4;
		int i = width - iModel;

		while (i > 1) {
			int j = height - iModel;
			while (j > 1) {
				int iPos = random.nextInt(100000000) % iModel;
				color = bitmap.getPixel(i + iPos, j + iPos);
				returnBitmap.setPixel(i, j, color);
				j--;
			}
			i--;
		}

		return returnBitmap;
	}

	// 怀旧效果函数
	public static Bitmap changeToOld(Bitmap bitmap) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap returnBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		int pixColor = 0;
		int pixR = 0;
		int pixG = 0;
		int pixB = 0;
		int newR = 0;
		int newG = 0;
		int newB = 0;
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 0; i < height; i++) {
			for (int k = 0; k < width; k++) {
				pixColor = pixels[width * i + k];
				pixR = Color.red(pixColor);
				pixG = Color.green(pixColor);
				pixB = Color.blue(pixColor);
				newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);
				newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);
				newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);
				int newColor = Color.argb(255, newR > 255 ? 255 : newR,
						newG > 255 ? 255 : newG, newB > 255 ? 255 : newB);
				pixels[width * i + k] = newColor;
			}
		}

		returnBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return returnBitmap;
	}

	/**
	 * 对图片进行处理 1，首先判断 图片的宽和高 2，如果宽和高都小于700，就放大到手机的宽度（要判断是否大于700）
	 * 3，如果有一项大于700，就进行缩放，都小于700为止
	 */
	public static Bitmap parseBitmap(Bitmap mBitmap, String path) {
		// 1
		int imgWidth = mBitmap.getWidth();
		int imgHeight = mBitmap.getHeight();
		// 2
		if (imgWidth > 700 || imgHeight > 700) {
			float sx = imgWidth > imgHeight ? ((float) 700) / (float) imgWidth
					: ((float) 700) / (float) imgHeight;

			mBitmap = postScaleBitamp(mBitmap, sx, sx);
		} else {
			/*
			 * if(screenWidth<700){ float sx = imgWidth > imgHeight ?
			 * ((float)screenWidth)/(float)imgWidth
			 * :((float)screenWidth)/(float)imgHeight; mBitmap =
			 * postScaleBitamp(mBitmap, sx, sx); }else{ float sx = imgWidth >
			 * imgHeight ? ((float)700)/(float)imgWidth
			 * :((float)700)/(float)imgHeight; mBitmap =
			 * postScaleBitamp(mBitmap, sx, sx); }
			 */
			int value = imgWidth > imgHeight ? imgWidth : imgHeight;
			if (value < 100) {
				mBitmap = ImageUtils.getBitmapByPath(path);
			} else {
				return mBitmap;
			}
		}
		return mBitmap;
	}

	public static Bitmap parseBitmap(String path, int mWidth) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(path, options);
		
		if (mWidth >= 700) {
			mWidth = 700;
		}
		int max = options.outWidth;
		int max2 = options.outHeight;
			int degree = getBitmapDegree(path);  
			//bmp =rotateBitmapByDegree(compressImage(ImageUtils.getBitmapByPath(path)), degree);
			if (max > mWidth) {
				options.inSampleSize = max / mWidth;
				int height = options.outHeight * mWidth / max;
				int width = options.outWidth * mWidth / max;
				options.outWidth = width;
				options.outHeight = height;

			} else {
				options.inSampleSize = 1;
				int a = options.outWidth;
				int b = options.outHeight;
				options.outWidth = options.outWidth;
				options.outHeight = options.outHeight;
			}
			/* 这样才能真正的返回一个Bitmap给你 */
			options.inJustDecodeBounds = false;
			//bmp = ImageUtils.getBitmapByPath(path, options);
			bmp = rotateBitmapByDegree( ImageUtils.getBitmapByPath(path, options),degree);
		return bmp;
	}

	public static String Bitmap2StrByBase64(Bitmap bit) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bit.compress(CompressFormat.JPEG, 40, bos);// 参数100表示不压缩
		byte[] bytes = bos.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	
	/** 
     * 读取图片的旋转的角度 
     * 
     * @param path 图片绝对路径 
     * @return 图片的旋转角度 
     */  
    public static int getBitmapDegree(String path) {  
        int degree = 0;  
        try {  
            // 从指定路径下读取图片，并获取其EXIF信息  
            ExifInterface exifInterface = new ExifInterface(path);  
            // 获取图片的旋转信息  
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,  
                    ExifInterface.ORIENTATION_NORMAL);  
            switch (orientation) {  
                case ExifInterface.ORIENTATION_ROTATE_90:  
                    degree = 90;  
                    break;  
                case ExifInterface.ORIENTATION_ROTATE_180:  
                    degree = 180;  
                    break;  
                case ExifInterface.ORIENTATION_ROTATE_270:  
                    degree = 270;  
                    break;  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return degree;  
    }  
    /** 
     * 将图片按照某个角度进行旋转 
     * 
     * @param bm     需要旋转的图片 
     * @param degree 旋转角度 
     * @return 旋转后的图片 
     */  
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {  
        Bitmap returnBm = null;  
   
        // 根据旋转角度，生成旋转矩阵  
        Matrix matrix = new Matrix();  
        matrix.postRotate(degree);  
        try {  
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片  
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);  
        } catch (OutOfMemoryError e) {  
        }  
        if (returnBm == null) {  
            returnBm = bm;  
        }  
        if (bm != returnBm) {  
            bm.recycle();  
        }  
        return returnBm;  
    }  
}
