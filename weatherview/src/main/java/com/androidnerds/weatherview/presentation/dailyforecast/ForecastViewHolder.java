package com.androidnerds.weatherview.presentation.dailyforecast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.weatherview.databinding.ItemForecastBinding;
import com.androidnerds.weatherview.presentation.model.ForecastViewData;

/**
 * ViewHolder class for the Daily Forecast item displayed.
 */
public class ForecastViewHolder extends RecyclerView.ViewHolder {

    private final ItemForecastBinding binding;

    public ForecastViewHolder(@NonNull ItemForecastBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ForecastViewData forecastViewData) {
        binding.setForecast(forecastViewData);
    }
}
