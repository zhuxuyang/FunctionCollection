package com.zxy.functioncollection.function.ImagePicker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zxy.functioncollection.R;
import com.zxy.functioncollection.util.CommonUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class ImagePickerAdapter extends RecyclerView.Adapter<ImagePickerAdapter.ViewHolder> {

    private FragmentActivity mActivity;

    private LayoutInflater mInflater;

    private List<String> mDatas;

    public final static int IMAGE_FROM_GALLERY = 100;

    public final static int IMAGE_FROM_CAMERA = 101;

    private Uri mPhotoUri; // 照相返回取不到data,

    private int mLimit, mScreenWidth;

    public ImagePickerAdapter(FragmentActivity act, List<String> datas, int limit) {
        mInflater = LayoutInflater.from(act);
        mActivity = act;
        mDatas = datas;
        mDatas.add("");
        mLimit = limit;
        mScreenWidth = CommonUtil.getScreenWidth();
    }

    public void addData(String imageurl) {
        int i = mDatas.size() - 1;
        mDatas.add(i, imageurl);
        if (i != 8) {
            notifyItemInserted(i);
        } else {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size() <= mLimit ? mDatas.size() : mLimit;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        if (i == mDatas.size() - 1) {
            holder.mDelete.setVisibility(View.GONE);
            holder.mImage.setImageResource(R.mipmap.ic_add_pic);
            holder.mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    addImage();
                }
            });
        } else {

            holder.mImage.setOnClickListener(null);
            holder.mDelete.setVisibility(View.VISIBLE);
            holder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    delImage(i);
                }
            });
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        ViewHolder holder = new ViewHolder(mInflater.inflate(R.layout.adapter_image_picker, viewGroup, false));
        return holder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;

        ImageView mDelete;

        public ViewHolder(View view) {
            super(view);
            mImage = (ImageView) view.findViewById(R.id.imageview);
            mDelete = (ImageView) view.findViewById(R.id.icon_delete);
        }
    }

    private void delImage(final int pos) {
        mDatas.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(0, mDatas.size());
    }



    /**
     * 添加图片选择
     */
    public void addImage() {
//        final DialogListFragment fragment = new DialogListFragment();
//        fragment.setDialogListener(new DialogListener() {
//
//            @Override
//            public void click(View v) {
//                if (v.getId() == R.id.camera) {
//                    takephotos();
//                } else if (v.getId() == R.id.gallery) {
//                    togallery();
//                }
//                fragment.dismiss();
//            }
//        });
//        fragment.showDialog(mActivity);
    }

    private void togallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        mActivity.startActivityForResult(intent, IMAGE_FROM_GALLERY);

    }

    @SuppressLint("SimpleDateFormat")
    private void takephotos() {
        // 存储至DCIM文件夹
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// 获取当前时间，进一步转化为字符串
        date = new Date();
        File outputImage = new File(path, "FunctionCollection" + format.format(date) + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将File对象转换为Uri并启动照相程序
        mPhotoUri = Uri.fromFile(outputImage);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE"); // 照相
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri); // 指定图片输出地址
        mActivity.startActivityForResult(intent, IMAGE_FROM_CAMERA);
    }

    public Uri getLastPhotoUri() {
        return mPhotoUri;
    }


}
