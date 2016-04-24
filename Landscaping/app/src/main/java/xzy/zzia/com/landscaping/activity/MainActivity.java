package xzy.zzia.com.landscaping.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;

import com.flyco.dialog.widget.MaterialDialog;
import com.jaeger.library.StatusBarUtil;

import xzy.zzia.com.landscaping.R;
import xzy.zzia.com.landscaping.service.LocationService;
import xzy.zzia.com.landscaping.utils.FragmentController;
import xzy.zzia.com.landscaping.view.MoreWindow;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private RadioGroup radioGroup;
    private ImageView add;
    private FragmentController controller;
    private MoreWindow moreWindow;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = new Intent(getApplicationContext(), LocationService.class);
        startService(intent);

        setContentView(R.layout.activity_main);
        StatusBarUtil.setColor(MainActivity.this, Color.RED);
        StatusBarUtil.setTranslucent(MainActivity.this, 200);
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
        Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
        if (null == moreWindow) {
            moreWindow = new MoreWindow(this);
            moreWindow.init();
        }
        moreWindow.showMoreWindow(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentController.onDestroy();
        if (null != moreWindow) {
            moreWindow.destroy();
        }
        stopService(intent);
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
                finish();
                System.exit(0);
            }
        });
    }
}


