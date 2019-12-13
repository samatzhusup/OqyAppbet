package com.zhusup.okyapp.BookView.SchoolBookView;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    Toolbar toolbar;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        link=getIntent().getStringExtra("link");
        title=getIntent().getStringExtra("title");

        pdfView= (PDFView) findViewById(R.id.pdfview);

        progressBar= (ProgressBar) findViewById(R.id.progressbarr);

        toolbar= findViewById(R.id.pdfbac);
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


        //pdfView.fromAsset(link).load();

        progressBar.setVisibility(View.VISIBLE);

        FileLoader.with(this)
                .load(link)
                .fromDirectory("PdfDiles",FileLoader.DIR_EXTERNAL_PRIVATE)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest fileLoadRequest, FileResponse<File> response) {

                        File pdffile = response.getBody();

                        progressBar.setVisibility(View.GONE);

                        pdfView.fromFile(pdffile)
                                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                                .scrollHandle(null)
                                .enableAntialiasing(true)
                                .spacing(0)
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
}

