package com.zua.landscaping.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.zua.landscaping.R;
import com.zua.landscaping.activity.DeviceListActivity;
import com.zua.landscaping.activity.ProcessUpdateActivity;
import com.zua.landscaping.activity.RecordVideoActivity;
import com.zua.landscaping.activity.SafeUploadActivity;
import com.zua.landscaping.activity.TakePhotoActivity;
import com.zua.landscaping.activity.TakePhotoActivity2;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Code;
import com.zua.landscaping.bean.Event;
import com.zua.landscaping.bean.Project;
import com.zua.landscaping.bean.Sign;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.ServiceGenerator;
import com.zua.landscaping.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoreWindow extends PopupWindow implements OnClickListener {

    private String TAG = MoreWindow.class.getSimpleName();
    Activity mContext;
    private int mWidth;
    private int mHeight;
    private int statusBarHeight;
    private Bitmap mBitmap = null;
    private Bitmap overlay = null;
    private RelativeLayout layout;
    private RelativeLayout relative_layout;


    private Handler mHandler = new Handler();

    public MoreWindow(Activity context) {
        mContext = context;
    }

    public void init() {
        Rect frame = new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;

        setWidth(mWidth);
        setHeight(mHeight);
    }

    private Bitmap blur() {
        if (null != overlay) {
            return overlay;
        }
        long startMs = System.currentTimeMillis();

        View view = mContext.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        mBitmap = view.getDrawingCache();

        float scaleFactor = 8;
        float radius = 10;
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        overlay = Bitmap.createBitmap((int) (width / scaleFactor), (int) (height / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(mBitmap, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        Log.i(TAG, "blur time is:" + (System.currentTimeMillis() - startMs));
        return overlay;
    }

    private Animation showAnimation1(final View view, int fromY, int toY) {
        AnimationSet set = new AnimationSet(true);
        TranslateAnimation go = new TranslateAnimation(0, 0, fromY, toY);
        go.setDuration(300);
        TranslateAnimation go1 = new TranslateAnimation(0, 0, -10, 2);
        go1.setDuration(100);
        go1.setStartOffset(250);
        set.addAnimation(go1);
        set.addAnimation(go);

        set.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }

        });
        return set;
    }


    public void showMoreWindow(View anchor) {
        layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.center_music_more_window, null);
        setContentView(layout);

        relative_layout = (RelativeLayout) layout.findViewById(R.id.relative_layout);
        ImageView close = (ImageView) layout.findViewById(R.id.center_music_window_close);

        close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    closeAnimation(relative_layout);
                }
            }

        });

        showAnimation(relative_layout);
        setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), blur()));
        setOutsideTouchable(true);
        setFocusable(true);
        showAtLocation(anchor, Gravity.BOTTOM, 0, statusBarHeight);
    }

    private void showAnimation(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);

            child.setOnClickListener(this);
            child.setVisibility(View.INVISIBLE);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                    fadeAnim.setDuration(300);
                    KickBackAnimator kickAnimator = new KickBackAnimator();
                    kickAnimator.setDuration(150);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                }
            }, i * 50);
        }

    }

    private void closeAnimation(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
            child.setOnClickListener(this);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
                    fadeAnim.setDuration(200);
                    KickBackAnimator kickAnimator = new KickBackAnimator();
                    kickAnimator.setDuration(100);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                    fadeAnim.addListener(new AnimatorListener() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            child.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
            }, (layout.getChildCount() - i - 1) * 30);

            if (child.getId() == R.id.take_device) {
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        dismiss();
                    }
                }, (layout.getChildCount() - i) * 30 + 80);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_photo:

                mContext.startActivity(new Intent(mContext, TakePhotoActivity2.class));
                clearPopup();
                break;
            case R.id.upload_video:

                mContext.startActivity(new Intent(mContext, RecordVideoActivity.class));
                clearPopup();

                break;
            case R.id.take_sign:
                signUpload();
                clearPopup();
                break;
            case R.id.upload_process:
                mContext.startActivity(new Intent(mContext, ProcessUpdateActivity.class));
                clearPopup();
                break;
            case R.id.upload_danger:
                mContext.startActivity(new Intent(mContext, SafeUploadActivity.class));
                clearPopup();
                break;
            case R.id.take_device:
                mContext.startActivity(new Intent(mContext, DeviceListActivity.class));
                clearPopup();
                break;

            default:
                break;
        }
    }

    private void clearPopup() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        closeAnimation(relative_layout);
    }

    private void signUpload() {

        ConnService service = ServiceGenerator.createService(ConnService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", App.getUser().getUserId() + "");
        map.put("signPosition", App.getPosition());
        map.put("method", "sign");

        Call<Code> call = service.userSign(map);
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.isSuccess()) {
                    Code body = response.body();

                    if (body.getCode() == 1) {
                        ToastUtils.showShort(mContext, body.getMessage());
                        EventBus.getDefault().post(new Event("sign"));
                    } else {
                        ToastUtils.showShort(mContext, body.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Code> call, Throwable t) {
                Log.e("roy", t.toString());
            }
        });

    }

    public void destroy() {

        if (null != overlay) {
            overlay.recycle();
            overlay = null;
            System.gc();
        }
        if (null != mBitmap) {
            mBitmap.recycle();
            mBitmap = null;
            System.gc();
        }
        Log.e("roy", "MoreWindow------->destroy");
    }
}
