package com.example.nitinsharma.loginapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nitin sharma on 07-Dec-18.
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    Context context;
    ArrayList<ItemsBean> arrayList = new ArrayList<>();
    itemClick itemClick;


    public ToDoAdapter(Context context, ArrayList<ItemsBean> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.listitem, null, false);
        return new ViewHolder(v, itemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ViewHolder holder, int position) {
        ItemsBean itemsBean = arrayList.get(position);
        holder.title.setText(itemsBean.getTitle());
        holder.description.setText(itemsBean.getDescription()+"...");

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,description;



        public ViewHolder(@NonNull View itemView, final itemClick itemClick) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.subtitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClick != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            itemClick.click(pos);

                        }
                    }

                }
            });


        }
    }

    public interface itemClick {
        void click(int pos);

    }

    public void itemClickListener(itemClick itemClick) {
        this.itemClick=itemClick;

    }


}
