package com.wanzheng.driver.network;


import java.lang.ref.SoftReference;
import java.util.HashSet;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapCache implements ImageCache {

	private static int DEFAULT_MEM_CACHE_SIZE = 1024 * 10; // 5MB
	private LruCache<String, Bitmap> mCache;

	public static HashSet<SoftReference<Bitmap>> mReusableBitmaps = new HashSet<SoftReference<Bitmap>>();

	public BitmapCache() {
		mCache = new LruCache<String, Bitmap>(DEFAULT_MEM_CACHE_SIZE) {
			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				mReusableBitmaps.add(new SoftReference<Bitmap>(oldValue));
			}

			@Override
			protected int sizeOf(String key, Bitmap value) {
				final int bitmapSize = getBitmapSize(value) / 1024;
				return bitmapSize == 0 ? 1 : bitmapSize;
			}
		};
	}

	public static int getBitmapSize(Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}

	@Override
	public Bitmap getBitmap(String url) {
		return mCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		mCache.put(url, bitmap);
	}

}
