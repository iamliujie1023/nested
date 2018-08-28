package com.liuj.demo.nestedscroll;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.support.v4.view.ViewCompat.TYPE_NON_TOUCH;

/**
 *
 */
public class BHomeStickNavLayout extends NestedScrollView2  {

    public static final String TAG = "BHomeStickNavLayout";

    private RecyclerView mRv;
    private ViewPager mViewPager;

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
        mViewPager = this.findViewWithTag("view_page");
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    //先于child滚动
    //前3个为输入参数，最后一个是输出参数
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed, int type) {
        Log.i(BHomeStickNavLayout.TAG, "onNestedPreScroll, "
                + "getScrollY=" + getScrollY() +
                " ,dy= " + dy + " ,type=" + (type == TYPE_NON_TOUCH ? "TYPE_NON_TOUCH" : "TYPE_TOUCH"));

        final RecyclerView rv = (RecyclerView) target;

        if ((dy < 0 && !rv.canScrollVertically(-1))
                || (dy > 0 && this.canScrollVertically(1))) {
            Log.i(BHomeStickNavLayout.TAG, "中标消耗");
            scrollBy(0, dy);
            consumed[1] = dy;
        }

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
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        RecyclerView rv = (RecyclerView) target;
        Log.i(BHomeStickNavLayout.TAG, "Fling, state = " + rv.getScrollState() + ",velocityY=" + velocityY + ", consumed=" + consumed);
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public void fling(int velocityY) {
        Log.i(BHomeStickNavLayout.TAG, "fling()");
        if (mRv != null) {
            mRv.fling(mRv.getScrollX(), velocityY);
            return;
        }
        super.fling(velocityY);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            Log.i("liujie", " onPageSelected pos=" + position);
            FragmentPagerAdapter adapter = (FragmentPagerAdapter) mViewPager.getAdapter();
            Fragment fragment = adapter.getItem(position);
            if (null != fragment) {
                mRv = fragment.getView().findViewWithTag("recycleview");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void init() {
        Log.i("liujie", " init pos=" + 0);
        FragmentPagerAdapter adapter = (FragmentPagerAdapter) mViewPager.getAdapter();
        Fragment fragment = adapter.getItem(0);
        if (null != fragment) {
            mRv = fragment.getView().findViewWithTag("recycleview");
        }
    }


}