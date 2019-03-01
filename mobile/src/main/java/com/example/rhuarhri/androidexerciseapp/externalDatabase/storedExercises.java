package com.example.rhuarhri.androidexerciseapp.externalDatabase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.example.rhuarhri.androidexerciseapp.recyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class storedExercises {

    FirebaseFirestore db;

    List<exercise> existingExercises = new ArrayList<>();

    public storedExercises()
    {
        db = FirebaseFirestore.getInstance();
    }

    public void getAllExercises(final RecyclerView QuizRV, final Context context) throws Exception
    {

        try {
                db.collection("exercises").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        exercise currentExercise = document.toObject(exercise.class);
                                        if (currentExercise != null) {
                                            existingExercises.add(currentExercise);
                                        }
                                    }

                                    RecyclerView.Adapter quizListAdapter = new recyclerViewAdapter(existingExercises, context);

                                    QuizRV.setAdapter(quizListAdapter);
                                }
                            }
                        });
                /**/


            } catch (Exception e) {

            }

    }
}
