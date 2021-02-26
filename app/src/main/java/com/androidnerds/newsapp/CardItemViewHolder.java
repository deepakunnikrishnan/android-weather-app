package com.androidnerds.newsapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.newsapp.databinding.ItemCardBinding;


public class CardItemViewHolder extends RecyclerView.ViewHolder {

    private ItemCardBinding cardBinding;

    public CardItemViewHolder(@NonNull ItemCardBinding binding) {
        super(binding.getRoot());
        this.cardBinding = binding;
    }

    public void bind(Card card) {
        cardBinding.setCard(card);
    }
}
