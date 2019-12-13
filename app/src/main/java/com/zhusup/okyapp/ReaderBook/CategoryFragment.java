package com.zhusup.okyapp.ReaderBook;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.zhusup.okyapp.Model.Category;
import com.zhusup.okyapp.R;
import com.zhusup.okyapp.ViewHolder.CategoryViewHolder;

import static android.media.CamcorderProfile.get;

public class CategoryFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference category;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);

        database= FirebaseDatabase.getInstance();
        category=database.getReference("Category");

        recycler_menu=(RecyclerView) v.findViewById(R.id.recyclerView);
        recycler_menu.setHasFixedSize(true);
        layoutManager= new GridLayoutManager(getContext(),2);
        recycler_menu.setLayoutManager(layoutManager);

        getActivity().setTitle("Category");

        if (isConnected()) {
            Toast.makeText(getContext(),"Welcome", Toast.LENGTH_SHORT).show();
        }else (buildDialog(getContext())).show();

        loadMenu();
        return v;
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        return netinfo !=null && netinfo.isConnected();
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return builder;
    }
    private void loadMenu() {
        FirebaseRecyclerOptions<Category> options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(category, Category.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            public void onBindViewHolder(@NonNull CategoryViewHolder menuViewHolder, @SuppressLint("RecyclerView") final int position,
                                         @NonNull Category category) {
                Picasso.with(getContext())
                        .load(category.getImage())
                        .into(menuViewHolder.imageView);
                menuViewHolder.textView.setText(category.getName());
//                final Category clickItem = category;
                menuViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent foodintent=new Intent(getContext(), BookList.class);
                        foodintent.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodintent);


                    }
                });
            }


            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
                return new CategoryViewHolder(view);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recycler_menu.setAdapter(adapter);
    }
}


