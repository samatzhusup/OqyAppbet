package com.zhusup.okyapp.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zhusup.okyapp.Model.Book;
import com.zhusup.okyapp.R;
import com.zhusup.okyapp.ReaderBook.BookMarkFragment;
import com.zhusup.okyapp.ReaderBook.pdf;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Book> mPosts;
    private FirebaseUser fireuser;

    public BookMarkAdapter(Context context, List<Book> posts){
        mContext = context;
        mPosts = posts;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.bookmark_list_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {
        fireuser = FirebaseAuth.getInstance().getCurrentUser();
        final Book post = mPosts.get(position);

        Glide.with(mContext).load(post.getImage()).into(holder.post_image);
        holder.name.setText(post.getName());
        holder.desc.setText(post.getDescription());

        isSaved(post.getName(),holder.save);

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(fireuser.getUid())
                            .child(post.getName()).removeValue()
                    ;
                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.frame_layoutt,
                        new BookMarkFragment()).commit();
            }
        });

        holder.post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                Intent book = new Intent(view.getContext(), pdf.class);
                book.putExtra("link",post.getLink());
                book.putExtra("title",post.getName());
                mContext.startActivity(book);
            }
        });
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
                    imageView.setTag("save");
                } else{
                    imageView.setImageResource(R.drawable.ic_date);
                    imageView.setTag("saved");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView post_image,save;
        public TextView name,desc;

        public ImageViewHolder(View itemView) {
            super(itemView);

            save = itemView.findViewById(R.id.bookmark);
            post_image = itemView.findViewById(R.id.post_image);
            name=itemView.findViewById(R.id.titleBookName);
            desc=itemView.findViewById(R.id.titleBookDesc);
        }
    }
}