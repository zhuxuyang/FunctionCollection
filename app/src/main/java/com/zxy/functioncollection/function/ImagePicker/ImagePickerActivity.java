package com.zxy.functioncollection.function.ImagePicker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zxy.functioncollection.BaseActivity;
import com.zxy.functioncollection.R;
import com.zxy.functioncollection.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;


public class ImagePickerActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private ImagePickerAdapter mAdapter;
    private List<Bitmap> mImageUriList;

    public static void start(Context context) {
        Intent starter = new Intent(context, ImagePickerActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_image_picker);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_image_picker);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(ImagePickerActivity.this, 4, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);


    }

    @Override
    protected void initData() {
        mImageUriList = new ArrayList<>();
        mAdapter = new ImagePickerAdapter(ImagePickerActivity.this, mImageUriList, 9);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri uri = null;
            if (requestCode == ImagePickerAdapter.IMAGE_FROM_GALLERY) {
                uri = data.getData();
            } else if (requestCode == ImagePickerAdapter.IMAGE_FROM_CAMERA) {
                uri = mAdapter.getLastPhotoUri();
            }

            if (uri != null) {
                String imagePath = ImageUtil.getImageAbsolutePath(this, uri);
                int rotate = ImageUtil.getBitmapDegree(imagePath);
                Bitmap bitmap = ImageUtil.getBitmapByPath(imagePath);
                if (rotate != 0) {  //解决某些机型拍的照片角度会旋转
                    bitmap = ImageUtil.rotateBitmapByDegree(bitmap, rotate);
                }
                mAdapter.addData(bitmap);
            }

        }

    }
}
