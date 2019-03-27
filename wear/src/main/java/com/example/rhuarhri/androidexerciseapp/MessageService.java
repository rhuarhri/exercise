package com.example.rhuarhri.androidexerciseapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.content.Intent;
import com.google.android.gms.wearable.MessageEvent;
import android.support.v4.content.LocalBroadcastManager;
import com.google.android.gms.wearable.WearableListenerService;

public class MessageService extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals(getApplicationContext().getString(R.string.path))) {

            final String message = new String(messageEvent.getData());
            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("message", message);

            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        }
        else {
            super.onMessageReceived(messageEvent);
        }
    }
}