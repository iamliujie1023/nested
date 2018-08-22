package com.liuj.demo.nestedscroll;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.id_stickynavlayout_headerview)
    LinearLayout mIdStickynavlayoutHeaderview;

    @BindView(R.id.id_stickynavlayout_indicator)
    SimpleViewPagerIndicator mIdStickynavlayoutIndicator;

    @BindView(R.id.id_stickynavlayout_viewpager)
    ViewPager mViewpager;

    @BindView(R.id.id_stickynavlayout_container)
    BHomeStickNavLayout mIdStickynavlayoutContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mIdStickynavlayoutIndicator.setTitles(TITLES);
        mViewpager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        mViewpager.addOnPageChangeListener(mOnPageChangeListener);
        initView();
    }

    private void initView() {
        mIdStickynavlayoutContainer.post(new Runnable() {
            @Override
            public void run() {
                Rect rect = new Rect();
                mViewpager.getWindowVisibleDisplayFrame(rect);
                int scHeight = rect.bottom;
                Log.i(BHomeStickNavLayout.TAG,"Container=" + scHeight);

                //状态栏高度
                int mStatusHeight = getStatusHeight(MainActivity.this);
//                LogUtils.i("mStatusHeight=" + mStatusHeight);

                int indicatorHeight = dip2px(50F);
                Log.i(BHomeStickNavLayout.TAG,"Indicator=" + indicatorHeight);

                ViewGroup.LayoutParams params = mViewpager.getLayoutParams();
                params.height = scHeight - (indicatorHeight + mStatusHeight);
                Log.i(BHomeStickNavLayout.TAG,"vp.height=" + params.height);

                Log.i(BHomeStickNavLayout.TAG,"mIdStickynavlayoutIndicator.top=" +  mIdStickynavlayoutIndicator.getTop());

                mViewpager.setLayoutParams(params);
                mIdStickynavlayoutContainer.forceLayout();
            }
        });

    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mIdStickynavlayoutIndicator.scroll(position, positionOffset);
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    static String[] TITLES = {"title1", "title2", "title3", "title4", "title5"};

    private static class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return CategoryFragment.newInstance();
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }


    /**
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    public int dip2px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
