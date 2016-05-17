package com.zua.landscaping.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zua.landscaping.R;
import com.zua.landscaping.app.Constant;
import com.zua.landscaping.view.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by roy on 16/5/17.
 */
public class PhotoViewPagerPreview extends Activity {

    private String path;
    private HackyViewPager mViewPager;

    private String[] paths;
    private List<String> url;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout_photo_viewpager);
        path = getIntent().getStringExtra("url");

        paths = path.split("jpg");
        url = new ArrayList<>();

        for (int i = 0; i < paths.length; i++) {

            String s = paths[i].substring(0, paths[i].lastIndexOf("_")) + ".jpg";

            url.add(s);

        }

        for (int i = 0; i < url.size(); i++) {
            Log.e("roy", url.get(i) + "");
        }
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);


        mViewPager.setAdapter(new SamplePagerAdapter());
    }

    private class SamplePagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return url.size();
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            final PhotoView photoView = new PhotoView(container.getContext());

            imageLoader.loadImage(Constant.BasePath + url.get(position), options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    if (dialog == null) {
                        dialog = ProgressDialog.show(PhotoViewPagerPreview.this, null, getString(R.string.loading));
                    }
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                        dialog = null;
                    }
                    photoView.setImageBitmap(loadedImage);

                    container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
