package com.example.backgroundchanger;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class PeriodicRequest extends Worker {

    static int count = 0;
    private Context context;
    ArrayList<String> values;

    WallpaperManager wallpaper;
    ArrayList<Uri> narrows;
    int myTime =0;
    public PeriodicRequest(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {


        Properties properties = new Properties();
        context = getApplicationContext();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String one = properties.getProperty("count");

        int temp = Integer.parseInt(one);
        count++;
        temp += count;
        properties.setProperty("count", String.valueOf(temp));
        String two = properties.getProperty("count");

        Log.d("final", "running");


        Log.d("final String", values.get(Integer.parseInt(two)));


        narrows = ShareData.data().arrayList;
        wallpaper = WallpaperManager.getInstance(getApplicationContext());

        Uri uri = narrows.get(Integer.parseInt(one));
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            wallpaper.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return Result.success();
    }
}
