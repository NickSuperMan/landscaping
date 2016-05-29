package com.zua.landscaping.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.zua.landscaping.R;
import com.zua.landscaping.app.App;
import com.zua.landscaping.bean.User;
import com.zua.landscaping.utils.TitleBuilder;

import java.util.Locale;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;


/**
 * Created by roy on 4/25/16.
 */
public class ConversationActivity extends FragmentActivity {

    private String mTargetId;

    private String mTargetIds;

    private Conversation.ConversationType mConversationType;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        App.getInstance().addActivity(this);

        Intent intent = getIntent();

        getIntentDate(intent);
    }

    private void getIntentDate(Intent intent) {
        mTargetId = intent.getData().getQueryParameter("targetId");
        mTargetIds = intent.getData().getQueryParameter("targetIds");

        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));

        if (mConversationType.equals(Conversation.ConversationType.PRIVATE)) {

            for (User user : App.getUser().getFriends()) {
                if ((user.getUserId() + "").equals(mTargetId)) {
                    userName = user.getUserName();
                }
            }
        } else {
            userName = intent.getData().getQueryParameter("title");
        }


        new TitleBuilder(this).setLeftImage(R.drawable.back).setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setTitleText(userName).build();

        enterFragment(mConversationType, mTargetId);
    }

    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);
    }
}
