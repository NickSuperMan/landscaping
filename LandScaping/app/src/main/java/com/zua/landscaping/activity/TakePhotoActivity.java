package com.zua.landscaping.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;

import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.app.Constant;
import com.zua.landscaping.utils.TitleBuilder;
import com.zua.landscaping.utils.ToastUtils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by roy on 4/28/16.
 */
public class TakePhotoActivity extends Activity {

    private ImageView imageView;
    private File file;
    private EditText editText;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout_take_photo);


        new TitleBuilder(this).setLeftImage(R.drawable.back).setTitleText(getString(R.string.photo_preview)).setRightText(getString(R.string.video_publish))
                .setLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        }).build();

        imageView = (ImageView) findViewById(R.id.photo_preview);
        editText = (EditText) findViewById(R.id.photo_description);

        file = getImageFile();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK || requestCode == 1) {

            Uri uri = Uri.fromFile(file);

            Log.e("roy", uri.toString() + "~~~~~~");

            ContentResolver resolver = getContentResolver();

            if (uri != null) {

                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
                    imageView.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void uploadPhoto() {
        if (null == dialog) {
            dialog = ProgressDialog.show(TakePhotoActivity.this, null, getString(R.string.photo_landing));
        }
        String description = editText.getText().toString().trim();

        AjaxParams params = new AjaxParams();
        params.put("userId", App.getUser().getUserId() + "");
        params.put("sceneDescription", description);
        params.put("scenePosition", App.getPosition() + "");
        Log.e("roy", App.getPosition());

        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FinalHttp http = new FinalHttp();
        http.post(Constant.BasePath + "UploadPhotoServlet", params, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                ToastUtils.showShort(TakePhotoActivity.this, "upload success");
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                deleteDirectory();
                finish();

            }

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);

                dialog.setMax((int) count);
                dialog.setProgress((int) count);

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("roy", t.toString() + strMsg);
            }
        });

    }


    private void deleteDirectory() {
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


    public File getImageFile() {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != dialog) {
            dialog = null;
        }
    }
}
