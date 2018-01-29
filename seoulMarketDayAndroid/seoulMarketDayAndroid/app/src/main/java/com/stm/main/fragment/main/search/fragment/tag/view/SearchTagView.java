package com.stm.main.fragment.main.search.fragment.tag.view;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.main.fragment.main.search.fragment.tag.adapter.SearchTagStoryAdapter;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public interface SearchTagView {
    void showMessage(String message);

    void showEmptyView();

    void showProgressDialog();

    void goneProgressDialog();

    void setOnScrollChangeListener();

    void setSearchTagAdapterItem(List<Story> stories);

    void getStoryListByKeyword(String keyword);

    void clearSearchTagAdapter();

    void notifyItemRangeInserted(int startPosition, int itemCount);

    void goneEmptyView();

    void navigateToCommentActivity(Story story, int position);

    void navigateToMerchantDetailActivity(User user);

    void navigateToUserDetailActivity(User user);

    void setSearchTagFileAdapterItem(SearchTagStoryAdapter.SearchTagStoryViewHolder holder, List<File> files);

    void gonePosition(SearchTagStoryAdapter.SearchTagStoryViewHolder holder);

    void goneIndicator(SearchTagStoryAdapter.SearchTagStoryViewHolder holder);

    void goneFile(SearchTagStoryAdapter.SearchTagStoryViewHolder holder);

    void navigateToVideoPlayerActivity(File file);

    void navigateToPhotoDialogActivity(File file);

    void navigateToStoryDialogActivity(Story story, int position);

    void searchTagStoryAdapterNotifyItemRemoved(int position);

    void searchTagStoryAdapterNotifyItemChanged(int position);

    void searchTagStoryAdapterNotifyDataSetChanged();

    void navigateToLoginActivity();

    void setSearchTagAdapterLikeUnChecked(SearchTagStoryAdapter.SearchTagStoryViewHolder holder);

}
