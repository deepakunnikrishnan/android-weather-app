package com.androidnerds.weatherview.presentation.card;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ViewHolder for the Empty view displayed in the carousel.
 */
public class EmptyCardViewHolder extends RecyclerView.ViewHolder {

    public EmptyCardViewHolder(@NonNull View itemView, WeatherCardsAdapter.OnMyLocationClickListener locationClickListener) {
        super(itemView);
        itemView.setOnClickListener(v -> locationClickListener.onMyLocationClicked());
    }


}
