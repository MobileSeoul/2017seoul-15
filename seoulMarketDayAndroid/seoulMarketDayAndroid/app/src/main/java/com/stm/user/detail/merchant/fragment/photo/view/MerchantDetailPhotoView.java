package com.stm.user.detail.merchant.fragment.photo.view;

import com.stm.common.dao.File;
import com.stm.common.dao.User;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-02.
 */

public interface MerchantDetailPhotoView {
    void showEmptyView();

    void showMessage(String message);

    void showProgressDialog();

    void goneProgressDialog();

    void clearMerchantDetailPhotoAdapter();

    void setMerchantDetailPhotoAdapterItem(List<File> files);

    void notifyItemRangeInserted(int photoSize, int newPhotoSize);

    void setScrollViewOnScrollChangeListener();

    void navigateToPhotoDialogActivity(List<File> file, int position);
}
