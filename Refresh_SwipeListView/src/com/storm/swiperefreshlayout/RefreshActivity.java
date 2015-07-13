package com.storm.swiperefreshlayout;

/**
 * 下拉刷新 侧滑删除
 */
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

public class RefreshActivity extends Activity {

    private RefreshLayout mSwipeLayout;
    private SwipeListView mListView;
    private ArrayList<String> list = new ArrayList<String>();
    private Adapter adapter;
    private int page;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeLayout = (RefreshLayout) findViewById(R.id.swipe_container);
        mListView = (SwipeListView) findViewById(R.id.listview);
        mListView.setRightViewWidth(108);

        adapter = new Adapter(this, getData(), mListView);

        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mListView.addFooterView(mSwipeLayout.getFootView(), null, false);
        mListView.setOnScrollListener(mSwipeLayout);

        mSwipeLayout.setOnRefreshListener(new OnRefreshListener()
        {

            @Override
            public void onRefresh() {
                mSwipeLayout.setLoading(true);// 关闭上拉
                new Handler().postDelayed(new Runnable()
                {

                    @Override
                    public void run() {
                        list.clear();
                        for (int i = 0; i < 10; i++) {
                            list.add(String.valueOf(i));
                        }
                        adapter.notifyDataSetChanged();
                        page = 1;
                        mSwipeLayout.setRefreshing(false);// 关闭刷新进度条
                        mSwipeLayout.setLoad_More(true);// 还有更多
                        mSwipeLayout.setLoading(false);// 可以上拉
                    }
                }, 5000);

            }
        });
        mSwipeLayout.setOnLoadListener(new RefreshLayout.OnLoadListener()
        {

            @Override
            public void onLoad() {
                new Handler().postDelayed(new Runnable()
                {

                    @Override
                    public void run() {
                        // mSwipeLayout.setRefreshing(false);
                        if (page == 1) {
                            list.clear();
                        }
                        for (int i = 0; i < 10; i++) {
                            list.add(String.valueOf(i));
                        }
                        adapter.notifyDataSetChanged();
                        mSwipeLayout.setLoading(false);// 可以上拉
                        if (page == 3) {
                            mSwipeLayout.setLoad_More(false);// 没有更多了
                        }
                    }
                }, 1500);
                page++;
            }
        });
        mListView.setAdapter(adapter);

    }

    private ArrayList<String> getData() {
        list.add("Hello");
        list.add("This is stormzhang");
        list.add("An Android Developer");
        list.add("Love Open Source");
        list.add("My GitHub: stormzhang");
        list.add("weibo: googdev");

        return list;
    }

}
