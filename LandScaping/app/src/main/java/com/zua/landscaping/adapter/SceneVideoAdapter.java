package com.zua.landscaping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Scene;

import java.util.List;

/**
 * Created by roy on 4/29/16.
 */
public class SceneVideoAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private List<Scene> sceneVideoList;

    public SceneVideoAdapter(Context context) {

        inflater = LayoutInflater.from(context);
        sceneVideoList = App.getSceneVideoList();
    }

    @Override
    public int getCount() {
        return sceneVideoList.size() == 0 ? 0 : sceneVideoList.size();
    }

    @Override
    public Object getItem(int position) {
        return sceneVideoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_scene_video_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.item_video_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(sceneVideoList.get(position).getSceneDescription());

        return convertView;
    }

    class ViewHolder {
        TextView textView;

    }
}
