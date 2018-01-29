package com.stm.user.detail.merchant.fragment.video.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.stm.R;
import com.stm.common.dao.File;
import com.stm.common.dao.User;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.util.ToastUtil;
import com.stm.media.video.player.activity.VideoPlayerActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.detail.merchant.fragment.video.adapter.MerchantDetailVideoAdapter;
import com.stm.user.detail.merchant.fragment.video.presenter.MerchantDetailVideoPresenter;
import com.stm.user.detail.merchant.fragment.video.presenter.impl.MerchantDetailVideoPresenterImpl;
import com.stm.user.detail.merchant.fragment.video.view.MerchantDetailVideoView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-08-04.
 */

public class MerchantDetailVideoFragment extends Fragment implements MerchantDetailVideoView, NestedScrollView.OnScrollChangeListener {
    private MerchantDetailVideoPresenter merchantDetailVideoPresenter;
    private Context context;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private Handler progressDialogHandler;
    private ProgressDialog progressDialog;
    private MerchantDetailVideoAdapter merchantDetailVideoAdapter;

    @BindView(R.id.rv_merchantdetailvideo)
    RecyclerView rv_merchantdetailvideo;

    @BindView(R.id.ll_merchantdetailvideo_empty)
    LinearLayout ll_merchantdetailvideo_empty;

    @BindView(R.id.nsv_merchantdetailvideo)
    NestedScrollView nsv_merchantdetailvideo;

    @BindString(R.string.cloud_front_story_video)
    String storyVideoUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
        this.toastUtil = new ToastUtil(context);
        this.sharedPrefersManager = new SharedPrefersManager(context);
        this.progressDialog = new ProgressDialog(context);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        User storyUser = (User) getArguments().getSerializable("storyUser");

        this.merchantDetailVideoPresenter = new MerchantDetailVideoPresenterImpl(this);
        this.merchantDetailVideoPresenter.init(user, storyUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchantdetailvideo, container, false);
        ButterKnife.bind(this, view);

        merchantDetailVideoPresenter.onCreateView();
        return view;
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void showEmptyView() {
        ll_merchantdetailvideo_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearMerchantDetailVideoAdapter() {
        if (merchantDetailVideoAdapter != null) {
            merchantDetailVideoAdapter = null;
        }
    }

    @Override
    public void setMerchantDetailVideoAdapterItem(List<File> files) {
        this.merchantDetailVideoAdapter = new MerchantDetailVideoAdapter(merchantDetailVideoPresenter, files, context, R.layout.item_merchantdetailvideo);
        rv_merchantdetailvideo.setAdapter(merchantDetailVideoAdapter);
        rv_merchantdetailvideo.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void videoAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (merchantDetailVideoAdapter != null) {
            merchantDetailVideoAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void videoAdapterNotifyItemChanged(int position) {
        if (merchantDetailVideoAdapter != null) {
            merchantDetailVideoAdapter.notifyItemChanged(position);
        }
    }


    @Override
    public void showProgressDialog() {
        progressDialog.show();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_dialog);
    }

    @Override
    public void goneProgressDialog() {
        progressDialogHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }, 10);
    }

    @Override
    public void navigateToVideoPlayerActivity(File file) {
        String uri = storyVideoUrl + file.getName() + "." + DefaultFileFlag.VIDEO_EXT;
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra("uri", uri);
        startActivity(intent);
    }


    @Override
    public void setScrollViewScrollChangedListener() {
        nsv_merchantdetailvideo.setOnScrollChangeListener(this);
    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
        int difference = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
        merchantDetailVideoPresenter.onScrollChange(difference);
    }
}
