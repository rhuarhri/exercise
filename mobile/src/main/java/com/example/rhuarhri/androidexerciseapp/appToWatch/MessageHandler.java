package com.example.rhuarhri.androidexerciseapp.appToWatch;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;

import com.example.rhuarhri.androidexerciseapp.R;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MessageHandler extends Thread {

    /*
    How the api works
    The API assumes that the device it is running on is connected to multiple devices
    it goes to each device that is connected to the device it is on.
    What it is looking for is an app running on a connected device that has a key
    that it is looking for.
     */

    /*
    The path identifies the message and allows the message to be found when sent.
    All paths values must start with a / for example /my_path
     */
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

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageHandler.Receiver messageReceiver = new MessageHandler.Receiver();
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
                    //sendmessage("I just sent the wearable a message " + sentMessageNumber++);



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
