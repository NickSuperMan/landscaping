package com.zua.landscaping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

            holder.tv_user_name = (TextView) convertView.findViewById(R.id.item_photo_user_name);
            holder.img_user_pic = (ImageView) convertView.findViewById(R.id.item_photo_user_pic);

            holder.textView = (TextView) convertView.findViewById(R.id.item_photo_name);
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_photo_image);
            holder.tv_position = (TextView) convertView.findViewById(R.id.item_photo_position);
            holder.tv_time = (TextView) convertView.findViewById(R.id.item_photo_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_user_name.setText(scenePhotoList.get(position).getUserName());
        imageLoader.displayImage(Constant.BasePath + scenePhotoList.get(position).getUserPicUrl(), holder.img_user_pic, options);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String photoPath = scenePhotoList.get(position).getScenePicUrl().split("jpg")[0] + "jpg";

        imageLoader.displayImage(Constant.BasePath + photoPath, holder.imageView, options);
        holder.textView.setText(scenePhotoList.get(position).getSceneDescription());
        holder.tv_position.setText(scenePhotoList.get(position).getScenePosition());
        holder.tv_time.setText(format.format(scenePhotoList.get(position).getSceneTime()));

        return convertView;
    }

    public List<Scene> getDataList() {
        return scenePhotoList;
    }

    class ViewHolder {
        TextView tv_user_name;
        ImageView img_user_pic;
        TextView textView;
        ImageView imageView;
        TextView tv_position;
        TextView tv_time;
    }
}
