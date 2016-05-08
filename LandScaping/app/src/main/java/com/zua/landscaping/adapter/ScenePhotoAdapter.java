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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roy on 4/29/16.
 */
public class ScenePhotoAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Scene> scenePhotoList = new ArrayList<>();
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public ScenePhotoAdapter(Context context) {

        inflater = LayoutInflater.from(context);
        scenePhotoList = App.getScenePhotoList();

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public int getCount() {
        return scenePhotoList.size() == 0 ? 0 : scenePhotoList.size();
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        imageLoader.displayImage(Constant.PhotoPath + scenePhotoList.get(position).getScenePicUrl(), holder.imageView, options);
        holder.textView.setText(scenePhotoList.get(position).getSceneDescription());

        return convertView;
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
