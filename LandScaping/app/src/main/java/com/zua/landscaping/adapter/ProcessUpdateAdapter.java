package com.zua.landscaping.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Code;
import com.zua.landscaping.bean.Project;
import com.zua.landscaping.bean.Update;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.ServiceGenerator;
import com.zua.landscaping.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roy on 16/5/5.
 */
public class ProcessUpdateAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Project> data;
    private Context context;
    private List<Update> list;
    private HashMap<Integer, Boolean> isUpdated;


    public ProcessUpdateAdapter(Context context, List<Update> updateList) {
        inflater = LayoutInflater.from(context);
        data = App.getProjectList();
        this.context = context;
        this.list = updateList;
        setButtonStatus();
    }

    private void setButtonStatus() {

        isUpdated = new HashMap<>();

        for (int i = 0; i < data.size(); i++) {
            isUpdated.put(i, false);
            for (int j = 0; j < list.size(); j++) {
                if (data.get(i).getpId() == list.get(j).getpId()) {
                    isUpdated.put(i, true);
                }
            }
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        convertView = inflater.inflate(R.layout.activity_layout_process_update_item, null);
        holder = new ViewHolder();
        holder.textView = (TextView) convertView.findViewById(R.id.item_project_name);
        holder.button = (Button) convertView.findViewById(R.id.item_project_update);


        holder.textView.setText(data.get(position).getpName() + "");


        if (isUpdated.get(position)) {
            holder.button.setText(context.getString(R.string.process_update_success));
        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProcess(v, position);
            }
        });

        return convertView;
    }


    private void updateProcess(final View v, final int position) {
        ConnService service = ServiceGenerator.createService(ConnService.class);
        Call<Code> call = service.updateProcess(data.get(position).getpId() + "", "update");
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.isSuccess()) {
                    isUpdated.put(position, true);
                    Button button = (Button) v;

                    button.setText(context.getString(R.string.process_update_success));
                    button.setClickable(false);
                    ToastUtils.showShort(context, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<Code> call, Throwable t) {

            }
        });
    }


    class ViewHolder {
        TextView textView;
        Button button;
    }


}
