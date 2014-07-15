package com.easyjp.hellowatch;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyListItemLayout extends LinearLayout implements WearableListView.Item {

    private final float mFadedTextAlpha;
    private final int mFadedCircleColor;
    private final int mChosenCircleColor;
    private ImageView mCircle;
    private float mScale;
    private TextView mName;

    public MyListItemLayout(Context context) {
        this(context, null);
    }

    public MyListItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mFadedTextAlpha = 0.5f;
        mFadedCircleColor = Color.LTGRAY;
        mChosenCircleColor = Color.BLUE;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
      //  mCircle = (ImageView) findViewById(R.id.circle);
        mName = (TextView) findViewById(R.id.name);
    }

    @Override
    public float getProximityMinValue() {
        return 1f;
    }

    @Override
    public float getProximityMaxValue() {
        return 1.6f;
    }

    @Override
    public float getCurrentProximityValue() {
        return mScale;
    }

    @Override
    public void setScalingAnimatorValue(float scale) {
        mScale = scale;
        mCircle.setScaleX(scale);
        mCircle.setScaleY(scale);
    }

    @Override
    public void onScaleUpStart() {
        mName.setAlpha(1f);
        ((GradientDrawable) mCircle.getDrawable()).setColor(mChosenCircleColor);
    }

    @Override
    public void onScaleDownStart() {
        ((GradientDrawable) mCircle.getDrawable()).setColor(mFadedCircleColor);
        mName.setAlpha(mFadedTextAlpha);
    }
}