package com.zhusup.okyapp.Activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.zhusup.okyapp.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class TimeTableActivity extends Fragment {
    private static final String TAG = "TimeTableActivity";
    private DatabaseReference reference;
    private ImageView imageView;
    Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.activity_timetable, container, false);

        Log.d(TAG,"onCreate: started");

        imageView = (ImageView) view.findViewById(R.id.timetableimage);
        PhotoViewAttacher photoViewAttacher =new PhotoViewAttacher(imageView);

        reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);

        getActivity().setTitle("Schedule");

        Query query = reference.child("time7grade");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String url = dataSnapshot.child("url").getValue().toString();

                Log.d(TAG,"onDataChange: url : "+ url);

                if(!url.isEmpty()){
                    Picasso.with(ctx).load(url).into(imageView);
                }
                Picasso.with(ctx).load(url).networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError() {
                        Picasso.with(ctx).load(url).into(imageView);
                    }
                });
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}
