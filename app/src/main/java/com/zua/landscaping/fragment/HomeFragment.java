package com.zua.landscaping.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.zua.landscaping.R;
import com.zua.landscaping.activity.LeaveStatusActivity;
import com.zua.landscaping.activity.LoginActivity;
import com.zua.landscaping.activity.LoginActivity1;
import com.zua.landscaping.activity.MeetingActivity;
import com.zua.landscaping.activity.NoteActivity;
import com.zua.landscaping.activity.OpinionActivity;
import com.zua.landscaping.activity.PersonalActivity;
import com.zua.landscaping.activity.ProjectActivity;
import com.zua.landscaping.activity.ProjectDrawing;
import com.zua.landscaping.activity.WeatherActivity;
import com.zua.landscaping.activity.WebActivity;
import com.zua.landscaping.app.App;
import com.zua.landscaping.app.Constant;
import com.zua.landscaping.banner.SimpleImageBanner;
import com.zua.landscaping.bean.Event;
import com.zua.landscaping.bean.Sign;
import com.zua.landscaping.testData.HomeDatas;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.DataProvider;
import com.zua.landscaping.utils.ServiceGenerator;
import com.zua.landscaping.utils.TitleBuilder;
import com.zua.landscaping.utils.ToastUtils;
import com.zua.landscaping.utils.ViewFindUtils;
import com.zua.landscaping.view.RoundProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by roy on 16/4/18.
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private View view;
    private SimpleImageBanner sib;
    private RoundProgressBar progressBar;
    private TextView current_people, max_people;
    private TextView project_start_time, project_cancel_time;
    private GridView gridView_home;
    private Drawer result;
    private Sign sign;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.layout_home, null);

        new TitleBuilder(view).setLeftImage(R.drawable.me).setTitleText("首页").setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                result.openDrawer();
            }
        }).setRightImage(R.drawable.icon_search).setRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (App.getCity() != null) {

                    intent2Activity(WeatherActivity.class);
                }
            }
        }).build();


        initView();

        initEvent();
        initSlideMenu();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sign != null) {
            getPeople();
            initSlideMenu();
        }
    }

    private void getPeople() {
        sign = new Sign();
        final ConnService service = ServiceGenerator.createService(ConnService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("method", "people");
        Call<Sign> call = service.getPeople(map);
        call.enqueue(new Callback<Sign>() {
            @Override
            public void onResponse(Call<Sign> call, Response<Sign> response) {
                if (response.isSuccess()) {
                    sign = response.body();
                    current_people.setText(sign.getCurrentPeople() + "");
                    max_people.setText(sign.getTotalPeople() + "");
                }
            }

            @Override
            public void onFailure(Call<Sign> call, Throwable t) {
                Log.e("roy", t.toString());
            }
        });
    }


    private void initSlideMenu() {


        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(getActivity())
                .withHeaderBackground(R.drawable.fab_label_background)

                .addProfiles(new ProfileDrawerItem().withIcon(App.getIcon()).withName(App.getUser().getUserSign()).withEmail(App.getUser().getUserTel()))
                .withSelectionListEnabled(false)
//                .withSelectionSecondLineShown(false)
//                .withSelectionFirstLineShown(false)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {

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
        SecondaryDrawerItem item3 = new SecondaryDrawerItem()
                .withIcon(R.drawable.icon_home)
                .withName(R.string.opinion);

        result = new DrawerBuilder()
                .withActivity(getActivity())
                .withAccountHeader(header)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new DividerDrawerItem(),
                        item3,
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
//                        ToastUtils.showShort(getActivity(),position+"");
                        switch (position) {
                            case -1:
                                App.setIsLogin(false);
                                intent2Activity(LoginActivity1.class);
                                activity.finish();
                                break;
                            case 1:
                                intent2Activity(LeaveStatusActivity.class);
                                break;
                            case 5:
                                intent2Activity(OpinionActivity.class);
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

    @Subscribe
    public void onEvent(Event event) {
        if (event.getMsg().equals("sign")) {
            getPeople();
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

        getPeople();
    }

    private void initEvent() {

        Calendar cal1 = getCalendarFromDate("2016-04-18");
        long startTime = cal1.getTimeInMillis();
        Calendar cal2 = getCalendarFromDate("2017-04-19");
        long endTime = cal2.getTimeInMillis();

        Calendar cal3 = getCalendarFromDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        long currentTime = cal3.getTimeInMillis();

        int totalTime = (int) (endTime - startTime) / (24 * 60 * 60 * 1000);
        int current = (int) (currentTime - startTime) / (24 * 60 * 60 * 1000);
//        Log.e("roy", "totalTime" + totalTime + "~~~~" + "current" + current);
        float progress = (current / 365) * 100;
        int time = 365 - current;

        if (progress < 1) {
            progressBar.setProgress(1);
        } else {
            progressBar.setProgress((int) progress);
        }
        progressBar.setCricleProgressColor(Color.RED);
        progressBar.setText("" + time);


        sib.setSelectAnimClass(ZoomInEnter.class)
                .setSource(DataProvider.getList())
                .setTransformerClass(ZoomOutSlideTransformer.class)
                .startScroll();

        sib.setOnItemClickL(new BaseBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", App.getNewsList().get(position).getNewsInformation());
                intent.putExtra("name", App.getNewsList().get(position).getNewsName());
                startActivity(intent);
            }
        });

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), HomeDatas.getDatas(), R.layout.layout_gridview_home, new String[]{"imgId", "nameId"}, new int[]{R.id.gridView_img_item, R.id.gridView_text_item});
        gridView_home.setAdapter(adapter);

        gridView_home.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                intent2Activity(ProjectActivity.class);
                break;
            case 1:
                intent2Activity(ProjectDrawing.class);
                break;
            case 3:
                intent2Activity(MeetingActivity.class);
                break;
        }

    }

    private Calendar getCalendarFromDate(final String date) {
        int year = 0;
        int month = 0;
        int day = 0;
        try {
            String[] array = date.split("-");
            int[] arrayInt = new int[array.length];
            for (int i = 0; i < array.length; i++) {
                arrayInt[i] = Integer.parseInt(array[i]);
                if (i == 0) {
                    year = arrayInt[0];
                } else if (i == 1) {
                    month = arrayInt[1];
                } else if (i == 2) {
                    day = arrayInt[2];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        if (year > 0 && month >= 0 && day >= 0) {
            cal.set(year, month, day);
        }
        return cal;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
