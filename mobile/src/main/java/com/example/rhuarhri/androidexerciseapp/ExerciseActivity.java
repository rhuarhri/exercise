package com.example.rhuarhri.androidexerciseapp;

import android.arch.persistence.room.Room;
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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rhuarhri.androidexerciseapp.internalDatabase.PerformanceDBController;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.chosenExerciseDBAccess;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.chosenExerciseDBController;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.chosenExercises;

import java.util.ArrayList;
import java.util.List;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class ExerciseActivity extends AppCompatActivity {


    //TODO calculate time for the exercises

        protected Handler messageHandler;
        signalLossTimer checkSignal = new signalLossTimer();
        boolean checkingForSignal = true;
        ImageView exerciseIV;
        ImageView notificationIV;
        ProgressBar exerciseProgress;
        exercisePause pauseFragement;
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        int currentExerciseLocation = 0;
        int performance = 0;
        //int oldPerformance;
        boolean performanceUpdated = true;
        int minPerformance = 1;
        int maxPerformance = 5;

        boolean fragmentDisplayed = false;
        boolean Displayed = false;
        boolean hidden = false;
        MediaPlayer goodJobAudio;
        MediaPlayer tooSlowAudio;
        MediaPlayer tooFastAudio;

        int displayNotifications = 0;

        long time = 0;

    OneTimeWorkRequest addExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        exerciseIV = (ImageView) findViewById(R.id.exerciseIV);
        notificationIV = (ImageView) findViewById(R.id.notificationIV);
        exerciseProgress = (ProgressBar) findViewById(R.id.exercisePB);



        fragmentManager = getSupportFragmentManager();
        pauseFragement = (exercisePause) fragmentManager.findFragmentById(R.id.pauseFRG);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(pauseFragement);
        fragmentTransaction.commit();

        exerciseIV.setImageResource(R.drawable.human_figure_down);
        notificationIV.setImageResource(R.drawable.too_slow);

        //Message handler

        messageHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
                Bundle stuff = msg.getData();
                messageText(stuff.getString("messageText"));
                return true;
            }
        });

        //Register to receive local broadcasts

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        ExerciseActivity.Receiver messageReceiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);

        mediaSetUp();


        getExercises();

        //Log.d("MESSAGE", "Time is " + time);

        exerciseTime(time);


        checkSignal.start();

}


    public void messageText(String newinfo) {
        if (!newinfo.isEmpty() || newinfo != "") {
            performance = Integer.parseInt(newinfo);
            performanceUpdated = true;
            //Log.d("RECEIVED PERFORMANCE", "current performance is " + performance);
            //performanceChangeHandler();
        }
    }

    //Define a nested class that extends BroadcastReceiver//

    public class Receiver extends BroadcastReceiver {
        @Override

        public void onReceive(Context context, Intent intent) {

            try{

                performance = Integer.parseInt(intent.getStringExtra("message"));
                performanceUpdated = true;
                //Log.d("RECEIVED PERFORMANCE", "received performance is " + performance);
                performanceChangeHandler();
            }
            catch(Exception e)
            {

            }
        }
    }



        private void mediaSetUp()
        {
                goodJobAudio = MediaPlayer.create(getApplicationContext(), R.raw.good_job);
                tooSlowAudio = MediaPlayer.create(getApplicationContext(), R.raw.speed_up);
                tooFastAudio = MediaPlayer.create(getApplicationContext(), R.raw.slow_down);
        }


        private void getExercises()
        {
            //get exercise routine
            chosenExerciseDBAccess ExerciseDB = Room.databaseBuilder(getApplicationContext(), chosenExerciseDBAccess.class, "ExerciseData")
                    .allowMainThreadQueries().build();
            List<chosenExercises> allExercises;

            allExercises = ExerciseDB.storedExercises().getAll();


            long exerciseTime = 0;

            for (int i = 0; i < allExercises.size(); i++)
            {
                chosenExercises nextExercise = allExercises.get(i);

                exerciseTime += nextExercise.getTime();

                minPerformance = nextExercise.getMinimumPerformanceLevel();

                maxPerformance = nextExercise.getMaximumPerformanceLevel();

                String type = nextExercise.getExerciseType();

                Data threadData = new Data.Builder().putString("function", type)
                        .build();


                addExercise = new OneTimeWorkRequest.Builder(PerformanceDBController.class)
                        .setInputData(threadData).build();


                WorkManager.getInstance().enqueue(addExercise);



            }

            time = exerciseTime;
            Log.d("MESSAGE", "Time is " + time);


        }

        private void performanceChangeHandler()
        {
            int newPerformance = performance;
            //int OldPerformance = oldPerformance;

/*
            if (newPerformance == OldPerformance) {

            }
            else {*/

                Log.d("Performance", "performance is "+newPerformance);
                if (newPerformance <= 0) {
                    if (pauseFragement.isHidden() == true) {
                        fragmentTransaction.show(pauseFragement);
                        fragmentTransaction.commitNow();
                        displayNotifications = 0;
                    }
                } else {
                    if (pauseFragement.isHidden() == false) {

                        //TODO find a way to hide the fragment when the performance changes
                        fragmentTransaction.hide(pauseFragement);
                        fragmentTransaction.commitNow();

                    }
                }

                if (displayNotifications >= 10)
                {
                    //is ensures that the notifications only change about once every
                    //5 seconds
            if (performance > minPerformance && performance < maxPerformance) {
                notificationIV.setImageResource(R.drawable.great_work);
                goodJobAudio.start();

            } else if (performance < minPerformance) {
                notificationIV.setImageResource(R.drawable.too_slow);
                tooSlowAudio.start();
            } else if (performance > maxPerformance) {
                notificationIV.setImageResource(R.drawable.too_fast);
                tooFastAudio.start();
            }

            displayNotifications = 0;

            }
            else{
                    displayNotifications++;
                }

            //oldPerformance = newPerformance;
        }


        public class signalLossTimer extends Thread
        {
            /*
            This class is used to pause the app if the connection with the smart watch app is lost.
            The smart watch should be constantly transmitting data and if there has been no data
            from the smart watch in a 5 second period then the connection can be considered as lost.
             */

            @Override
            public void run() {

                while (checkingForSignal == true)
                {
                    try {


                        if (performanceUpdated == true) {
                            performanceUpdated = false;
                        } else {
                            if(performance > 0) {
                                performance = 0;
                                performanceChangeHandler();
                            }

                        }

                        //wait 5 seconds
                        Thread.sleep(5000);
                    }
                    catch (Exception e)
                    {

                    }
                }



            }
        }

        boolean displayPositionOne = true;

        int pauseTime = 0;

        private void changeImage()
        {

            if (performance >= maxPerformance) {
                if (displayPositionOne == true) {
                    exerciseIV.setImageResource(R.drawable.human_figure_down);
                    displayPositionOne = false;
                } else {
                    exerciseIV.setImageResource(R.drawable.human_figure_up);
                    displayPositionOne = true;
                }
            }
            else {
                if (pauseTime == (maxPerformance - performance))
                {
                    if (displayPositionOne == true) {
                        exerciseIV.setImageResource(R.drawable.human_figure_down);
                        displayPositionOne = false;
                    } else {
                        exerciseIV.setImageResource(R.drawable.human_figure_up);
                        displayPositionOne = true;
                    }
                    pauseTime = 0;
                }
                else if (performance == 0)
                {
                    //do nothing

                }
                else {

                    pauseTime++;

                }
            }


        }

        private void exerciseTime(long Time)
        {
            exerciseProgress.setMax((int) Time);

            CountDownTimer exerciseTimer = new CountDownTimer(Time, 500) {
                @Override
                public void onTick(long l) {




                    exerciseProgress.setProgress(((int) Time -(int) l));

                    changeImage();





                }

                @Override
                public void onFinish() {
                    Intent completeScreen = new Intent(getApplicationContext(), completeActivity.class);

                    startActivity(completeScreen);
                }
            }.start();




        }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        OneTimeWorkRequest removeExerciseRoutine;

        Data threadData = new Data.Builder().putString("function", "end")
                .build();


        removeExerciseRoutine = new OneTimeWorkRequest.Builder(chosenExerciseDBController.class)
                .setInputData(threadData).build();


        WorkManager.getInstance().enqueue(removeExerciseRoutine);
    }
}

