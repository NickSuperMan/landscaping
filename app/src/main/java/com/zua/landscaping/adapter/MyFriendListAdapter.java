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
import com.zua.landscaping.bean.User;
import com.zua.landscaping.utils.ViewFindUtils;

import java.util.List;


/**
 * Created by roy on 4/25/16.
 */
public class MyFriendListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<User> friends = App.getUser().getFriends();
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public MyFriendListAdapter(Context context) {

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return friends == null ? 0 : friends.size();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_fragment_friend_item, null);
            holder = new ViewHolder();
            holder.imageView = ViewFindUtils.find(convertView, R.id.friend_image);
            holder.textView = ViewFindUtils.find(convertView, R.id.friend_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        imageLoader.displayImage(Constant.BasePath + friends.get(position).getUserPicUrl(), holder.imageView, options);

        holder.textView.setText(friends.get(position).getUserName() + "");


        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
