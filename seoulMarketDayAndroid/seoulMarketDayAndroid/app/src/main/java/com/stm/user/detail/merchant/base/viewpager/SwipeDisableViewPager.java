package com.stm.user.detail.merchant.base.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ㅇㅇ on 2017-05-26.
 */

public class SwipeDisableViewPager extends ViewPager {
    public SwipeDisableViewPager(Context context) {
        super(context);
    }

    public SwipeDisableViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }
}
