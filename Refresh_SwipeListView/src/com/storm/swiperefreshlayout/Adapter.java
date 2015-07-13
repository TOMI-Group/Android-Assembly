package com.storm.swiperefreshlayout;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Adapter extends BaseAdapter {

    private Context mContext;
    private List<String> list;
    private SwipeListView mListView;

    public Adapter(Context mContext, List<String> list, SwipeListView mListView) {
        this.mContext = mContext;
        this.list = list;
        this.mListView = mListView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item, null);
        }
        TextView tv = (TextView) ViewHolder.get(convertView, R.id.tv);
        View item_right = ViewHolder.get(convertView, R.id.item_right);
        if (position < getCount()) {// listView添加了FootView 最后一行应该没有
            tv.setText(list.get(position));
        }

        final View cview = convertView;
        LinearLayout.LayoutParams paramRight = new LinearLayout.LayoutParams(
                mListView.getRightViewWidth(), LinearLayout.LayoutParams.MATCH_PARENT);
        item_right.setLayoutParams(paramRight);
        item_right.setTag(position);
        item_right.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v) {
                int pos = (Integer) v.getTag();
                mListView.hiddenRight(getView(pos, cview, parent));// 隐藏删除按钮
                list.remove(pos);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

}
