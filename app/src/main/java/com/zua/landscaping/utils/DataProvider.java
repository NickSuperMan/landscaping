package com.zua.landscaping.utils;


import com.zua.landscaping.app.App;
import com.zua.landscaping.app.Constant;
import com.zua.landscaping.bean.BannerItem;
import com.zua.landscaping.bean.News;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by roy on 16/4/20.
 */
public class DataProvider {

    public static String[] titles = new String[]{
            "伪装者:胡歌演绎'痞子特工'",
            "无心法师:生死离别!月牙遭虐杀",
            "花千骨:尊上沦为花千骨",
            "综艺饭:胖轩偷看夏天洗澡掀波澜",
            "碟中谍4:阿汤哥高塔命悬一线,超越不可能",
    };
    public static String[] urls = new String[]{//640*360 360/640=0.5625
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160323071011277.jpg",//伪装者:胡歌演绎"痞子特工"
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144158380433341332.jpg",//无心法师:生死离别!月牙遭虐杀
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160286644953923.jpg",//花千骨:尊上沦为花千骨
            "http://photocdn.sohu.com/tvmobilemvms/20150902/144115156939164801.jpg",//综艺饭:胖轩偷看夏天洗澡掀波澜
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144159406950245847.jpg",//碟中谍4:阿汤哥高塔命悬一线,超越不可能
    };

    public static ArrayList<BannerItem> getList() {
        List<News> newsList = App.getNewsList();
        ArrayList<BannerItem> list = new ArrayList<>();
        for (int i = 0; i < newsList.size(); i++) {
            BannerItem item = new BannerItem();
            item.imgUrl = Constant.BasePath+newsList.get(i).getNewsPicUrl();
            item.title = newsList.get(i).getNewsName();

            list.add(item);
        }

        return list;
    }

}
