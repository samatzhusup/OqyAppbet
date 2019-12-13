    package com.zhusup.okyapp.ReaderBook;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.github.barteksc.pdfviewer.PDFView;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;
import com.zhusup.okyapp.R;
import java.io.File;


    public class pdf extends AppCompatActivity {

        String link="";
        String title="";
        PDFView pdfView;
        ProgressBar progressBar;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pdf);

            link=getIntent().getStringExtra("link");
            title=getIntent().getStringExtra("title");
            pdfView= (PDFView) findViewById(R.id.pdfview);
            progressBar= (ProgressBar) findViewById(R.id.progressbarr);

            Toolbar toolbar = (Toolbar) findViewById(R.id.pdfbac);
            setSupportActionBar(toolbar);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setTitle(title);
                ab.setDisplayHomeAsUpEnabled(true);
            }

            progressBar.setVisibility(View.VISIBLE);

            FileLoader.with(this)
                    .load(link)
                    .fromDirectory("KASMASA",FileLoader.DIR_EXTERNAL_PRIVATE)
                    .asFile(new FileRequestListener<File>() {
                        @Override
                        public void onLoad(FileLoadRequest request, FileResponse<File> response) {

                            File pdffile = response.getBody();
                            progressBar.setVisibility(View.GONE);

                            pdfView.fromFile(pdffile)
                                    .password(null)
                                    .defaultPage(0)
                                    .load();
                        }

                        @Override
                        public void onError(FileLoadRequest request, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Internet not found!", Toast.LENGTH_SHORT);
                            toast.show();

                        }
                    });
        }

        @Override public boolean onOptionsItemSelected(MenuItem item)
        {
            if (item.getItemId() == android.R.id.home)//means home default
            {
                onBackPressed();
                return true;
            }
            return false;
        }
    }

