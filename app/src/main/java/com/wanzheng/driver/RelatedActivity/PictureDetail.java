package com.wanzheng.driver.RelatedActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.haha.exam.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 放大显示图片的界面
 * Created by Administrator on 2017/1/11.
 */
public class PictureDetail extends Activity {

    private String url;
    private ImageLoader imageLoader;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.picture_detail);
        url = getIntent().getStringExtra("bitmap");
        imageView = (ImageView) findViewById(R.id.picture);
        imageLoader.getInstance().displayImage(url, imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
