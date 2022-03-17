package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.mapmodel.Document;
import com.example.test.utils.BusProvider;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    Context context;
    ArrayList<Document> items;
    RecyclerView recyclerView;

    public LocationAdapter(ArrayList<Document> items, Context context, RecyclerView recyclerView) {
        this.context = context;
        this.items = items;
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Document item) {
        items.add(item);
    }

    public void clear() {
        items.clear();
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(items.get(position).getId());
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_location, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final Document model = items.get(i);
        holder.placeNameText.setText(model.getPlaceName());
        holder.addressText.setText(model.getAddressName());
        holder.location_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(model);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView placeNameText;
        TextView addressText;
        LinearLayout location_detail;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            placeNameText = itemView.findViewById(R.id.ltem_location_tv_placename);
            addressText = itemView.findViewById(R.id.ltem_location_tv_address);
            location_detail = itemView.findViewById(R.id.location_detail);
        }
    }
}