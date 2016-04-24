package xzy.zzia.com.landscaping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import xzy.zzia.com.landscaping.R;
import xzy.zzia.com.landscaping.utils.TitleBuilder;


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

        new TitleBuilder(this).setLeftImage(android.R.drawable.ic_menu_delete).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setTitleText(getString(R.string.login)).build();


        initView();
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
        finish();

    }

    private void initEvent() {
        String account = user_account.getText().toString().trim();
        String password = user_password.getText().toString().trim();
    }
}
