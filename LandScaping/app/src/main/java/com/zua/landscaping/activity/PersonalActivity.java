package com.zua.landscaping.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zua.landscaping.R;
import com.zua.landscaping.adapter.PersonalAdapter;
import com.zua.landscaping.app.App;
import com.zua.landscaping.app.Constant;
import com.zua.landscaping.bean.User;
import com.zua.landscaping.utils.BitmapUtils;
import com.zua.landscaping.utils.TitleBuilder;
import com.zua.landscaping.utils.ToastUtils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by roy on 16/4/21.
 */
public class PersonalActivity extends Activity implements AdapterView.OnItemClickListener {

    private static final int REQUESTCODE_CUTTING = 2;
    private ListView listView;
    private PersonalAdapter adapter;
    private User user;
    private final static int REQUESTCODE_PICK = 1;
    private File file = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_personal);

        new TitleBuilder(this).setTitleText(getString(R.string.personal_center)).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).build();
        user = App.getUser();
        initView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            user = App.getUser();
            adapter.notifyDataSetChanged();
        }
    }

    private void initView() {

        listView = (ListView) findViewById(R.id.person_listView);
        adapter = new PersonalAdapter(this, user);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickIntent, REQUESTCODE_PICK);
                break;
            case 1:
                startActivity(new Intent(this, ModifySignActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, ModifyAgeActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, ModifyTelActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUESTCODE_PICK:
                try {

                    startPhotoZoom(data.getData());

                } catch (NullPointerException e) {
                    e.printStackTrace();// 用户点击取消操作
                }
                break;
            case REQUESTCODE_CUTTING:

                setPicToView(data);

                break;
        }
    }

    private void setPicToView(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Bitmap photo = bundle.getParcelable("data");
            if (photo != null) {
                BitmapDrawable drawable = BitmapUtils.zoomBitmap(getResources(), photo, 320, 320);
                saveImageToLocal(photo);
                App.setIcon(drawable.getBitmap());
                Log.e("roy", file.toString());
                upLoadIcon(file);
            }
        }
    }

    private void upLoadIcon(File file) {

        AjaxParams params = new AjaxParams();
        User user = App.getUser();
        params.put("userId", user.getUserId() + "");
        params.put("userAge", user.getUserAge());
        params.put("userTel", user.getUserTel());
        params.put("userSign", user.getUserSign());
        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FinalHttp http = new FinalHttp();
        http.post(Constant.BasePath + "UploadIconServlet", params, new AjaxCallBack<Object>() {

            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                BitmapUtils.deleteDirectory();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });

    }

    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 320);// this seting cant work on samsung
        // Galaxy SIII , we need manual scale to
        // 320X320
        intent.putExtra("outputY", 320);// this seting cant work on samsung
        // Galaxy SIII, we need manual scale to
        // 320X320
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }


    private void saveImageToLocal(Bitmap photo) {
        file = BitmapUtils.getImageFile();
        try {
            FileOutputStream out = new FileOutputStream(file);
            // photo.compress(Bitmap.CompressFormat.PNG, 50, out);
            BitmapUtils.bitmapCompress(photo, 64.00, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
