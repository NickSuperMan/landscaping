package com.zua.landscaping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.User;
import com.zua.landscaping.utils.ConnService;
import com.zua.landscaping.utils.ServiceGenerator;
import com.zua.landscaping.utils.TitleBuilder;
import com.zua.landscaping.utils.ToastUtils;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by roy on 4/23/16.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText user_account, user_password;

    private ImageView imageView;

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        App.getInstance().addActivity(this);

//        new TitleBuilder(this).setTitleText(getString(R.string.login)).build();


        initView();


        getGroupList();
    }

    private void getGroupList() {

    }


    private void initView() {
        user_account = (EditText) findViewById(R.id.user_account);
        user_password = (EditText) findViewById(R.id.user_password);

        login = (Button) findViewById(R.id.user_login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        initEvent();


    }

    private void initEvent() {
        String account = user_account.getText().toString().trim();
        String password = user_password.getText().toString().trim();

        ConnService service = ServiceGenerator.createService(ConnService.class, account, password);
        Call<User> call = service.getUser();

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccess()) {
                    User user = response.body();
                    ToastUtils.showShort(LoginActivity.this, user.toString());

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
                    ToastUtils.showShort(LoginActivity.this, "登录成功");
                    App.setIsLogin(true);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    finish();
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e("roy", "--onError" + errorCode);
                }
            });
        }
    }



}