/**PAST CODE **/
/*
The code below was meant to display a series of images for a period of time.
It would have been used if it worked.

        This idea did not really work as the images could not be updated within a thread

        //List<Integer> images = new ArrayList<Integer>();
        //images.add(R.drawable.human_figure_down);
        //images.add(R.drawable.human_figure_up);
        //exerciseTimer timer = new exerciseTimer(10, 1000, images);

        //timer.start();

        public class exerciseTimer extends Thread{

            private int Time;
            private long Intervals;
            private List<Integer> ImageName;
            private int imageLocation = 0;


            public exerciseTimer(int time, long intervals, List<Integer> imageResources)
            {

                Time = time;
                Intervals = intervals;
                ImageName = imageResources;

            }

            public void run()
            {
                for (int i = 0; i < Time; i++)
                {
                    try {
                        Thread.sleep(Intervals);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (imageLocation < ImageName.size())
                    {
                        Log.d("IMAGE FLIP TEST", "location is " + imageLocation);
                        //TODO most likely will need a list of bitmaps which are the exercise images
                        flipBetweenImages();
                        imageLocation++;
                    }
                    else{
                        imageLocation = 0;
                    }


                }
            }
        }

    @Override
    protected void onDestroy() {
            //stop thread just in case
            checkingForSignal = false;
    }

    //image test
    boolean test = false;
    public void flipBetweenImages()
    {

        if (test = false) {
            notificationIV.setImageResource(R.drawable.chest);
            test = true;
        }
        else
        {
            notificationIV.setImageResource(R.drawable.arm);
            test = false;
        }

    }


 */


