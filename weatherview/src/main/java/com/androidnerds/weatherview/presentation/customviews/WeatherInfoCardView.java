package com.androidnerds.weatherview.presentation.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.androidnerds.weatherview.R;
import com.androidnerds.weatherview.databinding.ItemWeatherCardBinding;
import com.androidnerds.weatherview.presentation.model.WeatherCard;
import com.google.android.material.card.MaterialCardView;

public class WeatherInfoCardView extends MaterialCardView {


    private ItemWeatherCardBinding binding;

    public WeatherInfoCardView(@NonNull Context context) {
        this(context, null);
    }

    public WeatherInfoCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = DataBindingUtil.inflate(inflater, R.layout.item_weather_card, this, true);
        setCardElevation(5);
        setStrokeColor(context.getColor(android.R.color.darker_gray));
        setStrokeWidth(context.getResources().getDimensionPixelSize(R.dimen.card_border));
        setRadius(context.getResources().getDimensionPixelSize(R.dimen.card_radius));
    }

    @BindingAdapter("data")
    public static void setData(WeatherInfoCardView view, WeatherCard weatherCard) {
        view.binding.setData(weatherCard);
        view.binding.executePendingBindings();
    }

    @BindingAdapter("image")
    public static void setImage(ImageView imageView, @DrawableRes int resourceId) {
        imageView.setImageDrawable(imageView.getContext().getDrawable(resourceId));
    }

}
