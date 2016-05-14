package com.zua.landscaping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.app.Constant;
import com.zua.landscaping.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by roy on 4/26/16.
 */
public class MyGroupAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<User> datas;
    private Map<Integer, Boolean> checked = new HashMap<>();

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public MyGroupAdapter(Context context) {

        inflater = LayoutInflater.from(context);

        datas = App.getUser().getFriends();
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.group_popup_list, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.popup_name);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.popup_pic);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_group);
            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) v;
                    checked.put(position, checkBox.isChecked());
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(Constant.BasePath + datas.get(position).getUserPicUrl(), viewHolder.imageView, options);

        viewHolder.textView.setText(datas.get(position).getUserName());
        viewHolder.checkBox.setTag(position);
        return convertView;
    }

    public List<String> checkedList(/*SelectedData selectedData*/) {
        List<String> addList = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> entry : checked.entrySet()) {
            if (entry.getValue()) {
                addList.add(datas.get(entry.getKey()).getUserId() + "");
            }
        }
        return addList;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;
        CheckBox checkBox;
    }
}
