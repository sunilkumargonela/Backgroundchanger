package com.example.backgroundchanger;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> {

    private ArrayList<Uri> arrayList;
    Context c;

    public Myadapter(ArrayList<Uri> urilist) {
        this.arrayList = urilist;
    }

    @NonNull
    @Override
    public Myadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Myadapter.ViewHolder holder, int position) {

        holder.imageView.setImageURI((arrayList.get(position)));

    }

    @Override
    public int getItemCount() {

        if (arrayList.size() == 0){

            return 0;
        }else

            return arrayList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView2);
        }
    }
}
