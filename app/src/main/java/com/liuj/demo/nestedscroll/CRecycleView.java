package com.liuj.demo.nestedscroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class CRecycleView extends RecyclerView {
    public CRecycleView(Context context) {
        this(context, null);
    }

    public CRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {
        this.setTag("recycleview");
    }

}
