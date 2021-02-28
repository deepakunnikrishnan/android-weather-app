package com.androidnerds.weatherview.presentation.customviews;

import android.content.Context;
import android.util.AttributeSet;

import com.androidnerds.weatherview.R;
import com.google.android.material.card.MaterialCardView;

public class BaseCardView extends MaterialCardView {
    public BaseCardView(Context context) {
        this(context, null);
    }

    public BaseCardView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BaseCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setCardElevation(5);
        setStrokeColor(context.getColor(android.R.color.darker_gray));
        setStrokeWidth(context.getResources().getDimensionPixelSize(R.dimen.card_border));
        setRadius(context.getResources().getDimensionPixelSize(R.dimen.card_radius));
    }
}
