package com.zua.landscaping.adapter;

import android.content.Context;
import android.util.Log;
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

import java.util.List;


/**
 * Created by roy on 16/5/6.
 */
public class DeviceAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Device> deviceList;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public DeviceAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        deviceList = App.getDeviceList();

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public int getCount() {
        return deviceList == null ? 0 : deviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return deviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_layout_device_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_device_image);
            holder.tv_name = (TextView) convertView.findViewById(R.id.item_device_name);
            holder.tv_owner = (TextView) convertView.findViewById(R.id.item_device_owner);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        imageLoader.displayImage(Constant.BasePath + deviceList.get(position).getDevicecPicUrl(), holder.imageView, options);

        holder.tv_name.setText(deviceList.get(position).getDeviceName() + "");
        holder.tv_owner.setText(deviceList.get(position).getDeviceOwner() + "");
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView tv_name;
        TextView tv_owner;
    }
}
