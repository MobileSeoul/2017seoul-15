package com.stm.dialog.photo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.common.dao.File;
import com.stm.dialog.photo.presenter.PhotoDialogPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-08-17.
 */

public class PhotoDialogAdapter extends PagerAdapter {
    private PhotoDialogPresenter photoDialogPresenter;
    private List<File> files;
    private Context context;
    private int layout;

    @BindView(R.id.iv_photodialog)
    ImageView iv_photodialog;

    @BindString(R.string.cloud_front_story_image)
    String storyImageUrl;

    public PhotoDialogAdapter(PhotoDialogPresenter photoDialogPresenter, List<File> files, Context context, int layout) {
        this.photoDialogPresenter = photoDialogPresenter;
        this.files = files;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(layout, container, false);
        ButterKnife.bind(this, view);

        File file = files.get(position);
        String fileName = file.getName();
        String fileExt = file.getExt();
        String url = fileName + "." + fileExt;

        Glide.with(context).load(storyImageUrl + url).thumbnail(0.1f).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_photodialog);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
