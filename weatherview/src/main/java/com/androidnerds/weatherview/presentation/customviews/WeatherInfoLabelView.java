package com.androidnerds.weatherview.presentation.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.androidnerds.weatherview.R;
import com.androidnerds.weatherview.databinding.ItemInfoLabelBinding;

import org.jetbrains.annotations.NotNull;

/**
 * CustomView for displaying a Title and SubTitle used as part of the WeatherCard.
 */
public class WeatherInfoLabelView extends ConstraintLayout {

    public static class LabelInfo {
        public String label;
        public String value;
    }

    private ItemInfoLabelBinding binding;

    @BindingAdapter("title")
    public static void setTitle(WeatherInfoLabelView view, String value) {
        view.binding.textViewValue.setText(value);
        view.binding.executePendingBindings();
    }

    @BindingAdapter("subTitle")
    public static void setSubTitle(@NotNull WeatherInfoLabelView view, String label) {
        view.binding.textViewLabel.setText(label);
        view.binding.executePendingBindings();
    }

    public WeatherInfoLabelView(@NonNull Context context) {
        this(context, null);
    }

    public WeatherInfoLabelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public WeatherInfoLabelView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = DataBindingUtil.inflate(inflater, R.layout.item_info_label, this, true);
        if(null != attrs) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.WeatherInfoLabelView);
            LabelInfo labelInfo = new LabelInfo();
            labelInfo.value = attributes.getString(R.styleable.WeatherInfoLabelView_title);
            labelInfo.label = attributes.getString(R.styleable.WeatherInfoLabelView_subTitle);
            setLabelInfo(labelInfo);
            attributes.recycle();
        }
    }

    private void setLabelInfo(LabelInfo labelInfo) {
        binding.setInfo(labelInfo);
        binding.executePendingBindings();
    }

}
