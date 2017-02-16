package com.zxy.functioncollection.function.ImagePicker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zxy.functioncollection.BaseActivity;
import com.zxy.functioncollection.R;


public class ImagePickerActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private ImagePickerAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, ImagePickerActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_image_picker);
        mRecyclerView= (RecyclerView) findViewById(R.id.recyclerview_image_picker);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(ImagePickerActivity.this, 4, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

}
