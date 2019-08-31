package com.example.backgroundchanger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public static final int SELECT_PICTURES = 1;
    private static final String MY_UNIQUE_WORK = "one";
    ClipData clipData;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Myadapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pickButtonClicked(View view) {

        gallery();
    }

    private void gallery(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        //intent.putExtra(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURES);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURES) {
            if (resultCode == Activity.RESULT_OK) {


                clipData = data.getClipData();

                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();

                    for (int i = 0; i < count; i++) {

                      ArrayList<Uri>  nars = new ArrayList<>();

                        nars.add(clipData.getItemAt(i).getUri());
                    }
                }
            }
        }

        setToRecyclerView();
        saveUris();
    }

    private void saveUris() {

        ShareData.data().arrayList = new ArrayList<>();

        for (int i = 0; i < clipData.getItemCount(); i++) {

            ShareData.data().arrayList.add(clipData.getItemAt(i).getUri());
        }
    }

    private void setToRecyclerView() {

        ArrayList<Uri> urilist = new ArrayList<>();

        for (int i=0;i<clipData.getItemCount();i++){

            urilist.add(clipData.getItemAt(i).getUri());

        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new Myadapter(urilist);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void startButtonClicked(View view) {

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(

                PeriodicRequest.class, 15, TimeUnit.MINUTES
        ).build();

        WorkManager.getInstance(getApplicationContext()).enqueue(periodicWorkRequest);
    }
}
