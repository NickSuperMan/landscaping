package com.zua.landscaping.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;

import com.zua.landscaping.bean.ImageItem;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by roy on 16/5/13.
 */
public class BitmapUtils {

    public static String SDPATH = Environment.getExternalStorageDirectory()
            + "/LandScaping/";

    public static int max = 0;

    public static ArrayList<ImageItem> tempSelectBitmap = new ArrayList<>();   //选择的图片的临时列表

    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }


    public static void saveBitmap(Bitmap bm, File f) {
        try {
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void deleteDirectory() {

        File imageFileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "LandScaping" + File.separator + "image");
        if (imageFileDir.exists() && imageFileDir.isDirectory()) {
            File[] files = imageFileDir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    File imageFile = new File(files[i].getAbsolutePath());
                    if (imageFile.isFile() && imageFile.exists()) {
                        imageFile.delete();
                    }
                }
            }
            imageFileDir.delete();
        }
    }


    public static File getImageFile() {

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        String fileName = dateFormat.format(date);
        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "LandScaping" + File.separator + "image";
        File rootFile = new File(rootPath);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        File newFile = new File(rootPath + File.separator + fileName + ".jpg");
        return newFile;

    }

    public static BitmapDrawable zoomBitmap(Resources res, Bitmap bitmap, int newWidth, int newHeight) {

        int oldWidth = bitmap.getWidth();
        int oldHeight = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) newWidth / oldWidth);
        float scaleHeight = ((float) newHeight / oldHeight);
        matrix.postScale(scaleWidht, scaleHeight);

        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, oldWidth, oldHeight,
                matrix, true);
        return new BitmapDrawable(res, newbmp);
    }


    public static void bitmapCompress(Bitmap bitmap, double maxSize,
                                      FileOutputStream out) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);//the
        // compression wont work as PNG which is lossless, will ignore the
        // quality setting.
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        double mid = b.length / 1024;
        while (mid > maxSize) {
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            b = baos.toByteArray();
            mid = b.length / 1024;
        }
        // save bitmap to file system
        try {
            out.write(b);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
