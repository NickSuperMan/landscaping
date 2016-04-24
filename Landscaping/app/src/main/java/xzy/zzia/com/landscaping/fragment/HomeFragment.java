package xzy.zzia.com.landscaping.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.flyco.banner.anim.select.ZoomInEnter;
import com.flyco.banner.transform.ZoomOutSlideTransformer;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import xzy.zzia.com.landscaping.R;
import xzy.zzia.com.landscaping.activity.ApplyLeaveActivity;
import xzy.zzia.com.landscaping.activity.LoginActivity;
import xzy.zzia.com.landscaping.activity.NoteActivity;
import xzy.zzia.com.landscaping.activity.PersonalActivity;
import xzy.zzia.com.landscaping.app.App;
import xzy.zzia.com.landscaping.banner.SimpleImageBanner;
import xzy.zzia.com.landscaping.testData.HomeDatas;
import xzy.zzia.com.landscaping.utils.DataProvider;
import xzy.zzia.com.landscaping.utils.TitleBuilder;
import xzy.zzia.com.landscaping.utils.ToastUtils;
import xzy.zzia.com.landscaping.utils.ViewFindUtils;
import xzy.zzia.com.landscaping.view.RoundProgressBar;

/**
 * Created by roy on 16/4/18.
 */
public class HomeFragment extends BaseFragment {

    private View view;
    private SimpleImageBanner sib;
    private RoundProgressBar progressBar;
    private TextView current_people, max_people;
    private TextView project_start_time, project_cancel_time;
    private GridView gridView_home;
    private Drawer result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.layout_home, null);

        new TitleBuilder(view).setLeftImage(R.drawable.me).setTitleText("首页").setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(getActivity(), "click");
                result.openDrawer();
            }
        }).build();
        initSlideMenu();
        initView();
        initEvent();

        return view;
    }

    private void initSlideMenu() {

        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(getActivity())
                .withHeaderBackground(R.drawable.fab_label_background)

                .addProfiles(new ProfileDrawerItem().withIcon(R.drawable.icon_me).withName("roy").withEmail("15839665365"))
                .withSelectionListEnabled(false)
//                .withSelectionSecondLineShown(false)
//                .withSelectionFirstLineShown(false)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        ToastUtils.showShort(getActivity(), "click....");
                        if (App.isLogin()) {
                            intent2Activity(PersonalActivity.class);
                        } else {
                            intent2Activity(LoginActivity.class);
                        }
                        return false;
                    }
                })
                .build();

        SecondaryDrawerItem item1 = new SecondaryDrawerItem()
                .withIcon(R.mipmap.ic_launcher)
                .withName(R.string.leave);

        SecondaryDrawerItem item2 = new SecondaryDrawerItem()
                .withIcon(R.drawable.icon_home)
                .withName(R.string.note);

        result = new DrawerBuilder()
                .withActivity(getActivity())
                .withAccountHeader(header)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new DividerDrawerItem()
                )
                .addStickyDrawerItems(new PrimaryDrawerItem()
                        .withIcon(R.drawable.icon_message)
                        .withName(R.string.cancellation)
                        .withTextColor(Color.RED)
                        .withSelectedTextColor(Color.RED))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        ToastUtils.showShort(getActivity(), "position------>" + position);
                        switch (position) {
                            case -1:

                                break;
                            case 1:
                                intent2Activity(ApplyLeaveActivity.class);
                                break;
                            case 3:
                                intent2Activity(NoteActivity.class);
                                break;
                        }
                        return false;
                    }
                })
                .withSelectedItem(-1)
                .build();


        if (!App.isLogin()) {

            result.removeAllStickyFooterItems();
        }

    }

    private void initView() {
        sib = ViewFindUtils.find(view, R.id.looper);
        progressBar = ViewFindUtils.find(view, R.id.project_circle);
        current_people = ViewFindUtils.find(view, R.id.current_people_text);
        max_people = ViewFindUtils.find(view, R.id.max_people_text);
        project_start_time = ViewFindUtils.find(view, R.id.project_start_time);
        project_cancel_time = ViewFindUtils.find(view, R.id.project_cancel_time);
        gridView_home = ViewFindUtils.find(view, R.id.gridView_home);
    }

    private void initEvent() {

        progressBar.setProgress(80);
        progressBar.setCricleProgressColor(Color.RED);
        progressBar.setText("300");

        sib.setSelectAnimClass(ZoomInEnter.class)
                .setSource(DataProvider.getList())
                .setTransformerClass(ZoomOutSlideTransformer.class)
                .startScroll();

        sib.setOnItemClickL(new BaseBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.showShort(getActivity(), "position--->" + position);
            }
        });

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), HomeDatas.getDatas(), R.layout.layout_gridview_home, new String[]{"imgId", "nameId"}, new int[]{R.id.gridView_img_item, R.id.gridView_text_item});
        gridView_home.setAdapter(adapter);
    }

}
