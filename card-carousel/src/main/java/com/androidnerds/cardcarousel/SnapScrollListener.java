package com.androidnerds.cardcarousel;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

/**
 * ScrollListener for the RecyclerView.
 * Uses the {@link SnapHelper} to find the position of the view in focus
 * and sends a callback to listener when the selected item changes via {@link OnSnapPositionChangeListener#onSnapPositionChanged(int)}
 */
public class SnapScrollListener extends RecyclerView.OnScrollListener {

    public interface OnSnapPositionChangeListener {
        void onSnapPositionChanged(int position);
    }

    private final SnapHelper snapHelper;
    private final OnSnapPositionChangeListener snapPositionChangeListener;
    private int currentSnapPosition = RecyclerView.NO_POSITION;

    public SnapScrollListener(SnapHelper snapHelper, OnSnapPositionChangeListener snapPositionChangeListener) {
        this.snapHelper = snapHelper;
        this.snapPositionChangeListener = snapPositionChangeListener;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if(newState == RecyclerView.SCROLL_STATE_IDLE) {
            notifySnapPosition(recyclerView);
        }
    }

    private void notifySnapPosition(RecyclerView recyclerView) {
        int snapPosition = getSnapPosition(recyclerView, snapHelper);
        if (currentSnapPosition != snapPosition) {
            if (null != snapPositionChangeListener) {
                snapPositionChangeListener.onSnapPositionChanged(snapPosition);
                currentSnapPosition = snapPosition;
            }
        }
    }

    int getSnapPosition(RecyclerView recyclerView, SnapHelper snapHelper) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (null == layoutManager) {
            return RecyclerView.NO_POSITION;
        }
        View snapView = snapHelper.findSnapView(layoutManager);
        if (null == snapView) {
            return RecyclerView.NO_POSITION;
        }
        return layoutManager.getPosition(snapView);
    }
}
