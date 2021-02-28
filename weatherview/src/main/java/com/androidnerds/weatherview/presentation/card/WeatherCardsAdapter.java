package com.androidnerds.weatherview.presentation.card;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.weatherview.presentation.card.WeatherCardViewHolder;
import com.androidnerds.weatherview.presentation.customviews.WeatherInfoCardView;
import com.androidnerds.weatherview.presentation.customviews.WeatherInfoEmptyView;
import com.androidnerds.weatherview.presentation.model.WeatherCard;

import java.util.ArrayList;
import java.util.List;

public class WeatherCardsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnMyLocationClickListener {
        void onMyLocationClicked();
    }

    private final List<WeatherCard> weatherCardList;
    private static final int VIEW_TYPE_EMPTY = 1;

    private OnMyLocationClickListener locationClickListener;

    public WeatherCardsAdapter(@NonNull List<WeatherCard> weatherCards,
                               @NonNull OnMyLocationClickListener myLocationClickListener) {
        this.weatherCardList = new ArrayList<>();
        this.weatherCardList.addAll(weatherCards);
        this.locationClickListener = myLocationClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1) {
            WeatherInfoEmptyView emptyView = new WeatherInfoEmptyView(parent.getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(getCardWidth(parent),
                    ViewGroup.LayoutParams.MATCH_PARENT);
            emptyView.setLayoutParams(layoutParams);
            return new EmptyCardViewHolder(emptyView, locationClickListener);
        } else {
            WeatherInfoCardView weatherInfoCardView = new WeatherInfoCardView(parent.getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(getCardWidth(parent),
                    ViewGroup.LayoutParams.MATCH_PARENT);
            weatherInfoCardView.setLayoutParams(layoutParams);
            return new WeatherCardViewHolder(weatherInfoCardView);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return weatherCardList.get(position).isEmpty() ? VIEW_TYPE_EMPTY : super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof WeatherCardViewHolder) {
            ((WeatherCardViewHolder) holder).bind(getItem(position), position);
        }
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

    private int getCardWidth(ViewGroup parent) {
        return (int) (parent.getMeasuredWidth() * 0.66);

    }

    private WeatherCard getItem(int position) {
        return weatherCardList.get(position);
    }

}
