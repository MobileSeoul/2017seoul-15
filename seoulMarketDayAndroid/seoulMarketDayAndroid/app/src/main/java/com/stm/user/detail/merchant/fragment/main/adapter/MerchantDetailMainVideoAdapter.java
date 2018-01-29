package com.stm.user.detail.merchant.fragment.main.adapter;

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
import com.stm.user.detail.merchant.fragment.main.presenter.MerchantDetailMainPresenter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-01.
 */

public class MerchantDetailMainVideoAdapter extends PagerAdapter {
    private MerchantDetailMainPresenter merchantDetailMainPresenter;
    private List<File> files;
    private Context context;
    private int layout;

    @BindView(R.id.iv_merchantdetailmain_video)
    ImageView iv_merchantdetailmain_video;

    @BindString(R.string.cloud_front_story_video)
    String storyVideoUrl;

    public MerchantDetailMainVideoAdapter(MerchantDetailMainPresenter merchantDetailMainPresenter, List<File> files, Context context, int layout) {
        this.merchantDetailMainPresenter = merchantDetailMainPresenter;
        this.files = files;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(layout, container, false);
        ButterKnife.bind(this, view);

        final File file = files.get(position);
        String fileName = file.getName();
        String fileExt = file.getExt();
        String uri = storyVideoUrl + fileName + "." + fileExt;
        Glide.with(context).load(uri).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_merchantdetailmain_video);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merchantDetailMainPresenter.onClickVideo(file);
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
