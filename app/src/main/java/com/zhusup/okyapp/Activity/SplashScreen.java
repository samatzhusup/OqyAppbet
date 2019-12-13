package com.zhusup.okyapp.Activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.zhusup.okyapp.R;

public class SplashScreen extends AppCompatActivity {
    ImageView image;
    Button btnn;
    Animation top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        image = (ImageView) findViewById(R.id.image);
        btnn = (Button) findViewById(R.id.start);
        top = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        image.setAnimation(top);

      btnn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i = new Intent(SplashScreen.this,LoginActivity.class);
              startActivity(i);
          }
      });

    }

}
