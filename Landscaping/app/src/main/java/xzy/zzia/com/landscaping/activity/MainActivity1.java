package xzy.zzia.com.landscaping.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import xzy.zzia.com.landscaping.R;
import xzy.zzia.com.landscaping.fragment.HomeFragment;
import xzy.zzia.com.landscaping.fragment.MessageFragment;
import xzy.zzia.com.landscaping.fragment.SceneFragment;
import xzy.zzia.com.landscaping.fragment.TechnicalFragment;
import xzy.zzia.com.landscaping.view.ChangeColorIconWithText;
import xzy.zzia.com.landscaping.view.MoreWindow;

public class MainActivity1 extends FragmentActivity implements View.OnClickListener {


    private ImageView add;
    private MoreWindow moreWindow;
    private ChangeColorIconWithText home, scene, message, technical;
    private ViewPager viewPager;
    private List<Fragment> mTabs = new ArrayList<>();
    private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        initView();
        initEvent();
    }

    private void initEvent() {

        mTabs.add(new HomeFragment());
        mTabs.add(new SceneFragment());
        mTabs.add(new MessageFragment());
        mTabs.add(new TechnicalFragment());

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }
        };

        viewPager.setAdapter(mAdapter);
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.layout_activity_viewpager);

        home = (ChangeColorIconWithText) findViewById(R.id.home);
        scene = (ChangeColorIconWithText) findViewById(R.id.scene);
        message = (ChangeColorIconWithText) findViewById(R.id.message);
        technical = (ChangeColorIconWithText) findViewById(R.id.technical);
        add = (ImageView) findViewById(R.id.image_add);

        mTabIndicators.add(home);
        mTabIndicators.add(scene);
        mTabIndicators.add(message);
        mTabIndicators.add(technical);


        home.setOnClickListener(this);
        scene.setOnClickListener(this);
        message.setOnClickListener(this);
        technical.setOnClickListener(this);
        add.setOnClickListener(this);

        home.setIconAlpha(1.0f);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_add:
                if (null == moreWindow) {
                    moreWindow = new MoreWindow(this);
                    moreWindow.init();
                }
                moreWindow.showMoreWindow(view);
                break;
            case R.id.home:
                resetOtherTabs();
                mTabIndicators.get(0).setIconAlpha(1.0f);
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.scene:
                resetOtherTabs();
                mTabIndicators.get(1).setIconAlpha(1.0f);
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.message:
                resetOtherTabs();
                mTabIndicators.get(2).setIconAlpha(1.0f);
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.technical:
                resetOtherTabs();
                mTabIndicators.get(3).setIconAlpha(1.0f);
                viewPager.setCurrentItem(3, false);
                break;
        }

    }

    /**
     * 重置其它的TabIndicator的颜色
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicators.size(); i++) {
            mTabIndicators.get(i).setIconAlpha(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != moreWindow) {
            moreWindow.destroy();
        }
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


