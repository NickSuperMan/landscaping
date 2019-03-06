package com.zua.landscaping.utils;

import android.content.Context;
import android.media.AudioManager;
import android.os.storage.StorageManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123 on 2016/2/19.
 */
public class LocUtil {

    /**
     * 获取所有存储路径
     *
     * @param context
     * @return
     */
    public static List<String> getDirs(Context context) {
        List<String> dirs = new ArrayList<>();
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);

        try {
            Class[] paramClasses = {};
            Method getVolumePathMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
            getVolumePathMethod.setAccessible(true);

            Object[] params = {};
            Object invoke = getVolumePathMethod.invoke(storageManager, params);
            for (int i = 0; i < ((String[]) invoke).length; i++) {
                dirs.add(((String[]) invoke)[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dirs;
    }

    /**
     * 获取最大音量
     *
     * @param context
     * @return
     */
    public static int getMaxVolume(Context context) {
        return ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE))
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    /**
     * 获取当前音量
     *
     * @param context
     * @return
     */
    public static int getCurVolume(Context context) {
        return ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE))
                .getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    /**
     * 设置当前音量
     *
     * @param context
     * @param index
     */
    public static void setCurVolume(Context context, int index) {
        ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE))
                .setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
    }

    /**
     * 获取屏幕宽高
     *
     * @param context
     * @return
     */
//    public static ScreenBean getScreenPix(Context context) {
//        Display display = ((VideoPlayerActivity) context).getWindowManager().getDefaultDisplay();
//        return new ScreenBean(display.getWidth(), display.getHeight());
//    }
}
