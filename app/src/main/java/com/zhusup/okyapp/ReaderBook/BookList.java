package com.zhusup.okyapp.ReaderBook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.zhusup.okyapp.Interface.ItemClickListener;
import com.zhusup.okyapp.Model.Book;
import com.zhusup.okyapp.R;
import com.zhusup.okyapp.ViewHolder.BookViewHolder;

public class BookList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference foodList;
    FirebaseRecyclerAdapter<Book, BookViewHolder> adapter;
    String categoryId = "";
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_list);

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
            ab.setTitle("");
            ab.setDisplayHomeAsUpEnabled(true);
        }
        //init firebase
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Book");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Toast.makeText(BookList.this,"hsds",Toast.LENGTH_SHORT).show();

        // get the position through intent of category id
        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null)
        {
                loadListFood(categoryId);
        }
    }
    private void loadListFood(String categoryId) {
        FirebaseRecyclerOptions<Book> options =
                new FirebaseRecyclerOptions.Builder<Book>().setQuery(foodList.orderByChild("menuId").equalTo(categoryId), Book.class).build();

        adapter = new FirebaseRecyclerAdapter<Book, BookViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final BookViewHolder foodViewHolder, int positon, @NonNull final Book book) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                Toolbar toolbar =(Toolbar) findViewById(R.id.back);
                toolbar.setTitle(book.getCategory());

                foodViewHolder.food_name.setText(book.getName());
                Picasso.with(getApplicationContext()).load(book.getImage()).into(foodViewHolder.food_image);

                isSaved(book.getName(),foodViewHolder.save);

                foodViewHolder.save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (foodViewHolder.save.getTag().equals("save")){
                            FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid())
                                    .child(book.getName()).setValue(true);
                        } else {
                            FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid())
                                    .child(book.getName()).removeValue();
                        }
                    }
                });

                foodViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // Start activity of food details
                        Intent foodDetails = new Intent(BookList.this, BookDetails.class);
                        foodDetails.putExtra("BookId", getRef(position).getKey()); //send BookId to new Activity
                        startActivity(foodDetails);
                    }
                });
            }



            @NonNull
            @Override
            public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
                return new BookViewHolder(view);
            }
        };
        //set Adapter
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void isSaved(final String menuId, final ImageView imageView){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Saves").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(menuId).exists()){
                    imageView.setImageResource(R.drawable.ic_bookmark);
                    imageView.setTag("saved");
                } else{
                    imageView.setImageResource(R.drawable.ic_date);
                    imageView.setTag("save");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
