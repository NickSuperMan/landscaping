package com.zua.landscaping.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zua.landscaping.R;
import com.zua.landscaping.bean.Leave;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by roy on 16/5/4.
 */
public class ApplyLeaveStatusAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Leave> data;
    private Context context;

    public ApplyLeaveStatusAdapter(Context context, List<Leave> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = list;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_layout_apply_leave_item, null);
            holder = new ViewHolder();
            holder.reason = (TextView) convertView.findViewById(R.id.item_leave_reason);
            holder.leave_time = (TextView) convertView.findViewById(R.id.item_leave_time);
            holder.back_time = (TextView) convertView.findViewById(R.id.item_leave_back_time);
            holder.leave_status = (TextView) convertView.findViewById(R.id.item_leave_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        holder.reason.setText(data.get(position).getLeaveReason());
        holder.leave_time.setText(format.format(data.get(position).getLeaveTime()));
        holder.back_time.setText(format.format(data.get(position).getBackTime()));
        if (data.get(position).getLeaveStatus() == 1){
            holder.leave_status.setText(context.getString(R.string.leave_success));
        }else if (data.get(position).getLeaveStatus() == 0){
            holder.leave_status.setText(context.getString(R.string.leave_waiting));

        }else{
            holder.leave_status.setText(context.getString(R.string.leave_failure));
        }

        return convertView;
    }

    class ViewHolder {
        TextView reason;
        TextView leave_time;
        TextView back_time;
        TextView leave_status;
    }
}
