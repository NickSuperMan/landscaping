package com.zua.landscaping.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.app.Constant;
import com.zua.landscaping.bean.ImageItem;
import com.zua.landscaping.utils.BitmapUtils;
import com.zua.landscaping.utils.TitleBuilder;
import com.zua.landscaping.utils.ToastUtils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roy on 16/5/14.
 */
public class SafeUploadActivity extends Activity {

    private static final int TAKE_PICTURE = 1;
    private static final int REQUESTCODE_PICK = 2;
    private GridView noScrollgridview;
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    private GridAdapter adapter;
    private View parentView;
    private EditText editText;
    private List<File> paths = new ArrayList<>();
    private String pathImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentView = getLayoutInflater().inflate(R.layout.activity_layout_safe, null);
        setContentView(parentView);

        App.getInstance().addActivity(this);

        new TitleBuilder(this).setTitleText(getString(R.string.safe_upload)).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapUtils.tempSelectBitmap.clear();
                finish();
            }
        }).setRightText(getString(R.string.video_publish)).setRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
                finish();
            }
        }).build();

        init();
    }

    private void upload() {
        String description = editText.getText().toString().trim();

        AjaxParams params = new AjaxParams();
        for (int i = 0; i < paths.size(); i++) {
            try {
                if (paths.get(i) != null)
                    params.put("" + i, paths.get(i));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        params.put("userId", App.getUser().getUserId() + "");
        params.put("sceneDescription", description);
        params.put("scenePosition", App.getPosition() + "");
        params.put("sceneStatus", "2");

        FinalHttp http = new FinalHttp();
        http.post(Constant.BasePath + "UploadPhotoServlet", params, new AjaxCallBack<Object>() {

            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                ToastUtils.showShort(SafeUploadActivity.this, "upload success");
            }
        });
    }

    public void init() {

        editText = (EditText) findViewById(R.id.editText);

        pop = new PopupWindow(this);

        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                photo();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickIntent, REQUESTCODE_PICK);
//                Intent intent = new Intent(MainActivity.this,
//                        AlbumActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });

        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == BitmapUtils.tempSelectBitmap.size()) {

                    ll_popup.startAnimation(AnimationUtils.loadAnimation(SafeUploadActivity.this, R.anim.activity_translate_in));
                    pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                } else {
//                    Intent intent = new Intent(MainActivity.this,
//                            GalleryActivity.class);
//                    intent.putExtra("position", "1");
//                    intent.putExtra("ID", arg2);
//                    startActivity(intent);
                }
            }
        });
    }

    private void photo() {

        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PICTURE:
                File file = BitmapUtils.getImageFile();
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                BitmapUtils.saveBitmap(bm, file);

                ImageItem takePhoto = new ImageItem();
                takePhoto.setImagePath(file.toString());
                Log.e("roy", file.toString());
                paths.add(file);
                BitmapUtils.tempSelectBitmap.add(takePhoto);
                break;
            case REQUESTCODE_PICK:
                if (data.getData() != null)
                    setPicToView(data.getData());
                break;

        }
    }

    private void setPicToView(Uri uri) {

        if (!TextUtils.isEmpty(uri.getAuthority())) {
            //查询选择图片
            Cursor cursor = getContentResolver().query(
                    uri,
                    new String[]{MediaStore.Images.Media.DATA},
                    null,
                    null,
                    null);
            //返回 没找到选择图片
            if (null == cursor) {
                return;
            }
            //光标移动至开头 获取图片路径
            cursor.moveToFirst();
            pathImage = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));


            File file = new File(pathImage);


            ImageItem takePhoto = new ImageItem();
            takePhoto.setImagePath(pathImage);

            Log.e("roy", file.toString() + "~~~~~");
            paths.add(file);
            BitmapUtils.tempSelectBitmap.add(takePhoto);
        }

    }


    public class GridAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if (BitmapUtils.tempSelectBitmap.size() == 8) {
                return 8;
            }
            return (BitmapUtils.tempSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.activity_gridview_addpic,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == BitmapUtils.tempSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 8) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(BitmapUtils.tempSelectBitmap.get(position).getBitmap());
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (BitmapUtils.max == BitmapUtils.tempSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            BitmapUtils.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }

    protected void onRestart() {
        if (adapter != null)
            adapter.update();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        if (adapter != null)
            adapter.update();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        BitmapUtils.tempSelectBitmap.clear();

    }
}
