package com.androidnerds.cardcarousel;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class CarouselLayoutManager extends RecyclerView.LayoutManager {

    public interface LayoutCompletionListener {
        void onLayoutCompleted();
    }

    public static final String TAG = "LooperLayoutManager";
    private LayoutCompletionListener layoutCompletionListener;

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
        transformChildViews();
        if (scrollDistance == 0) {
            return 0;
        }
        offsetChildrenHorizontal(-scrollDistance);
        recyclerHideView(dx, recycler, state);
        return scrollDistance;
    }

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

    private void recyclerHideView(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
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
        }
        fill(-1, recycler, state);
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        if (null != layoutCompletionListener) {
            layoutCompletionListener.onLayoutCompleted();
        }
    }
}
