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
import com.zua.landscaping.bean.Scene;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roy on 4/29/16.
 */
public class ScenePhotoAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Scene> scenePhotoList;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public ScenePhotoAdapter(Context context, List<Scene> datas) {

        inflater = LayoutInflater.from(context);
        this.scenePhotoList = datas;

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public int getCount() {
        return scenePhotoList == null ? 0 : scenePhotoList.size();
    }

    @Override
    public Object getItem(int position) {
        return scenePhotoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_scene_photo_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.item_photo_name);
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_photo_image);
            holder.tv_position = (TextView) convertView.findViewById(R.id.item_photo_position);
            holder.tv_time = (TextView) convertView.findViewById(R.id.item_photo_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String photoPath = scenePhotoList.get(position).getScenePicUrl().split("jpg")[0]+"jpg";

        imageLoader.displayImage(Constant.BasePath + photoPath, holder.imageView, options);
        holder.textView.setText(scenePhotoList.get(position).getSceneDescription());
        holder.tv_position.setText(scenePhotoList.get(position).getScenePosition());
        holder.tv_time.setText(format.format(scenePhotoList.get(position).getSceneTime()));

        return convertView;
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
        TextView tv_position;
        TextView tv_time;
    }
}
