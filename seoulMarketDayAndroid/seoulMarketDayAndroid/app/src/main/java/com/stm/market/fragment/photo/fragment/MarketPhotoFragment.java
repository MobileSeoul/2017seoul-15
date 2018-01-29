package com.stm.market.fragment.photo.fragment;

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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.stm.R;
import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.photo.activity.PhotoDialogActivity;
import com.stm.market.fragment.photo.adapter.MarketPhotoAdapter;
import com.stm.market.fragment.photo.presenter.MarketPhotoPresenter;
import com.stm.market.fragment.photo.presenter.impl.MarketPhotoPresenterImpl;
import com.stm.market.fragment.photo.view.MarketPhotoView;
import com.stm.repository.local.SharedPrefersManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-06-23.
 */

public class MarketPhotoFragment extends Fragment implements MarketPhotoView, NestedScrollView.OnScrollChangeListener {
    private MarketPhotoPresenter marketPhotoPresenter;
    private Context context;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private MarketPhotoAdapter marketPhotoAdapter;
    private GridLayoutManager gridLayoutManager;

    @BindView(R.id.nsv_marketphoto)
    NestedScrollView nsv_marketphoto;

    @BindView(R.id.ll_marketphoto_empty)
    LinearLayout ll_marketphoto_empty;

    @BindView(R.id.rv_marketphoto)
    RecyclerView rv_marketphoto;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity();
        this.toastUtil = new ToastUtil(context);
        this.sharedPrefersManager = new SharedPrefersManager(context);
        this.progressDialog = new ProgressDialog(context);
        this.progressDialogHandler = new Handler();

        Market market = (Market) getArguments().getSerializable("market");
        User user = sharedPrefersManager.getUser();

        this.marketPhotoPresenter = new MarketPhotoPresenterImpl(this);
        this.marketPhotoPresenter.init(user, market);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_marketphoto, container, false);
        ButterKnife.bind(this, view);

        marketPhotoPresenter.onCreateView();
        return view;
    }

    @Override
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
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
    public void showEmptyView() {
        ll_marketphoto_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void navigateToPhotoDialogActivity(List<File> files, int position) {
        Intent intent = new Intent(context, PhotoDialogActivity.class);
        intent.putParcelableArrayListExtra("files", (ArrayList) files);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void clearMarketPhotoAdapter() {
        if (marketPhotoAdapter != null) {
            marketPhotoAdapter = null;
        }
    }

    @Override
    public void setMarketPhotoAdapterItem(List<File> files) {
        marketPhotoAdapter = new MarketPhotoAdapter(marketPhotoPresenter, files, context, R.layout.item_marketphoto);
        gridLayoutManager = new GridLayoutManager(context, 2);
        rv_marketphoto.setLayoutManager(gridLayoutManager);
        rv_marketphoto.setAdapter(marketPhotoAdapter);
    }

    @Override
    public void setScrollViewOnScrollChangeListener() {
        nsv_marketphoto.setOnScrollChangeListener(this);
    }

    @Override
    public void notifyItemRangeInserted(int startPosition, int itemSize) {
        if (marketPhotoAdapter != null) {
            marketPhotoAdapter.notifyItemRangeInserted(startPosition, itemSize);
        }
    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
        int difference = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
        marketPhotoPresenter.onScrollChange(difference);
    }


}
