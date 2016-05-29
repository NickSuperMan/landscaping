package com.zua.landscaping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zua.landscaping.R;
import com.zua.landscaping.bean.Meeting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by roy on 16/5/24.
 */
public class MeetingAdatpter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Meeting> meetingList;

    public MeetingAdatpter(Context context, List<Meeting> data) {
        inflater = LayoutInflater.from(context);
        meetingList = data;
    }

    @Override
    public int getCount() {
        return meetingList == null ? 0 : meetingList.size();
    }

    @Override
    public Object getItem(int position) {
        return meetingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.activity_layout_meeting_item, null);
            holder.tv_meet_name = (TextView) convertView.findViewById(R.id.item_meeting_name);
            holder.tv_meet_compere = (TextView) convertView.findViewById(R.id.item_meeting_compere);
            holder.tv_meet_time = (TextView) convertView.findViewById(R.id.item_meeting_time);
            holder.tv_meet_content = (TextView) convertView.findViewById(R.id.item_meeting_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Meeting meeting = meetingList.get(position);
        holder.tv_meet_name.setText(meeting.getMeetName());
        holder.tv_meet_compere.setText(meeting.getMeetCompere());
        holder.tv_meet_time.setText(format.format(meeting.getMeetTime()));
        holder.tv_meet_content.setText(meeting.getMeetContent());
        return convertView;
    }

    public List<Meeting> getDataList() {
        return meetingList;
    }


    class ViewHolder {
        TextView tv_meet_name;
        TextView tv_meet_compere;
        TextView tv_meet_time;
        TextView tv_meet_content;
    }
}
