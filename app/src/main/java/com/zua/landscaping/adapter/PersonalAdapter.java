package com.zua.landscaping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.User;

/**
 * Created by roy on 16/5/13.
 */
public class PersonalAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private final int TYPE_ONE = 0, TYPE_TWO = 1, TYPE_COUNT = 2;
    private String[] datas = {"头像", "昵称", "年龄", "手机"};
    private String[] list;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private User user;

    public PersonalAdapter(Context context, User user) {
        inflater = LayoutInflater.from(context);
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        this.user = user;
    }


    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0)
            return TYPE_ONE;
        else
            return TYPE_TWO;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.length;
    }

    @Override
    public Object getItem(int position) {
        return datas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        list = new String[]{user.getUserPicUrl(), user.getUserSign(), user.getUserAge(), user.getUserTel()};

        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;

        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case TYPE_ONE:
                    holder1 = new ViewHolder1();
                    convertView = inflater.inflate(R.layout.activity_layout_personal_item1, null);
                    holder1.tv1 = (TextView) convertView.findViewById(R.id.item_personal_pic);
                    holder1.imageView = (ImageView) convertView.findViewById(R.id.item_personal_img);
                    convertView.setTag(holder1);
                    break;
                case TYPE_TWO:

                    holder2 = new ViewHolder2();
                    convertView = inflater.inflate(R.layout.activity_layout_personal_item2, null);
                    holder2.tv2 = (TextView) convertView.findViewById(R.id.item_personal_tv1);
                    holder2.tv3 = (TextView) convertView.findViewById(R.id.item_personal_tv2);

                    convertView.setTag(holder2);
                    break;
            }
        } else {
            switch (type) {
                case TYPE_ONE:
                    holder1 = (ViewHolder1) convertView.getTag();
                    break;
                case TYPE_TWO:
                    holder2 = (ViewHolder2) convertView.getTag();
                    break;
            }
        }

        switch (type) {
            case TYPE_ONE:

                holder1.tv1.setText(datas[position]);
                holder1.imageView.setImageBitmap(App.getIcon());

                break;
            case TYPE_TWO:

                holder2.tv2.setText(datas[position]);
                holder2.tv3.setText(list[position] + "");

                break;
        }

        return convertView;
    }

    class ViewHolder1 {
        TextView tv1;
        ImageView imageView;
    }

    class ViewHolder2 {
        TextView tv2;
        TextView tv3;

    }
}
