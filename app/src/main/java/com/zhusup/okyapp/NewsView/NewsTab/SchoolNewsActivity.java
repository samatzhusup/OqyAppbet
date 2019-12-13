package com.zhusup.okyapp.NewsView.NewsTab;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.zhusup.okyapp.NewsView.GlobalNewsDet.News;
import com.zhusup.okyapp.NewsView.GlobalNewsDet.NewsDetailView;
import com.zhusup.okyapp.R;


public class SchoolNewsActivity extends Fragment {

    private RecyclerView mPeopleRV;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<News, GlobalNewsActivity.NewsViewHolder> mPeopleRVAdapter;

    FirebaseRecyclerOptions<News> options;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_news, container, false);
        setHasOptionsMenu(true);
        //"News" here will reflect what you have called your database in Firebase.
        mDatabase = FirebaseDatabase.getInstance().getReference().child("SchoolNews");
        mDatabase.keepSynced(true);

        mPeopleRV = (RecyclerView) view.findViewById(R.id.myRecycleView);

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("SchoolNews");
        Query personsQuery = personsRef.orderByKey();

        mPeopleRV.hasFixedSize();


        options = new FirebaseRecyclerOptions.Builder<News>().setQuery(personsQuery, News.class).build();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<News, GlobalNewsActivity.NewsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(GlobalNewsActivity.NewsViewHolder holder, final int position, final News model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.setImage(getActivity(), model.getImage());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String MTitle = getItem(position).getTitle();
                        String MDesc = getItem(position).getDesc();
                        String mImage= getItem(position).getImage();

                        Intent intent = new Intent(getActivity(), NewsDetailView.class);
                        intent.putExtra("title",MTitle);
                        intent.putExtra("desc",MDesc);
                        intent.putExtra("image",mImage);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public GlobalNewsActivity.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.news_item, parent, false);

                return new GlobalNewsActivity.NewsViewHolder(view);
            }
        };

        mPeopleRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mPeopleRVAdapter.startListening();
        mPeopleRV.setAdapter(mPeopleRVAdapter);

        return view;
    }

    //Search function
    public void searchFire(String searchText)
    {
        String query = searchText.toLowerCase();

        Query myQuery = mDatabase.orderByChild("search")
                .startAt(query).endAt(query + "\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<News>().setQuery(myQuery, News.class).build();

        mPeopleRVAdapter = new FirebaseRecyclerAdapter<News, GlobalNewsActivity.NewsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(GlobalNewsActivity.NewsViewHolder holder, final int position, final News model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.setImage(getActivity(), model.getImage());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String MTitle = getItem(position).getTitle();
                        String MDesc = getItem(position).getDesc();
                        String mImage= getItem(position).getImage();

                        Intent intent = new Intent(getActivity(), NewsDetailView.class);
                        intent.putExtra("title",MTitle);
                        intent.putExtra("desc",MDesc);
                        intent.putExtra("image",mImage);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public GlobalNewsActivity.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.news_item, parent, false);

                return new GlobalNewsActivity.NewsViewHolder(view);
            }
        };

        mPeopleRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mPeopleRVAdapter.startListening();
        mPeopleRV.setAdapter(mPeopleRVAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mPeopleRVAdapter !=null){
            mPeopleRVAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mPeopleRVAdapter.stopListening();
    }


    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public NewsViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setTitle(String title){
            TextView post_title = (TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }
        public void setDesc(String desc){
            TextView post_desc = (TextView)mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }
        public void setImage(final Context ctx, final String image){
            final ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(post_image, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {
                    Picasso.with(ctx).load(image).into(post_image);
                }
            });
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_news, menu);
        MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFire(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFire(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            //TODO
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

