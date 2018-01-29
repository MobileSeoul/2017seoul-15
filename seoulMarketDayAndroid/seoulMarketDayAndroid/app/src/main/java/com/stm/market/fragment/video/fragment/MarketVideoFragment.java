package com.stm.market.fragment.video.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.stm.R;
import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.util.ToastUtil;
import com.stm.market.fragment.video.adapter.MarketVideoAdapter;
import com.stm.market.fragment.video.presenter.MarketVideoPresenter;
import com.stm.market.fragment.video.presenter.impl.MarketVideoPresenterImpl;
import com.stm.market.fragment.video.view.MarketVideoView;
import com.stm.media.video.player.activity.VideoPlayerActivity;
import com.stm.repository.local.SharedPrefersManager;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-06-23.
 */

public class MarketVideoFragment extends Fragment implements MarketVideoView, NestedScrollView.OnScrollChangeListener {
    private MarketVideoPresenter marketVideoPresenter;
    private Context context;
    private ToastUtil toastUtil;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private SharedPrefersManager sharedPrefersManager;
    private MarketVideoAdapter marketVideoAdapter;

    @BindView(R.id.nsv_marketvideo)
    NestedScrollView nsv_marketvideo;

    @BindView(R.id.ll_marketvideo_empty)
    LinearLayout ll_marketvideo_empty;

    @BindView(R.id.rv_marketvideo)
    RecyclerView rv_marketvideo;

    @BindString(R.string.cloud_front_story_video)
    String storyVideoUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity();
        this.toastUtil = new ToastUtil(context);
        this.progressDialog = new ProgressDialog(context);
        this.progressDialogHandler = new Handler();
        this.sharedPrefersManager = new SharedPrefersManager(context);

        User user = sharedPrefersManager.getUser();
        Market market = (Market) getArguments().getSerializable("market");

        this.marketVideoPresenter = new MarketVideoPresenterImpl(this);
        this.marketVideoPresenter.init(user, market);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_marketvideo, container, false);
        ButterKnife.bind(this, view);

        marketVideoPresenter.onCreateView();
        return view;
    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(context);
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
        }, 400);
    }

    @Override
    public void setScrollViewOnScrollChangeListener() {
        nsv_marketvideo.setOnScrollChangeListener(this);
    }

    @Override
    public void showEmptyView() {
        ll_marketvideo_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void setMarketVideoAdapterItem(List<File> files) {
        marketVideoAdapter = new MarketVideoAdapter(marketVideoPresenter, files, context, R.layout.item_marketvideo);
        rv_marketvideo.setAdapter(marketVideoAdapter);
        rv_marketvideo.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void clearMarketVideoAdapter() {
        if (marketVideoAdapter != null) {
            marketVideoAdapter = null;
        }
    }

    @Override
    public void marketVideoAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (marketVideoAdapter != null) {
            marketVideoAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void marketVideoAdapterNotifyItemChanged(int position) {
        if (marketVideoAdapter != null) {
            marketVideoAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void navigateToVideoPlayerActivity(File file) {
        String uri = storyVideoUrl + file.getName() + "." + DefaultFileFlag.VIDEO_EXT;
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra("uri", uri);
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
        int difference = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
        marketVideoPresenter.onScrollChange(difference);
    }
}
