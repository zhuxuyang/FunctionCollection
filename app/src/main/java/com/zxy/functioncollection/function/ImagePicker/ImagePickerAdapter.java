package com.zxy.functioncollection.function.ImagePicker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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

    private Activity mActivity;

    private LayoutInflater mInflater;

    private List<Bitmap> mDatas;

    public final static int IMAGE_FROM_GALLERY = 100;

    public final static int IMAGE_FROM_CAMERA = 101;

    private Uri mPhotoUri; // 照相返回取不到data,

    private int mLimit, mScreenWidth;

    public ImagePickerAdapter(Activity activity, List<Bitmap> datas, int limit) {
        mInflater = LayoutInflater.from(activity);
        mActivity = activity;
        mDatas = datas;
        mDatas.add(null);
        mLimit = limit;
        mScreenWidth = CommonUtil.getScreenWidth();
    }

    public void addData(Bitmap bitmap) {
        int i = mDatas.size() - 1;
        mDatas.add(i, bitmap);
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

            holder.mImage.setImageBitmap(mDatas.get(i));
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
        final String items[] = {"相册", "拍照"};
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);  //先得到构造器
        builder.setTitle("从哪里获取图片"); //设置标题
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (which == 0) {    //相册
                    togallery();
                } else {    //拍照
                    takephotos();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
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
