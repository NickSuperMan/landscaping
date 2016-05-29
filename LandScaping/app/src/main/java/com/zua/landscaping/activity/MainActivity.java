package com.zua.landscaping.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.app.Constant;
import com.zua.landscaping.bean.Event;
import com.zua.landscaping.bean.User;
import com.zua.landscaping.utils.DataLoad;
import com.zua.landscaping.utils.FragmentController;
import com.zua.landscaping.view.MoreWindow;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.SystemConversationProvider;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, RongIM.UserInfoProvider {

    public static final String ACTION_DMEO_RECEIVE_MESSAGE = "action_demo_receive_message";
    public static final String ACTION_DMEO_AGREE_REQUEST = "action_demo_agree_request";
    private RadioGroup radioGroup;
    private ImageView add;
    private FragmentController controller;
    private MoreWindow moreWindow;
    private List<User> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RongIM.setUserInfoProvider(this, true);
        setContentView(R.layout.activity_main);

        App.getInstance().addActivity(this);

        data = App.getUser().getFriends();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        controller = FragmentController.getInstance(this, R.id.fl_content);
        controller.showFragments(0);

        initView();

    }

    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.group);

        add = (ImageView) findViewById(R.id.image_add);


        radioGroup.setOnCheckedChangeListener(this);
        add.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        switch (i) {
            case R.id.home:
                controller.showFragments(0);
                break;
            case R.id.scene:
                controller.showFragments(1);
                break;
            case R.id.message:
                controller.showFragments(2);
                break;
            case R.id.technical:
                controller.showFragments(3);
                break;
        }
    }


    @Override
    public void onClick(View view) {

        if (null == moreWindow) {
            moreWindow = new MoreWindow(this);
            moreWindow.init();
        }
        moreWindow.showMoreWindow(view);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        deleteDirectory();
        FragmentController.onDestroy();
        if (null != moreWindow) {
            moreWindow.destroy();
        }

        ImageLoader.getInstance().clearMemoryCache();
    }

    @Override
    public void onBackPressed() {

        final MaterialDialog dialog = new MaterialDialog(this);
        dialog.isTitleShow(false)
                .content("是否确定退出当前App?")
                .contentTextSize(18)
                .btnText("取消", "确定")
                .showAnim(new BounceTopEnter())
                .dismissAnim(new SlideBottomExit())
                .show();

        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.superDismiss();
                App.getInstance().exit();
            }
        });
    }


    @Override
    public UserInfo getUserInfo(String s) {

        Log.e("roy", "enter rongUserInfo");

        for (User user : data) {
            if ((user.getUserId() + "").equals(s)) {
                Log.e("roy", "~~~~" + user.getUserPicUrl());
                return new UserInfo(user.getUserId() + "", user.getUserName(), Uri.parse(Constant.BasePath + user.getUserPicUrl()));
            }
        }

        return null;
    }


    private void deleteDirectory() {
        File imageFileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "LandScaping" + File.separator + "download");
        if (imageFileDir.exists() && imageFileDir.isDirectory()) {
            File[] files = imageFileDir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    File imageFile = new File(files[i].getAbsolutePath());
                    if (imageFile.isFile() && imageFile.exists()) {
                        imageFile.delete();
                    }
                }
            }
            imageFileDir.delete();
        }
    }


}


