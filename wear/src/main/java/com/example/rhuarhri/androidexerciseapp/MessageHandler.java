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

    /*
    How the api works
    The API assumes that the device it is running on is connected to multiple devices


     */



    protected Handler myHandler;
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

        myHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Bundle stuff = msg.getData();
                //messageText(stuff.getString("messageText"));
                return true;
            }
        });

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        Receiver messageReceiver = new Receiver();
        LocalBroadcastManager.getInstance(appContext).registerReceiver(messageReceiver, messageFilter);
    }

    /*
    public void sendmessage(String messageText) {
        Bundle bundle = new Bundle();
        bundle.putString("messageText", messageText);
        Message msg = myHandler.obtainMessage();
        msg.setData(bundle);
        myHandler.sendMessage(msg);

    }*/

    /*
    public void SendMessage(String Path, String Message)
    {

        path = Path;
        message = Message;

    }*/

    /*
    public void watchSendMessage()
    {

    }*/


    public void run() {

//Retrieve the connected devices, known as nodes//

        Task<List<Node>> wearableList =
                Wearable.getNodeClient(appContext).getConnectedNodes();
        try {

            List<Node> nodes = Tasks.await(wearableList);
            for (Node node : nodes) {
                Task<Integer> sendMessageTask =

//Send the message//

                        Wearable.getMessageClient(currentActivity).sendMessage(node.getId(), path, message.getBytes());

                try {

//Block on a task and get the result synchronously//

                    Integer result = Tasks.await(sendMessageTask);
                    //sendmessage("I just sent the wearable a message " + sentMessageNumber++);

                    //if the Task fails, then…..//

                } catch (ExecutionException exception) {

                    //TO DO: Handle the exception//

                } catch (InterruptedException exception) {

                    //TO DO: Handle the exception//

                }

            }

        } catch (ExecutionException exception) {

            //TO DO: Handle the exception//

        } catch (InterruptedException exception) {

            //TO DO: Handle the exception//
        }

    }


    public class Receiver extends BroadcastReceiver {
        @Override

        public void onReceive(Context context, Intent messageData) {

            /*
            A message that is received from another device comes from the
            MessageService and is stored in the intent.
             */





        }
    }

}
