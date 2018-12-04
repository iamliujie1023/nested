package com.liuj.demo.nestedscroll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.support.v4.view.ViewCompat.TYPE_NON_TOUCH;

public class BHomeStickNavLayout extends NestedScrollView2 {

    public static final String TAG = "BHomeStickNavLayout";

    private View mTargetView;

    public BHomeStickNavLayout(Context context) {
        this(context, null);
    }

    public BHomeStickNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        super.onNestedScrollAccepted(child, target, axes, type);
        mTargetView = target;
    }

    @Override
    public void stopNestedScroll(int type) {
        super.stopNestedScroll(type);
    }

    //先于child滚动
    //前3个为输入参数，最后一个是输出参数
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed, int type) {
        Log.i(BHomeStickNavLayout.TAG, "onNestedPreScroll, "
                + "getScrollY=" + getScrollY() +
                " ,dy= " + dy + " ,type=" + (type == TYPE_NON_TOUCH ? "TYPE_NON_TOUCH" : "TYPE_TOUCH"));

        if ((dy < 0 && !target.canScrollVertically(-1))
                || (dy > 0 && this.canScrollVertically(1))) {
            Log.i(BHomeStickNavLayout.TAG, "中标消耗");
            scrollBy(0, dy);
            consumed[1] = dy;
        }

//        if (type == TYPE_NON_TOUCH &&
//                (dy > 0 && !this.canScrollVertically(1) && !target.canScrollVertically(1)) ||
//                (dy < 0 && !this.canScrollVertically(-1) && !target.canScrollVertically(-1))) {
//            if (target instanceof RecyclerView) {
//                ((RecyclerView) target).stopScroll();
//                return;
//            }
//        }
        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.i(BHomeStickNavLayout.TAG,
                "onNestedScroll, dyUnconsumed= " + dyUnconsumed +
                        ", dyUnconsumed =" + dyUnconsumed +
                        " ,type=" + (type == TYPE_NON_TOUCH ? "TYPE_NON_TOUCH" : "TYPE_TOUCH"));
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    @Override
    public void fling(int velocityY) {
        Log.i(BHomeStickNavLayout.TAG, "fling()");
        if (mTargetView instanceof RecyclerView) {
            ((RecyclerView) mTargetView).fling(mTargetView.getScrollX(), velocityY);
            return;
        }
        super.fling(velocityY);
    }

}