package com.zua.landscaping.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.User;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.RongCouldEvent;
import com.zua.landscaping.utils.ServiceGenerator;
import com.zua.landscaping.utils.ToastUtils;
import com.zua.landscaping.view.EditTextHolder;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by roy on 4/23/16.
 */
public class LoginActivity1 extends Activity implements View.OnClickListener {

    /**
     * 用户账户
     */
    private EditText mUserNameEt;
    /**
     * 密码
     */
    private EditText mPassWordEt;
    /**
     * 登录button
     */
    private Button mSignInBt;
    /**
     * 输入用户名删除按钮
     */
    private FrameLayout mFrUserNameDelete;
    /**
     * 输入密码删除按钮
     */
    private FrameLayout mFrPasswordDelete;

    private ImageView mImgBackgroud;

    EditTextHolder mEditUserNameEt;
    EditTextHolder mEditPassWordEt;
    Drawable drawable;

    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login1);
        App.getInstance().addActivity(this);

        initView();
    }


    private void initView() {

        mUserNameEt = (EditText) findViewById(R.id.app_username_et);
        mPassWordEt = (EditText) findViewById(R.id.app_password_et);
        mSignInBt = (Button) findViewById(R.id.app_sign_in_bt);

        mImgBackgroud = (ImageView) findViewById(R.id.de_img_backgroud);
        mFrUserNameDelete = (FrameLayout) findViewById(R.id.fr_username_delete);
        mFrPasswordDelete = (FrameLayout) findViewById(R.id.fr_pass_delete);


        mSignInBt.setOnClickListener(this);

        drawable = mImgBackgroud.getDrawable();

//        下面的代码为 EditTextView 的展示以及背景动画
        mEditUserNameEt = new EditTextHolder(mUserNameEt, mFrUserNameDelete, null);
        mEditPassWordEt = new EditTextHolder(mPassWordEt, mFrPasswordDelete, null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(LoginActivity1.this, R.anim.translate_anim);
                mImgBackgroud.startAnimation(animation);
            }
        }, 200);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_sign_in_bt:
                if (dialog == null) {
                    dialog = ProgressDialog.show(this, null, getString(R.string.logining));
                    dialog.setCanceledOnTouchOutside(true);
                }
                initEvent();
                break;
        }
    }

    private void initEvent() {
        String account = mUserNameEt.getText().toString().trim();
        String password = mPassWordEt.getText().toString().trim();

        ConnService service = ServiceGenerator.createService(ConnService.class, account, password);
        Call<User> call = service.getUser();

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccess()) {
                    User user = response.body();
//                    ToastUtils.showShort(LoginActivity1.this, user.toString());

                    if (user != null) {
                        App.setUser(user);
                        String token = user.getResult().getToken();
                        connect(token);
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("roy", t.toString());
            }
        });
    }

    /**
     * 建立与融云服务器的链接
     *
     * @param token
     */
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(App.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                @Override
                public void onTokenIncorrect() {
                    Log.e("roy", "--onTokenIncorrect");
                }

                @Override
                public void onSuccess(String userid) {

                    Log.e("roy", "--onSuccess" + userid);
                    ToastUtils.showShort(LoginActivity1.this, "登录成功");
                    App.setIsLogin(true);
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    startActivity(new Intent(LoginActivity1.this, MainActivity.class));
                    RongCouldEvent.getInstance().setOtherListener();

                    LoginActivity1.this.finish();
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e("roy", "--onError" + errorCode.getValue());
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("roy", "onBackPressed");
        App.getInstance().exit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.KEYCODE_BACK == keyCode) {
            App.getInstance().exit();
        }
        return super.onKeyDown(keyCode, event);
    }
}

