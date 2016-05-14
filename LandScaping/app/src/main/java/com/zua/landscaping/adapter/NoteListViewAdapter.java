package com.zua.landscaping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zua.landscaping.R;
import com.zua.landscaping.bean.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by roy on 16/5/11.
 */
public class NoteListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Note> mDatas;

    public NoteListViewAdapter(Context context, List<Note> datas) {
        inflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_layout_note_item, null);
            holder = new ViewHolder();
            holder.note_title = (TextView) convertView.findViewById(R.id.item_note_title);
            holder.note_time = (TextView) convertView.findViewById(R.id.item_note_time);
            holder.note_content = (TextView) convertView.findViewById(R.id.item_note_content);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.note_title.setText(mDatas.get(position).getNoteTitle());
        holder.note_content.setText(mDatas.get(position).getNoteContet());
        DateFormat format = new SimpleDateFormat("MM月dd日HH:mm");
        holder.note_time.setText(format.format(mDatas.get(position).getNoteTime()));

        return convertView;
    }

    class ViewHolder {
        TextView note_title;
        TextView note_time;
        TextView note_content;
    }
}
