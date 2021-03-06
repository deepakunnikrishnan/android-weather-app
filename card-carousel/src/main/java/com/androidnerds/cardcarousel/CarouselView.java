package com.androidnerds.cardcarousel;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView implementation of a CarouselView in which the item in the middle will be zoomed in
 * and user can see the near by item to left and right.
 * Uses the {@link CarouselLayoutManager} class as the LayoutManager for the RecyclerView which performs the
 * laying out of items in the recyclerview.
 * uses the {@link LinearSnapHelper} for making sure the item is snapped after a scroll or fling.
 */
public class CarouselView extends RecyclerView implements
        SnapScrollListener.OnSnapPositionChangeListener, CarouselLayoutManager.LayoutCompletionListener{


    public interface OnCarouselItemSelectedListener {
        void onItemSelected(int position);
    }

    private SnapScrollListener snapScrollListener;
    private OnCarouselItemSelectedListener itemSelectedListener;

    public CarouselView(Context context) {
        this(context, null);
    }

    public CarouselView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CarouselView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        CarouselLayoutManager layoutManager = new CarouselLayoutManager(this);
        setLayoutManager(layoutManager);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(this);
        snapScrollListener = new SnapScrollListener(snapHelper, this);
        this.addOnScrollListener(snapScrollListener);
    }

    public void setItemSelectedListener(OnCarouselItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        if (layoutManager instanceof CarouselLayoutManager) {
            super.setLayoutManager(layoutManager);
        } else {
            throw new IllegalArgumentException("Unsupported LayoutManager");
        }
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return true;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return false;
    }

    @Override
    public void onSnapPositionChanged(int position) {
        if(null != itemSelectedListener) {
            itemSelectedListener.onItemSelected(position);
        }
    }

    @Override
    public void onLayoutCompleted() {
        snapScrollListener.onScrollStateChanged(this, RecyclerView.SCROLL_STATE_IDLE);
    }
}
