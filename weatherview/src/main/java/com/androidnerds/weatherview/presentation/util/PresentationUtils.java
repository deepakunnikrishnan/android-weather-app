package com.androidnerds.weatherview.presentation.util;

import androidx.annotation.DrawableRes;

import com.androidnerds.weatherview.R;

public class PresentationUtils {

    @DrawableRes
    public static int getDrawableForState(String statusCode) {
        switch (statusCode) {
            case "sn":
                return R.drawable.ic_snow;
            case "sl":
                return R.drawable.ic_sleet;
            case "h":
                return R.drawable.ic_hail;
            case "t":
                return R.drawable.ic_thunder;
            case "hr":
                return R.drawable.ic_heavy_rain;
            case "lr":
                return R.drawable.ic_light_rain;
            case "s":
                return R.drawable.ic_showers;
            case "hc":
                return R.drawable.ic_heavy_cloud;
            case "lc":
                return R.drawable.ic_light_cloud;
            case "c":
                return R.drawable.ic_clear;
        }
        return R.drawable.ic_clear;
    }
}
