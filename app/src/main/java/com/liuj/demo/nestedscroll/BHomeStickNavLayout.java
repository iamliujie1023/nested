package com.liuj.demo.nestedscroll;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.support.v4.view.ViewCompat.TYPE_NON_TOUCH;

/**
 *
 */
public class BHomeStickNavLayout extends NestedScrollView2 {

    public static final String TAG = "BHomeStickNavLayout";

    public BHomeStickNavLayout(Context context) {
        this(context, null);
    }

    public BHomeStickNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    //先于child滚动
    //前3个为输入参数，最后一个是输出参数
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed, int type) {
        Log.i(BHomeStickNavLayout.TAG, "onNestedPreScroll, "
                + "getScrollY=" + getScrollY() +
                " ,dy= " + dy + " ,type=" + (type == TYPE_NON_TOUCH ? "TYPE_NON_TOUCH" : "TYPE_TOUCH"));

        final RecyclerView rv = (RecyclerView) target;

//        //拦截一些边界值
//        //NestedScrollView && RV 都不能滑动的时候 直接消耗掉
//        if ((dy > 0 && !this.canScrollVertically(1) && !rv.canScrollVertically(1)) ||
//                (dy < 0 && !this.canScrollVertically(-1) && !rv.canScrollVertically(-1))) {
//            consumed[1] = dy;
//            Log.i(BHomeStickNavLayout.TAG, "onNestedPreScroll, 拦截边界");
//            return;
//        }

        if ((dy < 0 && isRvScrolledToTop(rv))
                || (dy > 0 && !isNsvScrolledToBottom(this))) {
            Log.i(BHomeStickNavLayout.TAG, "中标");
            scrollBy(0, dy);
            consumed[1] = dy;
            return;
        }

        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.i(BHomeStickNavLayout.TAG, "onNestedScroll, dyUnconsumed= " + dyUnconsumed + ", dyUnconsumed =" + dyUnconsumed);

        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.i(BHomeStickNavLayout.TAG, "PreFling, velocityY=" + velocityY);

//        //拦截一些边界值
//        //NestedScrollView2 && RV 都不能滑动的时候 直接消耗掉Fling
//        RecyclerView rv = (RecyclerView) target;
//        if ((velocityY > 0 && !this.canScrollVertically(1) && !rv.canScrollVertically(1)) ||
//                (velocityY < 0 && !this.canScrollVertically(-1) && !rv.canScrollVertically(-1))) {
//            Log.i(BHomeStickNavLayout.TAG, "PreFling consumed");
//            return true;
//        }

        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.i(BHomeStickNavLayout.TAG, "Fling, velocityY=" + velocityY + ", consumed=" + consumed);
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    private static boolean isNsvScrolledToBottom(NestedScrollView nsv) {
        return !nsv.canScrollVertically(1);
    }

    private static boolean isRvScrolledToTop(RecyclerView rv) {
        final LinearLayoutManager lm = (LinearLayoutManager) rv.getLayoutManager();
        return lm.findFirstVisibleItemPosition() == 0
                && lm.findViewByPosition(0).getTop() == 0;
    }

}