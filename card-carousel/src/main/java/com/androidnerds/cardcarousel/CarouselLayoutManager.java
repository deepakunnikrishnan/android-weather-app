package com.androidnerds.cardcarousel;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * LayoutManager implementation for the CarouselView.
 * Performs the necessary to layout the items in the recyclerview such that items are placed
 * as desired.
 * Performs the transformation required for the items.
 */
public class CarouselLayoutManager extends RecyclerView.LayoutManager {

    public interface LayoutCompletionListener {
        void onLayoutCompleted();
    }

    private final LayoutCompletionListener layoutCompletionListener;

    public CarouselLayoutManager(LayoutCompletionListener layoutCompletionListener) {
        this.layoutCompletionListener = layoutCompletionListener;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrollDistance = fill(dx, recycler, state);
        if (scrollDistance == 0) {
            return 0;
        }
        transformChildViews();
        offsetChildrenHorizontal(-scrollDistance);
        removeAndRecycleHiddenView(dx, recycler);
        return scrollDistance;
    }

    /**
     * When OnLayoutChildren is called, performs the below:
     * 1. Removes the existing views for recycling.
     * 2. Calculates the position for each item and adds the items to the layout.
     * 3. Fills the extra space left in the View after the childViews have been added.
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0) {
            return;
        }
        if (state.isPreLayout()) {
            return;
        }
        detachAndScrapAttachedViews(recycler);
        int actualWidth = 0;
        float midPoint = getWidth() / 2.0f;
        for (int i = 0; i < getItemCount(); i++) {
            actualWidth = layoutItemView(recycler, actualWidth, midPoint, i);
        }
        fill(-1, recycler, state);
    }

    /**
     * When onLayoutCompleted is called, performs the below:
     * 1. Performs the transformations for the child views.
     * 2. Sends a callback to indicate the views have been layed out.
     */
    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        transformChildViews();
        if (null != layoutCompletionListener) {
            layoutCompletionListener.onLayoutCompleted();
        }
    }

    /**
     * Removing of the hidden views based on the scroll action.
     * If the user is scrolling to the right, then removes the hidden views to the left. These will be recycled.
     * if the user is scrolling to the left, then removes the hidden views to the right. These will be recycled.
     */
    private void removeAndRecycleHiddenView(int dx, RecyclerView.Recycler recycler) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (null == view) {
                continue;
            }
            if (dx > 0) {
                if (view.getRight() < 0) {
                    removeAndRecycleView(view, recycler);
                }
            } else {
                if (view.getLeft() > getWidth()) {
                    removeAndRecycleView(view, recycler);
                }
            }
        }
    }

    /**
     * Based on the user scroll action, performs the below:
     * 1. If the user is scrolling to the right, then
     * a) Checks if the user has reached the end of the list. If the user has reached the end,
     * then adds the first item to the end of the list, to provide a carousel effect.
     * 2. If the user is scrolling to the left, then
     * a) Checks if the user has reached the start of the list. If the user has reached the end,
     * then adds the first item to the end of the list, to provide a carousel effect.
     */
    private int fill(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (dx > 0) {
            View lastView = getChildAt(getChildCount() - 1);
            if (null == lastView) {
                return 0;
            }
            int lastItemPosition = getPosition(lastView);
            if (lastView.getRight() < getWidth()) {
                View scrap;
                if (lastItemPosition == getItemCount() - 1) {
                    scrap = recycler.getViewForPosition(0);
                } else {
                    scrap = recycler.getViewForPosition(lastItemPosition + 1);
                }
                if (scrap == null) {
                    return dx;
                }
                addView(scrap);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap, lastView.getRight(), 0, lastView.getRight() + width, height);
                return dx;
            }
        } else {
            View firstView = getChildAt(0);
            if (firstView == null) {
                return 0;
            }
            int firstItemPosition = getPosition(firstView);
            if (firstView.getLeft() >= 0) {
                View scrap;
                if (firstItemPosition == 0) {
                    scrap = recycler.getViewForPosition(getItemCount() - 1);
                } else {
                    scrap = recycler.getViewForPosition(firstItemPosition - 1);
                }
                if (scrap == null) {
                    return 0;
                }
                addView(scrap, 0);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap, firstView.getLeft() - width, 0,
                        firstView.getLeft(), height);
            }
        }
        return dx;
    }

    private int layoutItemView(RecyclerView.Recycler recycler, int actualWidth, float midPoint, int i) {
        View itemView = recycler.getViewForPosition(i);
        addView(itemView);
        measureChildWithMargins(itemView, 0, 0);
        int width = getDecoratedMeasuredWidth(itemView);
        int height = getDecoratedMeasuredHeight(itemView);
        if (i == 0) {
            layoutDecorated(itemView, (int) (midPoint - width / 2), 0, (int) (midPoint + width / 2), height);
            actualWidth = (int) (midPoint + width / 2);
        } else {
            layoutDecorated(itemView, actualWidth, 0, actualWidth + width, height);
            actualWidth += width;
        }
        return actualWidth;
    }

    /**
     * Performs the scale transformation for the items in the UI.
     */
    private void transformChildViews() {
        float midPoint = getWidth() / 2.0f;
        float d0 = 0.f;
        float mShrinkDistance = 0.9f;
        float d1 = mShrinkDistance * midPoint;
        float s0 = 1.f;
        float mShrinkAmount = 0.15f;
        float s1 = 1.f - mShrinkAmount;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            float childMidPoint =
                    (getDecoratedRight(child) + getDecoratedLeft(child)) / 2.f;
            float d = Math.min(d1, Math.abs(midPoint - childMidPoint));
            float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
            child.setScaleX(scale);
            child.setScaleY(scale);
        }
    }
}
