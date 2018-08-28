package com.liuj.demo.nestedscroll;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class MyViewPage extends ViewPager {
    public MyViewPage(Context context) {
        this(context, null);
    }

    public MyViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        this.setId(R.id.id_vp);
        this.setTag("view_page");
    }

}