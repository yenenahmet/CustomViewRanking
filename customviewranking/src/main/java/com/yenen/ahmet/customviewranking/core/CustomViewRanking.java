package com.yenen.ahmet.customviewranking.core;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class CustomViewRanking extends BaseCustomViewRanking {

    public CustomViewRanking(final LinearLayout containerView, final Context context) {
        super(containerView, context);
    }

    // ADD
    public void addViewToList(final int rowNumber, final int layoutId) {
        final View addView = layoutInflater.inflate(layoutId, null);
        if (addView != null) {
            itemClick(addView, rowNumber);
            viewSparseArray.append(rowNumber, addView);
        }
    }

    public void addViewList(final int rowNumber, final int layoutId) {
        final View addView = layoutInflater.inflate(layoutId, null);
        if (addView != null) {
            viewSparseArray.append(rowNumber, addView);
            itemClick(addView, rowNumber);
            initTransition();
            containerView.addView(addView);
        }
    }

    public void addAllView() {
        delayedAllViewListener(-1,true);
        for (int i = 0; i < viewSparseArray.size(); i++) {
            final int key = viewSparseArray.keyAt(i);
            final View row = viewSparseArray.get(key);
            if (row != null) {
                itemClick(row, key);
                if (isDelayed) {
                    delayedView(row, i, true,key);
                } else {
                    containerView.addView(row);
                }
            }
        }
    }
    // ADD

    // Remove
    public void removeView(final int rowNumber) {
        final View row = viewSparseArray.get(rowNumber);
        if (row != null) {
            initTransition();
            containerView.removeView(row);
            viewSparseArray.remove(rowNumber);
        }
    }


    public void removeAllViews() {
        containerView.removeAllViews();
    }

    public void removeAllViewsDelayed() {
        delayedAllViewListener(-1,false);
        for (int i = 0; i < viewSparseArray.size(); i++) {
            final int key = viewSparseArray.keyAt(i);
            final View row = viewSparseArray.get(key);
            if (row != null) {
                delayedView(row, i, false,key);
            }
        }
    }
    // Remove

    //Refresh
    public void refreshView() {
        removeAllViews();
        addAllView();
    }

    // automatic
    public void refreshViewSequentially() {
        removeAllViews();
        sortingList();
        addAllView();
    }

    //Refresh

    // Listener
    private void itemClick(final View view, final int rowNumber) {
        view.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onClick(rowNumber, view);
            }
        });
    }
    // Listener

}
