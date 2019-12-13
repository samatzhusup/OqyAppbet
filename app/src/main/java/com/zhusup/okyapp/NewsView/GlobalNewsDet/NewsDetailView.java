package com.zhusup.okyapp.NewsView.GlobalNewsDet;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhusup.okyapp.R;

public class NewsDetailView extends AppCompatActivity {
    TextView mTitle,mDesc;
    ImageView mImage;
    Context ctx;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_news_detail);

            Toolbar toolbar = (Toolbar) findViewById(R.id.back);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setTitle(R.string.Post_Detail);
                ab.setDisplayHomeAsUpEnabled(true);
            }


            mTitle = findViewById(R.id.post_titlee);
            mDesc = findViewById(R.id.post_desc);
            mImage = findViewById(R.id.post_imagee);

            String title =getIntent().getStringExtra("title");
            String desc =getIntent().getStringExtra("desc");
            String imag = getIntent().getStringExtra("image");

            mTitle.setText(title);
            mDesc.setText(desc);
            Picasso.with(ctx).load(imag).into(mImage);

        }
}
