package com.androidnerds.newsapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.newsapp.databinding.ItemCardBinding;

import java.util.ArrayList;
import java.util.List;

public class CarouseViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Card> items;

    public CarouseViewAdapter() {
        this.items = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_card, parent, false);
        return new CardItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CardItemViewHolder) {
            ((CardItemViewHolder) holder).bind(items.get(position));
        }
    }

    public void submitList(List<Card> items) {
        this.items.clear();
        this.items.addAll(items);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
