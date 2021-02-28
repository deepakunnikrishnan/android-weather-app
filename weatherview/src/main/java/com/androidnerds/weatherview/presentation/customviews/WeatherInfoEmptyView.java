package com.androidnerds.weatherview.presentation.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.androidnerds.weatherview.R;
import com.androidnerds.weatherview.databinding.ItemEmptyCardBinding;

public class WeatherInfoEmptyView extends BaseCardView {

    public WeatherInfoEmptyView(Context context) {
        this(context, null);
    }

    public WeatherInfoEmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public WeatherInfoEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ItemEmptyCardBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_empty_card, this, true);
    }


}
