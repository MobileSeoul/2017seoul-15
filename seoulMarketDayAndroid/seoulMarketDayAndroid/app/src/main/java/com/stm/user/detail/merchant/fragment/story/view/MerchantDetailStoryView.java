package com.stm.user.detail.merchant.fragment.story.view;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.user.detail.merchant.fragment.main.adapter.MerchantDetailMainStoryAdapter;
import com.stm.user.detail.merchant.fragment.story.adapter.MerchantDetailStoryAdapter;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-07-28.
 */

public interface MerchantDetailStoryView {
    void setScrollViewOnScrollChangeListener();

    void showMessage(String message);

    void showProgressDialog();

    void goneProgressDialog();

    void gonePosition(MerchantDetailStoryAdapter.MerchantDetailStoryViewHolder holder);

    void goneIndicator(MerchantDetailStoryAdapter.MerchantDetailStoryViewHolder holder);

    void goneFile(MerchantDetailStoryAdapter.MerchantDetailStoryViewHolder holder);

    void showFile(MerchantDetailStoryAdapter.MerchantDetailStoryViewHolder holder);

    void showEmptyView();

    void setMerchantDetailStoryFileAdapterItem(MerchantDetailStoryAdapter.MerchantDetailStoryViewHolder holder, List<File> files);

    void navigateToCommentActivity(Story story, int position);

    void navigateToVideoPlayerActivity(File file);

    void clearMerchantDetailStoryAdapter();

    void setStoryByIdAndOffsetItem(List<Story> stories);

    void storyAdapterNotifyItemRangeInserted(int startPosition, int itemCount);

    void navigateToLoginActivity();

    void storyAdapterNotifyItemChanged(int position);

    void storyAdapterNotifyItemRemoved(int position);

    void navigateToStoryDialogActivity(Story story, int position);

    void navigateToPhotoDialogActivity(File file);

    void navigateToSearchTagActivity(String tagName);

    void setStoryRefresh();


}
