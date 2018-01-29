package com.stm.user.detail.merchant.fragment.photo.fragment;

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
import com.stm.common.dao.User;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.photo.activity.PhotoDialogActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.user.detail.merchant.fragment.photo.adapter.MerchantDetailPhotoAdapter;
import com.stm.user.detail.merchant.fragment.photo.presenter.MerchantDetailPhotoPresenter;
import com.stm.user.detail.merchant.fragment.photo.presenter.impl.MerchantDetailPhotoPresenterImpl;
import com.stm.user.detail.merchant.fragment.photo.view.MerchantDetailPhotoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-08-02.
 */

public class MerchantDetailPhotoFragment extends Fragment implements MerchantDetailPhotoView, NestedScrollView.OnScrollChangeListener {
    private MerchantDetailPhotoPresenter merchantDetailPhotoPresenter;
    private Context context;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private GridLayoutManager gridLayoutManager;
    private MerchantDetailPhotoAdapter merchantDetailPhotoAdapter;

    @BindView(R.id.rv_merchantdetailphoto)
    RecyclerView rv_merchantdetailphoto;

    @BindView(R.id.nsv_merchantdetailphoto)
    NestedScrollView nsv_merchantdetailphoto;

    @BindView(R.id.ll_merchantdetailphoto_empty)
    LinearLayout ll_merchantdetailphoto_empty;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getContext();
        this.sharedPrefersManager = new SharedPrefersManager(context);
        this.progressDialog = new ProgressDialog(context);
        this.toastUtil = new ToastUtil(context);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        User storyUser = (User) getArguments().getSerializable("storyUser");

        this.merchantDetailPhotoPresenter = new MerchantDetailPhotoPresenterImpl(this);
        this.merchantDetailPhotoPresenter.init(user, storyUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchantdetailphoto, container, false);
        ButterKnife.bind(this, view);

        merchantDetailPhotoPresenter.onCreateView();
        return view;
    }

    @Override
    public void showEmptyView() {
        ll_merchantdetailphoto_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
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
    public void clearMerchantDetailPhotoAdapter() {
        if (merchantDetailPhotoAdapter != null) {
            merchantDetailPhotoAdapter = null;
        }
    }

    @Override
    public void setMerchantDetailPhotoAdapterItem(List<File> files) {
        merchantDetailPhotoAdapter = new MerchantDetailPhotoAdapter(merchantDetailPhotoPresenter, files, context, R.layout.item_merchantdetailphoto);
        gridLayoutManager = new GridLayoutManager(context, 2);
        rv_merchantdetailphoto.setLayoutManager(gridLayoutManager);
        rv_merchantdetailphoto.setAdapter(merchantDetailPhotoAdapter);
    }

    @Override
    public void notifyItemRangeInserted(int startPosition, int itemCount) {
        if (merchantDetailPhotoAdapter != null) {
            merchantDetailPhotoAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void setScrollViewOnScrollChangeListener() {
        nsv_merchantdetailphoto.setOnScrollChangeListener(this);
    }

    @Override
    public void navigateToPhotoDialogActivity(List<File> files, int position) {
        Intent intent = new Intent(context, PhotoDialogActivity.class);
        intent.putExtra("position", position);
        intent.putParcelableArrayListExtra("files", (ArrayList) files);
        startActivity(intent);
    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
        int difference = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
        merchantDetailPhotoPresenter.onScrollChange(difference);
    }

}
