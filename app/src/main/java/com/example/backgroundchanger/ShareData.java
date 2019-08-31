package com.example.backgroundchanger;

import android.net.Uri;

import java.util.ArrayList;

public class ShareData{
    private volatile static ShareData shareData;
    public static ShareData data(){
        if(shareData == null){
            synchronized (ShareData.class) {
                if (shareData == null) {
                    shareData = new ShareData();
                }
            }
        }
        return shareData;

    }

    public ArrayList<Uri> arrayList;
}

