package com.zxy.functioncollection.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class MainAdapter extends BaseAdapter{
    private ArrayList<String> mFunctionNameList;
    private Context mContext;

    public MainAdapter(ArrayList<String> functionNameList, Context context) {
        this.mFunctionNameList = functionNameList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mFunctionNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFunctionNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
