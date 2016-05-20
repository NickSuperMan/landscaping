package com.zua.landscaping.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zua.landscaping.activity.MainActivity;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ContactNotificationMessage;

/**
 * Created by roy on 16/5/18.
 */
public class RongCouldEvent implements RongIMClient.OnReceiveMessageListener, RongIM.OnSendMessageListener , RongIMClient.ConnectionStatusListener{

    private static RongCouldEvent mRongCloudInstance;
    private Context mContext;

    public static void init(Context context) {
        if (mRongCloudInstance == null) {
            synchronized (RongCouldEvent.class) {
                mRongCloudInstance = new RongCouldEvent(context);
            }
        }
    }

    public static RongCouldEvent getInstance() {
        return mRongCloudInstance;
    }

    private RongCouldEvent(Context context) {
        mContext = context;
    }


    public void setOtherListener(){
        RongIM.getInstance().getRongIMClient().setOnReceiveMessageListener(this);
        RongIM.getInstance().getRongIMClient().setConnectionStatusListener(this);
    }

    @Override
    public boolean onReceived(Message message, int i) {

        MessageContent messageContent = message.getContent();
        if (messageContent instanceof ContactNotificationMessage){
            ContactNotificationMessage contactContentMessage = (ContactNotificationMessage) messageContent;
            Log.d("roy", "onReceived-ContactNotificationMessage:getExtra;" + contactContentMessage.getExtra());
            Log.d("roy", "onReceived-ContactNotificationMessage:+getmessage:" + contactContentMessage.getMessage().toString());
            Intent in = new Intent();
            in.setAction(MainActivity.ACTION_DMEO_RECEIVE_MESSAGE);
            in.putExtra("rongCloud", contactContentMessage);
            in.putExtra("has_message", true);
            mContext.sendBroadcast(in);
        }
        return false;
    }

    @Override
    public Message onSend(Message message) {
        return null;
    }

    @Override
    public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
        return false;
    }

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {

    }
}
