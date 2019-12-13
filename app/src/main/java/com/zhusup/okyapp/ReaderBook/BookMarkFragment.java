package com.zhusup.okyapp.ReaderBook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zhusup.okyapp.Model.Book;
import com.zhusup.okyapp.R;
import com.zhusup.okyapp.ViewHolder.BookMarkAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookMarkFragment extends Fragment {

    private List<String> mySaves;
    FirebaseUser firebaseUser;
    private RecyclerView recyclerView_saves;
    private BookMarkAdapter myFotosAdapter_saves;
    private List<Book> postList_saves;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView_saves = view.findViewById(R.id.recyclerView);
        recyclerView_saves.setLayoutManager(new GridLayoutManager(getActivity(),2));
        postList_saves = new ArrayList<>();
        myFotosAdapter_saves = new BookMarkAdapter(getContext(), postList_saves);
        recyclerView_saves.setAdapter(myFotosAdapter_saves);

        getActivity().setTitle("Reader");
        mySaves();
        return view;
    }



    private void mySaves(){
        mySaves = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Saves").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    mySaves.add(snapshot.getKey());
                }
                readSaves();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void readSaves(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Book");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList_saves.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Book post = snapshot.getValue(Book.class);

                    for (String id : mySaves) {
                        if (post.getName().equals(id)) {
                            postList_saves.add(post);
                        }
                    }
                }
                myFotosAdapter_saves.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}


