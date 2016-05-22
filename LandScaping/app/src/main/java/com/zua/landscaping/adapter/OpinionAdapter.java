package com.zua.landscaping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zua.landscaping.R;
import com.zua.landscaping.bean.Scene;

import java.util.List;

/**
 * Created by roy on 16/5/14.
 */
public class OpinionAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Scene> opinionLists;

    public OpinionAdapter(Context context, List<Scene> datas) {
        inflater = LayoutInflater.from(context);
        this.opinionLists = datas;
    }

    @Override
    public int getCount() {
        return opinionLists == null ? 0 : opinionLists.size();
    }

    @Override
    public Object getItem(int position) {
        return opinionLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_scene_opinion_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.opinion_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.textView.setText(opinionLists.get(position).getSceneDescription());


        return convertView;
    }

    public List<Scene> getDataList() {
        return opinionLists;
    }

    class ViewHolder {
        TextView textView;
    }
}
