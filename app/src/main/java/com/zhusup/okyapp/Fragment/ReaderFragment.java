package com.zhusup.okyapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zhusup.okyapp.Activity.TimeTableActivity;
import com.zhusup.okyapp.R;
import com.zhusup.okyapp.ReaderBook.BookMarkFragment;
import com.zhusup.okyapp.ReaderBook.CategoryFragment;

public class ReaderFragment extends Fragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_bottdrawer, container, false);

        BottomNavigationView navigationView = v.findViewById(R.id.bottom_navigation_view);
        navigationView.setOnNavigationItemSelectedListener(navlistener);

        getFragmentManager().beginTransaction().replace(R.id.frame_layoutt, new BookMarkFragment()).commit();
        return v;

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedfragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_bookmark:
                            item.setTitle("BookMark");
                            selectedfragment = new BookMarkFragment();
                            break;
                        case R.id.nav_category:
                            item.setTitle("Library");
                            selectedfragment = new CategoryFragment();
                            break;
                    }
                    getFragmentManager().beginTransaction().replace(R.id.frame_layoutt,selectedfragment).commit();
                    return true;
                }
            };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_book, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.schedule) {
            startActivity(new Intent(getActivity(), TimeTableActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}



