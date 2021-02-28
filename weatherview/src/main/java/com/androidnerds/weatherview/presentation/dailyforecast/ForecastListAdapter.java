package com.androidnerds.weatherview.presentation.dailyforecast;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.weatherview.R;
import com.androidnerds.weatherview.databinding.ItemForecastBinding;
import com.androidnerds.weatherview.presentation.model.ForecastViewData;

import java.util.ArrayList;
import java.util.List;

public class ForecastListAdapter extends RecyclerView.Adapter<ForecastViewHolder> {

    List<ForecastViewData> forecastViewDataList;

    public ForecastListAdapter(@NonNull List<ForecastViewData> forecastViewDataList) {
        this.forecastViewDataList = new ArrayList<>();
        this.forecastViewDataList.addAll(forecastViewDataList);
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemForecastBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        holder.bind(forecastViewDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return forecastViewDataList.size();
    }

    public void submitList(List<ForecastViewData> newList) {
        if(null != newList) {
            forecastViewDataList.clear();
            forecastViewDataList.addAll(newList);
            notifyDataSetChanged();
        }
    }
}
