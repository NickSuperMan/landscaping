package com.zua.landscaping.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zua.landscaping.R;
import com.zua.landscaping.app.Constant;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by roy on 16/5/6.
 */
public class PhotoPreviewActivity extends Activity {

    private String url;
    private PhotoView imageView;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_photo_preview);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        initView();

    }

    private void initView() {

        imageView = (PhotoView) findViewById(R.id.photo_check);

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        imageLoader.displayImage(Constant.BasePath + url, imageView, options);

        mAttacher = new PhotoViewAttacher(imageView);


    }
}
