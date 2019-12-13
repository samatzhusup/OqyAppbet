package com.zhusup.okyapp.ReaderBook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.zhusup.okyapp.Model.Book;
import com.zhusup.okyapp.R;


public class BookDetails extends AppCompatActivity {

    TextView food_name, food_description;
    ImageView food_image;

    String foodId = "";
    Book currentFood;


    FirebaseDatabase database;
    DatabaseReference foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Book Detail");
            ab.setDisplayHomeAsUpEnabled(true);
        }
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Book");
        //init view
        food_name = findViewById(R.id.post_titlee);
        food_image = findViewById(R.id.post_imagee);
        food_description = findViewById(R.id.post_desc);

        // get food id from intent
        if (getIntent() != null)
            foodId = getIntent().getStringExtra("BookId");
        if (!foodId.isEmpty() && foodId != null) {
            getDetailFood(foodId);
        }
    }
    private void getDetailFood(String foodId) {

        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Book.class);
                // setting the image from firebase into appbar;
                Picasso.with(getApplicationContext()).load(currentFood.getImage()).into(food_image);
                //set title in appbar
                food_description.setText(currentFood.getDescription());
                food_name.setText(currentFood.getName());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
