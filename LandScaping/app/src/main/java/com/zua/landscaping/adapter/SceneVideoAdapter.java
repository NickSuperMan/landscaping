package com.zua.landscaping.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.yqritc.scalablevideoview.ScalableVideoView;
import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.Scene;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.ServiceGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roy on 4/29/16.
 */
public class SceneVideoAdapter extends BaseAdapter {

    private static final String MEDIA_FILE_DIR = "/LandScaping" + File.separator + "download";
    private LayoutInflater inflater;

    private List<Scene> sceneVideoList;

    private Context context;

    private String path;

    public SceneVideoAdapter(Context context, List<Scene> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        sceneVideoList = data;
    }

    @Override
    public int getCount() {
        return sceneVideoList == null ? 0 : sceneVideoList.size();
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
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_scene_video_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.item_video_name);
            holder.videoView = (ScalableVideoView) convertView.findViewById(R.id.item_video_videoview);

            try {

                // 这个调用是为了初始化mediaplayer并让它能及时和surface绑定
                holder.videoView.setDataSource("");

                holder.videoView.setVolume(1, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            holder.playImageView = (ImageView) convertView.findViewById(R.id.item_video_playImageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(sceneVideoList.get(position).getSceneDescription());

        final ViewHolder finalHolder = holder;


        holder.playImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("roy", sceneVideoList.get(position).getVideoUrl() + "");

                if (sceneVideoList.get(position).getVideoUrl() != null) {


                    path = sceneVideoList.get(position).getVideoUrl();
                    playVideo(finalHolder.videoView, finalHolder.playImageView);
                } else {
                    path = null;
                    downLoadVideo(finalHolder.videoView, position, finalHolder.playImageView);
                }
            }
        });

        holder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.videoView.stop();
                finalHolder.playImageView.setVisibility(View.VISIBLE);
            }
        });

        return convertView;
    }


    private void downLoadVideo(final ScalableVideoView videoView, final int position, final ImageView playImageView) {
        ConnService service = ServiceGenerator.createService(ConnService.class);
        Call<ResponseBody> call = service.downloadFileWithDynamicUrlSync(sceneVideoList.get(position).getScenePicUrl());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccess()) {

                    Log.e("roy", "success+");
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());

                    sceneVideoList.get(position).setVideoUrl(path);

                    Log.e("roy", path);
                    if (writtenToDisk) {
                        playVideo(videoView, playImageView);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("roy", t.toString());
            }
        });
    }

    private void playVideo(ScalableVideoView mScalableVideoView, ImageView mPlayImageView) {

        try {
            mScalableVideoView.setDataSource(path);
            mScalableVideoView.setLooping(true);
            mScalableVideoView.prepare();
            mScalableVideoView.start();
            mPlayImageView.setVisibility(View.GONE);

        } catch (IOException e) {
            Log.e("roy", e.getLocalizedMessage());
            Toast.makeText(context, "播放视频异常", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + MEDIA_FILE_DIR);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e("roy", "failed to create directory");
            }
        }

        try {
            // todo change the file location/name according to your needs
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".mp4";

            path = Environment.getExternalStorageDirectory() + MEDIA_FILE_DIR + File.separator + timeStamp;


            File futureStudioIconFile = new File(path);


            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.e("roy", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    public List<Scene> getDataList() {
        return sceneVideoList;
    }

    class ViewHolder {
        TextView textView;
        ScalableVideoView videoView;
        //        ImageView thumbnailImageView;
        ImageView playImageView;

    }

}
