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
import com.zua.landscaping.app.Constant;
import com.zua.landscaping.bean.Device;
import com.zua.landscaping.bean.Drawing;

import java.util.List;


/**
 * Created by roy on 16/5/6.
 */
public class DrawingAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Drawing> drawingList;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public DrawingAdapter(Context context, List<Drawing> data) {
        inflater = LayoutInflater.from(context);
        drawingList = data;

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public int getCount() {
        return drawingList == null ? 0 : drawingList.size();
    }

    @Override
    public Object getItem(int position) {
        return drawingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_layout_drawing_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_drawing_img);
            holder.tv_name = (TextView) convertView.findViewById(R.id.item_drawing_name);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        imageLoader.displayImage(Constant.BasePath + drawingList.get(position).getDrawPicUrl(), holder.imageView, options);

        holder.tv_name.setText(drawingList.get(position).getDrawName() + "");

        return convertView;
    }

    public List<Drawing> getDataList() {
        return drawingList;
    }

    class ViewHolder {
        ImageView imageView;
        TextView tv_name;
    }
}
