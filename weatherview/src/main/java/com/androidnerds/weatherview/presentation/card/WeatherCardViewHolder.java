package com.androidnerds.weatherview.presentation.card;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.weatherview.presentation.customviews.WeatherInfoCardView;
import com.androidnerds.weatherview.presentation.model.WeatherCard;

/**
 * ViewHolder class for the WeatherCard displayed in the carousel.
 * The UI binding is mainly done using the data binding library.
 */
public class WeatherCardViewHolder extends RecyclerView.ViewHolder {

    public WeatherCardViewHolder(@NonNull WeatherInfoCardView itemView) {
        super(itemView);
    }

    public void bind(WeatherCard weatherCard, int position) {
        WeatherInfoCardView.setData((WeatherInfoCardView) itemView, weatherCard);
    }
}
