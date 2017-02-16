package com.zxy.functioncollection;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zxy.functioncollection.function.ImagePicker.ImagePickerActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ListView mListView;
    private ArrayList<String> mFunctionNameList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listview_main);
    }

    @Override
    protected void initData() {
        mFunctionNameList = new ArrayList<>();
        mFunctionNameList.add("0图片选择");

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, mFunctionNameList);
        mListView.setAdapter(mAdapter);

    }

    @Override
    protected void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                 case   0:
                     ImagePickerActivity.start(MainActivity.this);
                    break;
                }
            }
        });
    }


}
