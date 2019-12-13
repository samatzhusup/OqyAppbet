package com.zhusup.okyapp.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zhusup.okyapp.Interface.ItemClickListener;
import com.zhusup.okyapp.Model.Book;
import com.zhusup.okyapp.R;

import java.util.List;

public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView food_name;
    public ImageView food_image,save;
    private ItemClickListener itemClickListener;
    private Context mcontext;
    private List<Book> mBook;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public BookViewHolder(View itemView, Context mcontext, List<Book> mBook) {
        super(itemView);
        this.mcontext = mcontext;
        this.mBook = mBook;
    }

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);

        save = itemView.findViewById(R.id.bookmark);
        food_image = itemView.findViewById(R.id.post_image);
        food_name = itemView.findViewById(R.id.titleBookName);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

}
