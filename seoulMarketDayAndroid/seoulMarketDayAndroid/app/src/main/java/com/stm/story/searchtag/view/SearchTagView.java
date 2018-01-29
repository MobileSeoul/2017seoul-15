package com.stm.story.searchtag.view;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.story.searchtag.adapter.SearchTagStoryAdapter;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public interface SearchTagView {
    void showMessage(String message);

    void showProgressDialog();

    void goneProgressDialog();

    void setSearchTagStoryAdapterItem(List<Story> stories);

    void setSearchTagFileAdapterItem(SearchTagStoryAdapter.SearchTagStoryViewHolder holder, List<File> files);

    void gonePosition(SearchTagStoryAdapter.SearchTagStoryViewHolder holder);

    void goneIndicator(SearchTagStoryAdapter.SearchTagStoryViewHolder holder);

    void goneMedia(SearchTagStoryAdapter.SearchTagStoryViewHolder holder);

    void showEmptyView();

    void setScrollViewOnScrollChangeListener();

    void clearSearchTagStoryAdapter();

    void searchTagStoryAdapterNotifyItemRangeInserted(int storySize, int newStorySize);

    void navigateToVideoPlayerActivity(File file);

    void navigateToPhotoDialogActivity(File file);

    void setToolbarLayout();

    void showToolbarTitle(String message);

    void onClickBack();

    void navigateToBack();

    void navigateToCommentActivity(Story story, int position);

    void navigateToLoginActivity();

    void navigateToMerchantDetailActivity(User user);

    void searchTagStoryAdapterNotifyItemChanged(int position);

    void navigateToStoryDialogActivity(Story story, int position);

    void searchTagStoryAdapterNotifyItemRemoved(int position);

    void setSearchTagAdapterLikeUnChecked(SearchTagStoryAdapter.SearchTagStoryViewHolder holder);
}
