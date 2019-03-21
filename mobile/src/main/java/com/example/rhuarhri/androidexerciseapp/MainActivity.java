package com.example.rhuarhri.androidexerciseapp;
/*
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rhuarhri.androidexerciseapp.appToWatch.MessageHandler;
import com.example.rhuarhri.androidexerciseapp.externalDatabase.storedExercises;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.PerformanceDBController;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.chosenExerciseDBAccess;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.chosenExercises;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.performanceDBAccess;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.storedUserPerformance;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    int performance = 0;

    Boolean getdata = false;

    Button talkbutton;
    Button addBTN;
    Button getBTN;
    TextView textview;
    protected Handler myHandler;
    int receivedMessageNumber = 1;
    int sentMessageNumber = 1;


    OneTimeWorkRequest getExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        talkbutton = findViewById(R.id.sendBTN);
        textview = findViewById(R.id.messageTXT);

        addBTN = (Button) findViewById(R.id.addBTN);
        getBTN = (Button) findViewById(R.id.getBTN);

        //Create a message handler//

        myHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Bundle stuff = msg.getData();
                messageText(stuff.getString("messageText"));
                return true;
            }
        });

//Register to receive local broadcasts, which we'll be creating in the next step//

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MainActivity.Receiver messageReceiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);

    }

    public void messageText(String newinfo) {
        if (!newinfo.isEmpty() || newinfo != "") {
            output(newinfo);

        }
    }

    //Define a nested class that extends BroadcastReceiver//

    public class Receiver extends BroadcastReceiver {
        @Override

        public void onReceive(Context context, Intent intent) {

            try{

                output(intent.getStringExtra("message"));


            }
            catch(Exception e)
            {

            }
        }
    }

    public void talkClick(View v) {

        new MessageHandler(getApplicationContext(), MainActivity.this,
                "" + "heart").start();
    }

    public void output(String result)
    {
        if (result.equals("0"))
        {

        }
        else if (result.equals("1"))
        {

        }
        else if (result.equals("2"))
        {

        }
        else
        {
            textview.setText(result);
        }
    }
}

    /*
    public void messageText(String newinfo) {
        if (newinfo.compareTo("") != 0) {

            performance = Integer.parseInt(newinfo);
            //textview.append("\n" + newinfo);

            textview.setText(""+ performance);
        }
    }

//Define a nested class that extends BroadcastReceiver//

    public class Receiver extends BroadcastReceiver {
        @Override

        public void onReceive(Context context, Intent intent) {

//Upon receiving each message from the wearable, display the following text//

            String message = "I just received a message from the wearable " + receivedMessageNumber++;

            try{
                message = intent.getStringExtra("message");
            }
            catch(Exception e)
            {

            }



            performance = Integer.parseInt(message);

            //textview.append("\n" + performance);

            textview.setText("" + performance);

        }
    }

    public void talkClick(View v) {
        String message = "Sending message.... ";
        //textview.setText(message);

//Sending a message can block the main UI thread, so use a new thread//

        //new NewThread("/my_path", message).start();
        //new MessageHandler(getApplicationContext(), MainActivity.this,  message).start();


        new MessageHandler(getApplicationContext(), MainActivity.this,
                "" + "heart").start();


    }

//Use a Bundle to encapsulate our message//

    public void sendmessage(String messageText) {
        Bundle bundle = new Bundle();
        bundle.putString("messageText", messageText);
        Message msg = myHandler.obtainMessage();
        msg.setData(bundle);
        myHandler.sendMessage(msg);

    }

    class NewThread extends Thread {
        String path;
        String message;

//Constructor for sending information to the Data Layer//

        NewThread(String p, String m) {
            path = p;
            message = m;
        }

        public void run() {

//Retrieve the connected devices, known as nodes//

            Task<List<Node>> wearableList =
                    Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();
            try {

                List<Node> nodes = Tasks.await(wearableList);
                for (Node node : nodes) {
                    Task<Integer> sendMessageTask =

//Send the message//

                            Wearable.getMessageClient(MainActivity.this).sendMessage(node.getId(), path, message.getBytes());

                    try {

//Block on a task and get the result synchronously//

                        Integer result = Tasks.await(sendMessageTask);
                        sendmessage("I just sent the wearable a message " + sentMessageNumber++);

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
    }
}
*/















    /*

    Button talkbutton;
    TextView textview;
    protected Handler myHandler;
    int receivedMessageNumber = 1;
    int sentMessageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        talkbutton = findViewById(R.id.sendBTN);
        textview = findViewById(R.id.messageTXT);

        //Create a message handler//

        myHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Bundle stuff = msg.getData();
                messageText(stuff.getString("messageText"));
                return true;
            }
        });

//Register to receive local broadcasts, which we'll be creating in the next step//

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MainActivity.Receiver messageReceiver = new MainActivity.Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);

    }

    public void messageText(String newinfo) {
        if (newinfo.compareTo("") != 0) {
            textview.append("\n" + newinfo);
        }
    }

//Define a nested class that extends BroadcastReceiver//

    public class Receiver extends BroadcastReceiver {
        @Override

        public void onReceive(Context context, Intent intent) {

//Upon receiving each message from the wearable, display the following text//

            String message = "I just received a message from the wearable " + receivedMessageNumber++;;

            textview.setText(message);

        }
    }

    public void talkClick(View v) {
        String message = "Sending message.... ";
        textview.setText(message);

//Sending a message can block the main UI thread, so use a new thread//

        new MainActivity.NewThread("/my_path", message).start();

    }

//Use a Bundle to encapsulate our message//

    public void sendmessage(String messageText) {
        Bundle bundle = new Bundle();
        bundle.putString("messageText", messageText);
        Message msg = myHandler.obtainMessage();
        msg.setData(bundle);
        myHandler.sendMessage(msg);

    }

    class NewThread extends Thread {
        String path;
        String message;

//Constructor for sending information to the Data Layer//

        NewThread(String p, String m) {
            path = p;
            message = m;
        }

        public void run() {

//Retrieve the connected devices, known as nodes//

            Task<List<Node>> wearableList =
                    Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();
            try {

                List<Node> nodes = Tasks.await(wearableList);
                for (Node node : nodes) {
                    Task<Integer> sendMessageTask =

//Send the message//

                            Wearable.getMessageClient(getApplicationContext()).sendMessage(node.getId(), path, message.getBytes());

                    try {

//Block on a task and get the result synchronously//

                        Integer result = Tasks.await(sendMessageTask);
                        sendmessage("I just sent the wearable a message " + sentMessageNumber++);

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
    }


}
*/