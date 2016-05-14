package com.zua.landscaping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Technical;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by roy on 16/5/8.
 */
public class TechnicalAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Technical> technicalList;

    public TechnicalAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        technicalList = App.getTechnicalList();
    }

    @Override
    public int getCount() {
        return technicalList == null ? 0 : technicalList.size();
    }

    @Override
    public Object getItem(int position) {
        return technicalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_technical_item, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.technical_title);
            holder.tv_time = (TextView) convertView.findViewById(R.id.technical_time);
            holder.tv_sign = (TextView) convertView.findViewById(R.id.technical_sign);
            holder.tv_content = (TextView) convertView.findViewById(R.id.technical_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        holder.tv_title.setText(technicalList.get(position).getTechnicalName());
        holder.tv_time.setText(format.format(technicalList.get(position).getTechnicalTime()));
        holder.tv_sign.setText(technicalList.get(position).getTechnicalStatus());
        holder.tv_content.setText(technicalList.get(position).getTechnicalSummary());

        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_time;
        TextView tv_sign;
        TextView tv_content;
    }
}
