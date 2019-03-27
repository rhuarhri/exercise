package com.example.rhuarhri.androidexerciseapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MessageHandler extends Thread {



    //protected Handler myHandler;

    String path = "";
    String message = "";
    Context appContext;
    Activity currentActivity;

    public MessageHandler(Context context, Activity activity, String Message)
    {
        appContext = context;
        currentActivity = activity;
        path = context.getString(R.string.path);
        message = Message;
/*
        myHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Bundle stuff = msg.getData();
                //messageText(stuff.getString("messageText"));
                return true;
            }
        });*/

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        Receiver messageReceiver = new Receiver();
        LocalBroadcastManager.getInstance(appContext).registerReceiver(messageReceiver, messageFilter);
    }


    public void run() {

        Task<List<Node>> wearableList =
                Wearable.getNodeClient(appContext).getConnectedNodes();
        try {

            List<Node> nodes = Tasks.await(wearableList);
            for (Node node : nodes) {
                Task<Integer> sendMessageTask =
                        Wearable.getMessageClient(currentActivity).sendMessage(node.getId(), path, message.getBytes());
                /*try {



                    Integer result = Tasks.await(sendMessageTask);


                } catch (ExecutionException exception) {



                } catch (InterruptedException exception) {



                }*/

            }

        } catch (ExecutionException exception) {



        } catch (InterruptedException exception) {


        }

    }


    public class Receiver extends BroadcastReceiver {
        @Override

        public void onReceive(Context context, Intent messageData) {

            /*
            A message that is received from another device comes from the
            MessageService and is stored in the intent.

            This is not used as activities that handle messages have their own receiver

             */

        }
    }

}
