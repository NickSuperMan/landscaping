package xzy.zzia.com.landscaping.testData;

import java.util.ArrayList;
import java.util.HashMap;

import xzy.zzia.com.landscaping.R;

/**
 * Created by roy on 16/4/20.
 */
public class HomeDatas {

    public static String[] name = new String[]{
            "进度查看", "图纸查看", "文件查看", "会议记录"
    };
    public static int[] img = new int[]{R.drawable.tabbar_compose_camera, R.drawable.tabbar_compose_review, R.drawable.tabbar_compose_photo
            , R.drawable.tabbar_compose_more};

    public static ArrayList<HashMap<String, Object>> datas = new ArrayList<>();

    public static ArrayList getDatas() {
        for (int i = 0; i < name.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("imgId", img[i]);
            map.put("nameId", name[i]);
            datas.add(map);
        }
        return datas;
    }
}
