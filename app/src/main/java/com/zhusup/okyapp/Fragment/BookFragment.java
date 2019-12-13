package com.zhusup.okyapp.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zhusup.okyapp.Activity.TimeTableActivity;
import com.zhusup.okyapp.BookView.SchoolBookView.Product;
import com.zhusup.okyapp.BookView.SchoolBookView.ProductAdapter;
import com.zhusup.okyapp.R;
import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment {

    List<Product> productList;
    ProductAdapter adapter;
    //the recyclerview
    RecyclerView recyclerView;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);
        //initializing the productlist
        productList = new ArrayList<>();

        //adding some items to our list
        productList.add(
                new Product(
                        1,
                        "Algebra\n",
                        0,
                        R.drawable.algebra,
                        "https://www.xeroxscanners.com/downloads/Manuals/XMS/PDF_Converter_Pro_Quick_Reference_Guide.RU.pdf" +
                                ""

                ));

        productList.add(
                new Product(
                        1,
                        " Geometry \n",
                        0,
                        R.drawable.geometry,
                        "https://www.xeroxscanners.com/downloads/Manuals/XMS/PDF_Converter_Pro_Quick_Reference_Guide.RU.pdf"
                ));

        productList.add(
                new Product(
                        3,
                        "Physics",
                        60000,
                        R.drawable.phys,
                        "https://firebasestorage.googleapis.com/v0/b/firepdf-4c1d6.appspot.com/o/2.intro.pdf?alt=media&token=75731b04-c1e7-42c4-b988-e50a8f7e5f6b    "
                ));

        productList.add(
                new Product(
                        4,
                        "Biology",
                        60000,
                        R.drawable.bio,
                        "https://firebasestorage.googleapis.com/v0/b/firepdf-4c1d6.appspot.com/o/2.intro.pdf?alt=media&token=75731b04-c1e7-42c4-b988-e50a8f7e5f6b    "
                ));
        productList.add(
                new Product(
                        5,
                        "English",
                        60000,
                        R.drawable.eng,
                        "https://firebasestorage.googleapis.com/v0/b/firepdf-4c1d6.appspot.com/o/2.intro.pdf?alt=media&token=75731b04-c1e7-42c4-b988-e50a8f7e5f6b    "
                ));
        productList.add(
                new Product(
                        6,
                        "Giorahy",
                        60000,
                        R.drawable.geor,
                        "https://firebasestorage.googleapis.com/v0/b/firepdf-4c1d6.appspot.com/o/2.intro.pdf?alt=media&token=75731b04-c1e7-42c4-b988-e50a8f7e5f6b    "
                ));
        productList.add(
                new Product(
                        7,
                        "Chimistry",
                        60000,
                        R.drawable.him,
                        "java6.pdf"
                ));
        productList.add(
                new Product(
                        8,
                        "World History",
                        60000,
                        R.drawable.vsehistory,
                        "https://firebasestorage.googleapis.com/v0/b/firepdf-4c1d6.appspot.com/o/2.intro.pdf?alt=media&token=75731b04-c1e7-42c4-b988-e50a8f7e5f6b    "
                ));
        productList.add(
                new Product(
                        9,
                        "Informatics",
                        60000,
                        R.drawable.inform,
                        "https://firebasestorage.googleapis.com/v0/b/firepdf-4c1d6.appspot.com/o/2.intro.pdf?alt=media&token=75731b04-c1e7-42c4-b988-e50a8f7e5f6b    "
                ));
        productList.add(
                new Product(
                        1,
                        "Technolgy \n",
                        0,
                        R.drawable.tech,
                        "http://yandex.ru/clck/jsredir?bu=d2d12y&from=yandex.ru%3Bsearch%2F%3Bweb%3B%3B&text=&etext=3913.j9liEKHyFTz19MRu-tLnABjQdGmZTcoBS0HOfATnn3M.c8e5d9e8c9b1f362a2152691604bcd80eee0aed6&uuid=&state=PEtFfuTeVD5kpHnK9lio9T6U0-imFY5IshtIYWJN7W9gCP82Ty_MkILSpQsSL_4seMWRrvw7swlkTSXMEde-IzM9MrE0Zs_jGVPhqpFDXZUd6Rb4pDFIRkpJrg4aALEs1tQvVCHkmxI,&&cst=AiuY0DBWFJ5Hyx_fyvalFFk3oysy3VO87M2W1C6rvUm_kDPj2Hi3ZXWf6JkBANEPP566INSExvktUuMSjVX842yK3gECPEIUyaKt102scXpwj1CzLzfHJg-3lqaA2JD7n_xaVH6MSNYfzHUr4XfKOMUM4D3cjrNfclfhoi_rnbvl45esSrYoj8A9yysLQD6Zt2f8ujuxcwaGZzrTKhTJ-WsQJk34__grA_9KOKGQtq0-nGMO2OWpR73bsy5w6XHC1NRUNNr2GlLwx4NivQzPeg,,&data=UlNrNmk5WktYejY4cHFySjRXSWhXR2g0OVprRDlKS0pjcFZRa2ROUllpLUZhZDhHQXdUNDFGeWhRcTQ2dGR2emYxa1Y3R3E2QnhPSlA3Z2FwSXNsMG82bm1iVUFjT08tclJ5YU5rSFdXTFN4VlpiaG05elI5NzdkSi1MSTBmNEtEclBrRU53T3J0Z0oyY2VPekstMEN5ZU92Yk1OOFBwM2FzdVE3NzAtSlBGY3hRQUp0UmpEVU1WTmoxaXAyRnVEeklJTWFIWVVyZEtnVllCV3lvSTFaZyws&sign=7d8f4397950a5e0adc5e835ce2807882&keyno=0&b64e=2&ref=orjY4mGPRjk5boDnW0uvlrrd71vZw9kpXTjERvnjSuPinptKKHfOAeoMpYdB96n-FoMJRXwtjltSZkabWd04yw,,&l10n=ru&rp=1&cts=1565457562307&mc=3.182005814760214&hdtime=7971.105"

                ));


        //creating recyclerview adapter
        recyclerView = v.findViewById(R.id.recyclerView);
        adapter = new ProductAdapter(getContext(), productList);

        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }
        recyclerView.setAdapter(adapter);
        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        return v;
    }

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



