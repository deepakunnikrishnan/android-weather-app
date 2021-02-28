package com.androidnerds.weatherview.presentation.dailyforecast;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.weatherview.databinding.ItemForecastBinding;
import com.androidnerds.weatherview.presentation.model.ForecastViewData;

public class ForecastViewHolder extends RecyclerView.ViewHolder {

    private ItemForecastBinding binding;
    public ForecastViewHolder(@NonNull ItemForecastBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ForecastViewData forecastViewData) {
        binding.setForecast(forecastViewData);
    }
}
