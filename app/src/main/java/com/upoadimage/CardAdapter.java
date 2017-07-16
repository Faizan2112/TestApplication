package com.upoadimage;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 10/29/2015.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<ListItem> items;

    public CardAdapter(String[] names, String[] urls, Bitmap[] images){
        super();
        items = new ArrayList<ListItem>();
        for(int i =0; i<names.length; i++){
            ListItem item = new ListItem();
           // item.setName(names[i]);
            item.setUrl(urls[i]);
            item.setImage(images[i]);
            items.add(item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem list =  items.get(position);
        holder.imageView.setImageBitmap(list.getImage());
        //holder.textViewName.setText(list.getName());
        holder.textViewUrl.setText(list.getUrl());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textViewName;
        public TextView textViewUrl;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewUrl = (TextView) itemView.findViewById(R.id.textViewUrl);

        }
    }
}

/*

import android.graphics.Bitmap;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.testapplication.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import static android.support.design.R.styleable.View;

*/
/**
 * Created by faizan on 05/06/2017.
 *//*


public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   List<ListItem> items ;
    public CardAdapter(String[] names , String[] urls , Bitmap[] images)
    {
        super();
        items = new ArrayList<ListItem>();
        for(int i= 0 ; i<names.length ; i++)
        {
            ListItem item = new ListItem();
            item.setName(names[i]);
            item.setUrl(urls[i]);
            item.setImage(images[i]);
            items.add(item);

        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card_view,parent,false);
        ViewHolder viewHolder = new RecyclerView.ViewHolder()

        return null;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
*/
