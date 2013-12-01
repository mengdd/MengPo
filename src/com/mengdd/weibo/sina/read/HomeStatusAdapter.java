package com.mengdd.weibo.sina.read;

import java.util.List;

import com.mengdd.hellosina.R;
import com.weibo.sina.android.data.StatusItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeStatusAdapter extends BaseAdapter {

    private Context mContext = null;
    private LayoutInflater mInflater = null;
    private List<StatusItem> mDataList = null;

    public HomeStatusAdapter(Context context, List<StatusItem> data) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mDataList = data;
    }

    public void setData(List<StatusItem> data) {
        mDataList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int count = 0;
        if (null != mDataList) {
            count = mDataList.size();
        }
        return count;
    }

    @Override
    public StatusItem getItem(int position) {
        StatusItem item = null;
        if (null != mDataList) {
            item = mDataList.get(position);
        }
        return item;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.status_item, null);

            viewHolder.icon = (ImageView) convertView
                    .findViewById(R.id.user_icon);
            viewHolder.userName = (TextView) convertView
                    .findViewById(R.id.user_name);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);

            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        StatusItem item = getItem(position);
        if (null != item) {
            viewHolder.userName.setText(item.getUserInfo().getName());
            viewHolder.text.setText(item.getText());
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView icon;
        TextView userName;
        TextView text;
    }

}
