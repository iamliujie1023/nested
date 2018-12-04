package com.liuj.demo.nestedscroll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.support.v4.view.ViewCompat.TYPE_NON_TOUCH;

/**
 * https://www.androiddesignpatterns.com/2018/01/experimenting-with-nested-scrolling.html#footnote4
 */
public class NestedScrollView2 extends NestedScrollView implements NestedScrollingParent2 {
    private final NestedScrollingParentHelper parentHelper;

    public NestedScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        parentHelper = new NestedScrollingParentHelper(this);
    }

    // NestedScrollingParent2 methods.

    @Override
    public boolean onStartNestedScroll(
            @NonNull View child, @NonNull View target, int axes, int type) {
        Log.i("liujie", "onStartNestedScroll");
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(
            @NonNull View child, @NonNull View target, int axes, int type) {
        Log.i("liujie", "onNestedScrollAccepted");
        parentHelper.onNestedScrollAccepted(child, target, axes);
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, type);
    }

    @Override
    public void onNestedPreScroll(
            @NonNull View target, int dx, int dy, @Nullable int[] consumed, int type) {
//        Log.i("liujie", "onNestedPreScroll");
        if (type == TYPE_NON_TOUCH &&
                (dy > 0 && !this.canScrollVertically(1) && !target.canScrollVertically(1)) ||
                (dy < 0 && !this.canScrollVertically(-1) && !target.canScrollVertically(-1))) {
            if (target instanceof NestedScrollingChild2) {
                ((NestedScrollingChild2) target).stopNestedScroll(type);
                return;
            }
        }
        dispatchNestedPreScroll(dx, dy, consumed, null, type);
    }

    @Override
    public void onNestedScroll(
            @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

//        //如果已经滑到顶或底，则停止子view继续滚动。
//        if (type == TYPE_NON_TOUCH && ((dyUnconsumed > 0 && !canScrollVertically(1))
//                  || (dyUnconsumed < 0 && !canScrollVertically(-1)))) {
//            if (target instanceof NestedScrollingChild2) {
//                ((NestedScrollingChild2) target).stopNestedScroll(type);
//                return;
//            }
//        }

        final int oldScrollY = getScrollY();
        scrollBy(0, dyUnconsumed);
        final int myConsumed = getScrollY() - oldScrollY;
        final int myUnconsumed = dyUnconsumed - myConsumed;
        dispatchNestedScroll(0, myConsumed, 0, myUnconsumed, null, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        parentHelper.onStopNestedScroll(target, type);
        stopNestedScroll(type);
        Log.i("liujie", "onStopNestedScroll");
    }

    // NestedScrollingParent methods. For the most part these methods delegate
    // to the NestedScrollingParent2 methods above, passing TYPE_TOUCH as the
    // type to maintain API compatibility.

    @Override
    public boolean onStartNestedScroll(
            @NonNull View child, @NonNull View target, int axes) {
        return onStartNestedScroll(child, target, axes, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedScrollAccepted(
            @NonNull View child, @NonNull View target, int axes) {
        onNestedScrollAccepted(child, target, axes, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedPreScroll(
            @NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        onNestedPreScroll(target, dx, dy, consumed, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedScroll(
            @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {
        onStopNestedScroll(target, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public int getNestedScrollAxes() {
        return parentHelper.getNestedScrollAxes();
    }

}
