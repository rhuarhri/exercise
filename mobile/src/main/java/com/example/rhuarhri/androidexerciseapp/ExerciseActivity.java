package com.example.rhuarhri.androidexerciseapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ExerciseActivity extends AppCompatActivity {

        protected Handler messageHandler;
        ImageView exerciseIV;
        ImageView notificationIV;
        ProgressBar exerciseProgress;
        exercisePause pauseFragement;
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        int performance = 0;
        int minPerformance = 1;
        int maxPerformance = 5;

        boolean fragmentDisplayed = false;
        boolean Displayed = false;
        boolean hidden = false;
        MediaPlayer goodJobAudio;
        MediaPlayer tooSlowAudio;
        MediaPlayer tooFastAudio;

        long time = 120000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        //Toast.makeText(this, "On this part you don't to do anything just watch", Toast.LENGTH_LONG).show();

        exerciseIV = (ImageView) findViewById(R.id.exerciseIV);
        notificationIV = (ImageView) findViewById(R.id.notificationIV);
        exerciseProgress = (ProgressBar) findViewById(R.id.exercisePB);

        exerciseProgress.setMax((int) time);

        fragmentManager = getSupportFragmentManager();
        pauseFragement = (exercisePause) fragmentManager.findFragmentById(R.id.pauseFRG);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(pauseFragement);
        fragmentTransaction.commit();


        exerciseIV.setImageResource(R.drawable.human_figure_down);
        notificationIV.setImageResource(R.drawable.too_slow);

        //Create a message handler//

        messageHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
                Bundle stuff = msg.getData();
                messageText(stuff.getString("messageText"));
                return true;
            }
        });

        //Register to receive local broadcasts, which we'll be creating in the next step//

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        ExerciseActivity.Receiver messageReceiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);






        mediaSetUp();
}


    public void messageText(String newinfo) {
        if (newinfo.compareTo("") != 0) {
            performance = Integer.parseInt(newinfo);
        }
    }

    //Define a nested class that extends BroadcastReceiver//

    public class Receiver extends BroadcastReceiver {
        @Override

        public void onReceive(Context context, Intent intent) {

//Upon receiving each message from the wearable, display the following text//

            //String message = "I just received a message from the wearable " + receivedMessageNumber++;



            try{

                performance = Integer.parseInt(intent.getStringExtra("message"));
            }
            catch(Exception e)
            {

            }





        }
    }


/*
//Use a Bundle to encapsulate our message//

        public void sendmessage(String messageText) {
            Bundle bundle = new Bundle();
            bundle.putString("messageText", messageText);
            Message msg = messageHandler.obtainMessage();
            msg.setData(bundle);
            messageHandler.sendMessage(msg);

        }*/


        private void mediaSetUp()
        {
                goodJobAudio = MediaPlayer.create(getApplicationContext(), R.raw.good_job);
                tooSlowAudio = MediaPlayer.create(getApplicationContext(), R.raw.speed_up);
                tooFastAudio = MediaPlayer.create(getApplicationContext(), R.raw.slow_down);
        }

        private void performanceChangeHandler()
        {
            if (performance == 0)
            {
                if (pauseFragement.isHidden() == true) {
                    fragmentTransaction.show(pauseFragement);
                    fragmentTransaction.commit();
                }
            }
            else
            {
                if (pauseFragement.isHidden() == false) {
                    fragmentTransaction.hide(pauseFragement);
                    fragmentTransaction.commit();
                }
            }

            if (performance > minPerformance && performance < maxPerformance)
            {
                notificationIV.setImageResource(R.drawable.great_work);
                //goodJobAudio.start();

            }
            else if (performance < minPerformance)
            {
                notificationIV.setImageResource(R.drawable.too_slow);
                //tooSlowAudio.start();
            }
            else if (performance > maxPerformance)
            {
                notificationIV.setImageResource(R.drawable.too_fast);
                //tooFastAudio.start();
            }
        }
}

        //This is all a part of user testing
        /*CountDownTimer exerciseTimer = new CountDownTimer(time, 2000) {
@Override
public void onTick(long l) {

        if(l > 110000)
        {
        Displayed = true;
        }
        else
        {
        if(Displayed == true)
        {
        fragmentTransaction.hide(pauseFragement);
        fragmentTransaction.commit();
        Displayed = false;
        }

        exerciseProgress.setProgress(((int) time -(int) l));

        exerciseDisplay();
        iconIteration++;
        if (iconIteration == 10)
        {
        //every 20 seconds
        notificationDisplay();
        iconIteration = 0;
        }

        }



        }

@Override
public void onFinish() {
        Intent completeScreen = new Intent(getApplicationContext(), completeActivity.class);

        startActivity(completeScreen);
        }
        }.start();




        }

private void notificationDisplay()
        {
        if (notificationIteration == 0)
        {
        //Toast.makeText(this, "You are exercising too slowly", Toast.LENGTH_LONG).show();

        notificationIV.setImageResource(R.drawable.too_slow);
        tooSlowAudio.start();

        notificationIteration++;
        }
        else if (notificationIteration == 1)
        {
        //Toast.makeText(this, "You are exercising correctly", Toast.LENGTH_LONG).show();

        notificationIV.setImageResource(R.drawable.great_work);
        goodJobAudio.start();

        notificationIteration++;
        }
        else if (notificationIteration == 2)
        {
        notificationIteration = 1;
        //Toast.makeText(this, "You are exercising too fast", Toast.LENGTH_SHORT).show();
        notificationIV.setImageResource(R.drawable.too_fast);
        tooFastAudio.start();
        }
        else{
        notificationIteration = 0;
        }


        }


private void exerciseDisplay()
        {
        if (iteration == 0)
        {
        exerciseIV.setImageResource(R.drawable.human_figure_down);
        iteration++;
        }
        else
        {
        iteration = 0;
        exerciseIV.setImageResource(R.drawable.human_figure_up);
        }


        }

private void pauseDisplay()
        {
        if (fragmentDisplayed == true)
        {
        if (pauseFragement.isHidden() == true)
        {
        if (Displayed == false){
        fragmentTransaction.show(pauseFragement);
        fragmentTransaction.commit();
        Displayed = true;}
        }


        }
        else
        {


        if (pauseFragement.isHidden() == false)
        {
        if(hidden == false){
        fragmentTransaction.hide(pauseFragement);
        fragmentTransaction.commit();
        hidden = true;}
        }


        }

        }

*/
