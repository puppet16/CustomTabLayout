package com.luck.customtablayout;

/*************************************************************************************
 * Module Name:
 * Description:
 * Author: 李桐桐
 * Date:   2019/3/18
 *************************************************************************************/

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TestFragment extends Fragment {

    RecyclerView mRecyclerView;
    private static final String KEY = "key";
    private String title = "测试";
    SimpleRecyclerViewAdapter mAdapter;
    List<String> mDatas = new ArrayList<>();

    public static TestFragment newInstance(String title) {
        TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            title = arguments.getString(KEY);
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);

        for (int i = 0; i < 50; i++) {
            String s = String.format("我是第%d个" + title, i);
            mDatas.add(s);
        }

        mAdapter = new SimpleRecyclerViewAdapter(getContext(), mDatas);
        mRecyclerView.setAdapter(mAdapter);

    }

    public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<BookViewHolder> {

        Context context;
        List<String> list;

        public SimpleRecyclerViewAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_string, parent, false);
            BookViewHolder viewHolder = new BookViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(BookViewHolder holder, int position) {
            holder.textview.setText(list.get(position));

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    /**
     * RecyclerView 的 ViewHolder 实现类
     */
    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView textview;

        public BookViewHolder(View itemView) {
            super(itemView);
            textview = (TextView) itemView.findViewById(R.id.tv);
        }
    }

}

