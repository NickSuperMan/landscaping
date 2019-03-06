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


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yqritc.scalablevideoview.ScalableVideoView;
import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.app.Constant;
import com.zua.landscaping.bean.Scene;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.ServiceGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
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

    private String localPath;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public SceneVideoAdapter(Context context, List<Scene> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        sceneVideoList = data;
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
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

            holder.tv_user_name = (TextView) convertView.findViewById(R.id.item_video_user_name);

            holder.img_user_pic = (ImageView) convertView.findViewById(R.id.item_video_user_pic);

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
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.item_video_thumbnailImageView);
            holder.tv_position = (TextView) convertView.findViewById(R.id.item_video_position);
            holder.tv_time = (TextView) convertView.findViewById(R.id.item_video_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_user_name.setText(sceneVideoList.get(position).getUserName());
        imageLoader.displayImage(Constant.BasePath + sceneVideoList.get(position).getUserPicUrl(), holder.img_user_pic, options);

        imageLoader.displayImage(Constant.BasePath + sceneVideoList.get(position).getScenePicUrl().split("_")[1], holder.thumbnailImageView, options);

        holder.textView.setText(sceneVideoList.get(position).getSceneDescription());

        final ViewHolder finalHolder = holder;


        holder.playImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("roy", sceneVideoList.get(position).getVideoUrl() + "");

                if (sceneVideoList.get(position).getVideoUrl() != null) {

                    localPath = sceneVideoList.get(position).getVideoUrl();
                    playVideo(finalHolder.videoView, finalHolder.playImageView, finalHolder.thumbnailImageView);
                } else {
                    localPath = null;
                    downLoadVideo(finalHolder.videoView, position, finalHolder.playImageView, finalHolder.thumbnailImageView);
                }
            }
        });

        holder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.videoView.stop();
                finalHolder.playImageView.setVisibility(View.VISIBLE);
                finalHolder.thumbnailImageView.setVisibility(View.VISIBLE);
            }
        });
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        holder.tv_position.setText(sceneVideoList.get(position).getScenePosition());
        holder.tv_time.setText(format.format(sceneVideoList.get(position).getSceneTime()));

        return convertView;
    }


    private void downLoadVideo(final ScalableVideoView videoView, final int position, final ImageView playImageView, final ImageView thumbImageView) {
        ConnService service = ServiceGenerator.createService(ConnService.class);

        Call<ResponseBody> call = service.downloadFileWithDynamicUrlSync(sceneVideoList.get(position).getScenePicUrl().split("_")[2]);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccess()) {

                    boolean writtenToDisk = writeResponseBodyToDisk(response.body());

                    sceneVideoList.get(position).setVideoUrl(localPath);

                    if (writtenToDisk) {
                        playVideo(videoView, playImageView, thumbImageView);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("roy", t.toString());
            }
        });
    }

    private void playVideo(ScalableVideoView mScalableVideoView, ImageView mPlayImageView, ImageView thumbImageView) {

        try {
            mScalableVideoView.setDataSource(localPath);
            mScalableVideoView.setLooping(true);
            mScalableVideoView.prepare();
            mScalableVideoView.start();
            mPlayImageView.setVisibility(View.GONE);
            thumbImageView.setVisibility(View.GONE);
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

            localPath = Environment.getExternalStorageDirectory() + MEDIA_FILE_DIR + File.separator + timeStamp;


            File futureStudioIconFile = new File(localPath);


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

//                    Log.e("roy", "file download: " + fileSizeDownloaded + " of " + fileSize);
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
        TextView tv_user_name;
        ImageView img_user_pic;
        TextView textView;
        ScalableVideoView videoView;
        ImageView thumbnailImageView;
        ImageView playImageView;
        TextView tv_position;
        TextView tv_time;
    }

}
