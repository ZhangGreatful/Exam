package com.haha.exam.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;
import com.haha.exam.R;

public class MyImgeview extends NetworkImageView {

	private Paint paint;
	private Bitmap mBitmap;
	private Bitmap output;
	private Context mContext;

	public MyImgeview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		paint = new Paint();
		initDefaultImage();
		// mBitmap = this.getBackgroundBitmap();
	}

	public MyImgeview(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		paint = new Paint();
		initDefaultImage();
		// mBitmap = this.getBackgroundBitmap();
	}

	public MyImgeview(Context context) {
		super(context);
		mContext = context;
		paint = new Paint();
		initDefaultImage();
		// mBitmap = this.getBackgroundBitmap();
	}

	private void initDefaultImage() {
		setDefaultImageResId(R.drawable.ic_launcher);
		setErrorImageResId(R.drawable.ic_launcher);
	}

	public void initAvatarDefaultImage() {
		// setDefaultImageResId(R.drawable.person_image_default);
		// setErrorImageResId(R.drawable.person_image_default);
	}

	public void setDefaultImage(int res) {
		setDefaultImageResId(res);
		setErrorImageResId(res);
	}

	public void setSmallImage() {
		// setDefaultImageResId(R.drawable.default_background_small);
		// setErrorImageResId(R.drawable.default_background_small);
	}

	public void setColorDefault() {
		// setDefaultImageResId(R.color.border_color);
		// setErrorImageResId(R.color.border_color);
	}

	public Bitmap getBackgroundBitmap() {
		Drawable drawable = getDrawable();
		if (null != drawable) {
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			Bitmap b = getRoundBitmap(bitmap, 10);
			// final Rect rectSrc = new Rect(0, 0, mBitmap =
			// this.getBackgroundBitmap();.getWidth(), b.getHeight());
			// final Rect rectDest = new Rect(0, 0, getWidth(), getHeight());
			return b;
		}
		return null;
	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		if (mBitmap != null && output != null) {
			mBitmap.recycle();
			mBitmap = null;
			output.recycle();
			output = null;
		}

	}

	/**
	 * 绘制圆角矩形图片
	 * 
	 * @author caizhiming
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// Toast.makeText(mContext, "1", Toast.LENGTH_SHORT).show();
		Drawable drawable = getDrawable();
		if (null != drawable) {
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			mBitmap = getRoundBitmap(bitmap, 10);
			if (mBitmap != null) {
				final Rect rectSrc = new Rect(0, 0, mBitmap.getWidth(),
						mBitmap.getHeight());
				final Rect rectDest = new Rect(0, 0, getWidth(), getHeight());
				paint.reset();
				canvas.drawBitmap(mBitmap, rectSrc, rectDest, paint);
			} else {
				super.onDraw(canvas);
			}

		} else {
			super.onDraw(canvas);
		}
	}

	/**
	 * 获取圆角矩形图片方法
	 * 
	 * @param bitmap
	 * @param roundPx
	 *            ,一般设置成14
	 * @return Bitmap
	 * @author caizhiming
	 */
	private Bitmap getRoundBitmap(Bitmap bitmap, int roundPx) {
		try {
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
					Bitmap.Config.ARGB_8888);
		} catch (OutOfMemoryError e) {
			// TODO Auto-generated catch block
			return null;
		}
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;

		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		int x = bitmap.getWidth();
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;

	}

}
