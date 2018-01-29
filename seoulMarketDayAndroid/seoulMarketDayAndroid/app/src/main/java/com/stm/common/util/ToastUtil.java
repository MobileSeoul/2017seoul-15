package com.stm.common.util;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-06-16.
 */

public class ToastUtil {
    private Context context;
    private ToastLayout toastLayout;
    private Toast toast;
    private View view;

    public ToastUtil(Context context) {
        this.context = context;
        setToast();
    }

    public void setToast() {
        toastLayout = new ToastLayout();
        view = LayoutInflater.from(context).inflate(R.layout.all_toast, null, false);
        ButterKnife.bind(toastLayout, view);

        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
    }

    public void showMessage(String message){
        toastLayout.tv_toast.setText(message);
        toast.show();
    }

    public void setToastColor(int color){
        GradientDrawable drawable = (GradientDrawable ) view.getBackground();
        drawable.setColor(color);
    }

    static class ToastLayout {
        @BindView(R.id.tv_toast)
        TextView tv_toast;
    }
}
