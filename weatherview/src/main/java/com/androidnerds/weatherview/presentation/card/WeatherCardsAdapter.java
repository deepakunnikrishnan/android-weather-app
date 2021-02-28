package com.androidnerds.weatherview.presentation.card;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.weatherview.presentation.card.WeatherCardViewHolder;
import com.androidnerds.weatherview.presentation.customviews.WeatherInfoCardView;
import com.androidnerds.weatherview.presentation.model.WeatherCard;

import java.util.ArrayList;
import java.util.List;

public class WeatherCardsAdapter extends RecyclerView.Adapter<WeatherCardViewHolder> {

    private final List<WeatherCard> weatherCardList;

    public WeatherCardsAdapter(@NonNull List<WeatherCard> weatherCards) {
        this.weatherCardList = new ArrayList<>();
        this.weatherCardList.addAll(weatherCards);
    }

    @NonNull
    @Override
    public WeatherCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WeatherInfoCardView weatherInfoCardView = new WeatherInfoCardView(parent.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(getCardWidth(parent),
                ViewGroup.LayoutParams.MATCH_PARENT);
        weatherInfoCardView.setLayoutParams(layoutParams);
        return new WeatherCardViewHolder(weatherInfoCardView);
    }

    private int getCardWidth(ViewGroup parent) {
        return (int) (parent.getMeasuredWidth() * 0.66);

    }

    @Override
    public void onBindViewHolder(@NonNull WeatherCardViewHolder holder, int position) {
        holder.bind(getItem(position), position);
    }

    private WeatherCard getItem(int position) {
        return weatherCardList.get(position);
    }

    public void submitList(List<WeatherCard> newList) {
        if(null != newList) {
            weatherCardList.clear();
            weatherCardList.addAll(newList);
            notifyDataSetChanged();
        }
    }

    public List<WeatherCard> getItems() {
        return weatherCardList;
    }

    @Override
    public int getItemCount() {
        return weatherCardList.size();
    }
}
