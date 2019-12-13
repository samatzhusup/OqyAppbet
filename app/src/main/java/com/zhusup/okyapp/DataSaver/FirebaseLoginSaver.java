package com.zhusup.okyapp.DataSaver;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zhusup.okyapp.Activity.MainActivity;

public class FirebaseLoginSaver extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser !=null){
            startActivity(new Intent(FirebaseLoginSaver.this, MainActivity.class));
        }
    }
}
