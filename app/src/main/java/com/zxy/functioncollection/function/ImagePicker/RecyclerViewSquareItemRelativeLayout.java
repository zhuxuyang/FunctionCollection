package com.zxy.functioncollection.function.ImagePicker;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.zxy.functioncollection.util.CommonUtil;

/**
 * Created by Administrator on 2017/2/16 0016.
 *
 */

public class RecyclerViewSquareItemRelativeLayout extends RelativeLayout {
    int mchildWidthSize = 0;

    public RecyclerViewSquareItemRelativeLayout(Context context) {
        super(context);
        mchildWidthSize = CommonUtil.getScreenWidth()/4-20;
    }

    public RecyclerViewSquareItemRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mchildWidthSize = CommonUtil.getScreenWidth()/4-20;
    }

    public RecyclerViewSquareItemRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mchildWidthSize = CommonUtil.getScreenWidth()/4-20;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = mchildWidthSize;
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
