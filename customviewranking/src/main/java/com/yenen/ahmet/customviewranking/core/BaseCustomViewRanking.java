package com.yenen.ahmet.customviewranking.core;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.yenen.ahmet.customviewranking.listener.ChangingViewListener;
import com.yenen.ahmet.customviewranking.listener.DelayedAllViewListener;
import com.yenen.ahmet.customviewranking.listener.ItemClickListener;

public class BaseCustomViewRanking {
    protected final LinearLayout containerView;
    protected SparseArray<View> viewSparseArray;
    protected final LayoutInflater layoutInflater;
    protected ItemClickListener itemClickListener;
    private DelayedAllViewListener delayedAllViewListener;
    protected ChangingViewListener changingViewListener;
    protected final Context context;
    protected boolean isDelayed = true, isContinueScrool = false;
    private volatile short postDelayedMilis = 250, animationType = 0;
    private long duration = 0;
    private Transition transition = null;

    protected BaseCustomViewRanking(final LinearLayout containerView, final Context context) {
        if (containerView == null || context == null) {
            throw new NullPointerException();
        }
        this.containerView = containerView;
        this.viewSparseArray = new SparseArray<>();
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    //Animation
    protected void initTransition() {
        final Transition t;
        if (transition == null) switch (animationType) {
            case CustomViewRankingConst.FadeIn:
                t = new Fade(Fade.IN);
                break;
            case CustomViewRankingConst.FadeOut:
                t = new Fade(Fade.OUT);
                break;
            case CustomViewRankingConst.SlideBottom:
                t = new Slide(Gravity.BOTTOM);
                break;
            case CustomViewRankingConst.SlideLeft:
                t = new Slide(Gravity.START);
                break;
            case CustomViewRankingConst.SlideRight:
                t = new Slide(Gravity.END);
                break;
            default:
                t = null;
                break;
        }
        else {
            t = transition;
        }
        if (t != null) {
            t.setDuration(duration);
            TransitionManager.beginDelayedTransition(containerView, t);
        }
    }

    // Sparse
    public View getViewToList(final int rowNumber) {
        return viewSparseArray.get(rowNumber);
    }

    public int getSize() {
        return viewSparseArray.size();
    }

    public int getPosition(final int rowNumber) {
        for (int i = 0; i < getSize(); i++) {
            if (viewSparseArray.keyAt(i) == rowNumber) {
                return i;
            }
        }
        return -1;
    }

    public void clearSparse() {
        if (viewSparseArray != null) {
            viewSparseArray.clear();
        }
    }
    // Sparse

    // OnDestroy Or MVVM onCleared()  RUN
    public final void unBind() {
        clearSparse();
        viewSparseArray = null;
        itemClickListener = null;
        changingViewListener = null;
        delayedAllViewListener = null;
        transition = null;
    }
    // OnDestroy Or MVVM OnClread RUN

    // Core Func
    protected void delayedView(final View view, final int i, final boolean isAdd, final int rowNumber) {
        final Handler handler = new Handler();
        handler.postDelayed(() -> ((Activity) context).runOnUiThread(() -> {

            onChange(rowNumber, isAdd, i);
            initTransition();

            if (isAdd) {
                containerView.addView(view);
            } else {
                containerView.removeView(view);
            }

            continueScrool(isAdd);
            delayedAllViewListener(i, isAdd);

        }), postDelayedMilis * i);
    }

    private void continueScrool(final boolean isAdd) {
        if (isContinueScrool) {
            final ViewParent rootView = containerView.getParent();
            if (rootView instanceof ScrollView) {
                final ScrollView scrollView = (ScrollView) rootView;
                scrollView.smoothScrollTo(0, isAdd ? scrollView.getBottom() : 0);
                return;
            }
            if (rootView instanceof NestedScrollView) {
                final NestedScrollView nestedScrollView = (NestedScrollView) rootView;
                nestedScrollView.smoothScrollTo(0, isAdd ? nestedScrollView.getBottom() : 0);
            }
        }
    }

    public final void sortingList() {
        final SparseArray<View> temp = new SparseArray<>();
        while (getSize() > 0) {
            int min = Integer.MAX_VALUE;
            View tempView = null;
            for (int i = 0; i < getSize(); i++) {
                if (viewSparseArray.keyAt(i) <= min) {
                    min = viewSparseArray.keyAt(i);
                    tempView = viewSparseArray.get(min);
                }
            }
            temp.put(min, tempView);
            viewSparseArray.remove(min);
        }
        clearSparse();
        viewSparseArray =null;
        viewSparseArray = temp;
    }
    // Core Func

    //Listener
    private void onChange(int rowNumber, boolean isAdd, int position) {
        if (changingViewListener != null) {
            changingViewListener.onChange(rowNumber, isAdd, position);
        }
    }

    protected void delayedAllViewListener(final int i, final boolean isAdd) {
        if (delayedAllViewListener != null) {
            if (i == -1) {
                delayedAllViewListener.onStart(isAdd);
            } else if (i == getSize() - 1) {
                delayedAllViewListener.onEnd(isAdd);
            }
        }
    }

    //Listener

    // Setter
    public BaseCustomViewRanking setIsDelayed(boolean delayed) {
        this.isDelayed = delayed;
        return this;
    }

    public BaseCustomViewRanking setPostDelayedMilis(short milis) {
        this.postDelayedMilis = milis;
        return this;
    }

    public BaseCustomViewRanking setAnimationType(short type) {
        this.animationType = type;
        return this;
    }

    public BaseCustomViewRanking setTransition(Transition transition) {
        this.transition = transition;
        return this;
    }

    public BaseCustomViewRanking setTransitionForLayout(int resource) {
        this.transition = TransitionInflater.from(containerView.getContext()).inflateTransition(resource);
        return this;
    }

    public BaseCustomViewRanking setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }

    public BaseCustomViewRanking setContinueScrol(boolean isContinueScrool) {
        this.isContinueScrool = isContinueScrool;
        return this;
    }

    public BaseCustomViewRanking setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public BaseCustomViewRanking setDelayedAllViewListener(DelayedAllViewListener delayedAllViewListener) {
        this.delayedAllViewListener = delayedAllViewListener;
        return this;
    }

    public BaseCustomViewRanking setChangingViewListener(ChangingViewListener changingViewListener) {
        this.changingViewListener = changingViewListener;
        return this;
    }
    // Setter

}
