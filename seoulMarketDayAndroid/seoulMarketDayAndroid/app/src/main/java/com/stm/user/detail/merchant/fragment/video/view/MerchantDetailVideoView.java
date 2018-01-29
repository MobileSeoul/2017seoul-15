package com.stm.user.detail.merchant.fragment.video.view;

import com.stm.common.dao.File;
import com.stm.common.dao.User;
import com.stm.user.detail.merchant.fragment.video.adapter.MerchantDetailVideoAdapter;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-04.
 */

public interface MerchantDetailVideoView {
    void showMessage(String message);

    void showEmptyView();

    void clearMerchantDetailVideoAdapter();

    void setScrollViewScrollChangedListener();

    void setMerchantDetailVideoAdapterItem(List<File> files);


    void videoAdapterNotifyItemRangeInserted(int startPosition, int itemCount);

    void videoAdapterNotifyItemChanged(int position);

    void showProgressDialog();

    void goneProgressDialog();

    void navigateToVideoPlayerActivity(File file);

}
