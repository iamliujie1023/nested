package com.liuj.demo.nestedscroll;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryFragment extends Fragment {

    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView mViewRv;
    private MyAdapter mAdapter;

    public static CategoryFragment newInstance() {
        Bundle args = new Bundle();
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nested_scrolling_bhome_fragment, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new MyAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mViewRv.setAdapter(mAdapter);
        mViewRv.setLayoutManager(linearLayoutManager);
        mViewRv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        return view;
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        private Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return MyHolder.obtain(context, parent);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.bindData(position);
        }

        @Override
        public int getItemCount() {
            return 20;
        }

    }

    static class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView title;

        static MyHolder obtain(Context context, ViewGroup viewGroup) {
            return new MyHolder(
                    LayoutInflater.from(context).inflate(
                            R.layout.nested_scrolling_bhome_recycle_item,
                            viewGroup,
                            false));
        }

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(int pos) {
//            itemView.setBackgroundColor(ColorUtils.getRandomColor());
            title.setText("item_" + pos);
        }

    }

}
