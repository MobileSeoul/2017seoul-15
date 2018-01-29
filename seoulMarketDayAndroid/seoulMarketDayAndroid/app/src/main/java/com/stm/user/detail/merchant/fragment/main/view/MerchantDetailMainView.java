package com.stm.user.detail.merchant.fragment.main.view;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.user.detail.merchant.fragment.main.adapter.MerchantDetailMainStoryAdapter;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public interface MerchantDetailMainView {
    void onClickMoreInfo();

    void onClickMorePhoto();

    void onClickMoreVideo();

    void goneIntro();

    void gonePhone();

    void goneFollow();

    void showIntroText(String message);

    void showPhoneText(String message);

    void showFollowText(String message);

    void showMessage(String message);

    void gonePhoto();

    void goneVideo();

    void setScrollViewOnScrollChangeListener();


    void setPhotoRecyclerViewOnScrollListener();

    void navigateToVideoPlayerActivity(File file);

    void showProgressDialog();

    void goneProgressDialog();

    void clearMerchantDetailMainStoryAdapter();

    void clearMerchantDetailMainPhotoAdapter();

    void clearMerchantDetailMainVideoAdapter();

    void setMerchantDetailMainStoryAdapterItem(List<Story> stories);

    void setMerchantDetailMainPhotoAdapterItem(List<File> files);

    void setMerchantDetailMainVideoAdapterItem(List<File> files);

    void storyAdapterNotifyItemRangeInserted(int startPosition, int itemCount);

    void storyAdapterNotifyItemChanged(int position);

    void storyAdapterNotifyItemRemoved(int position);

    void photoAdapterNotifyItemRangeInserted(int startPosition, int itemCount);

    void videoAdapterNotifyDataSetChanged();

    void navigateToCommentActivity(Story story, int position);

    void setMerchantDetailMainFileAdapterItem(MerchantDetailMainStoryAdapter.MerchantDetailStoryViewHolder holder, List<File> files);


    void goneFile(MerchantDetailMainStoryAdapter.MerchantDetailStoryViewHolder holder);

    void gonePosition(MerchantDetailMainStoryAdapter.MerchantDetailStoryViewHolder holder);

    void goneIndicator(MerchantDetailMainStoryAdapter.MerchantDetailStoryViewHolder holder);

    void navigateToLoginActivity();

    void navigateToPhotoDialogActivity(File file);

    void navigateToPhotoDialogActivity(List<File> files, int position);

    void navigateToStoryDialogActivity(Story story, int position);

    void navigateToSearchTagActivity(String tagName);


    void showFile(MerchantDetailMainStoryAdapter.MerchantDetailStoryViewHolder holder);
}
