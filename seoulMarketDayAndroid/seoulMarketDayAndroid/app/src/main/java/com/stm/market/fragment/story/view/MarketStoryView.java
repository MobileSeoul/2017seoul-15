package com.stm.market.fragment.story.view;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.market.fragment.story.adapter.MarketStoryAdapter;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-08-24.
 */

public interface MarketStoryView {
    void showMessage(String message);

    void showProgressDialog();

    void goneProgressDialog();

    void showPosition(MarketStoryAdapter.MarketStoryViewHolder holder);

    void showIndicator(MarketStoryAdapter.MarketStoryViewHolder holder);

    void gonePosition(MarketStoryAdapter.MarketStoryViewHolder holder);

    void goneIndicator(MarketStoryAdapter.MarketStoryViewHolder holder);

    void goneFile(MarketStoryAdapter.MarketStoryViewHolder holder);

    void showEmptyView();

    void setMarketStoryFileAdapterItem(MarketStoryAdapter.MarketStoryViewHolder holder, List<File> files);

    void clearMarketStoryAdapter();

    void setMarketStoryAdapterItem(List<Story> stories);

    void marketStoryAdapterNotifyItemRangeInserted(int startPosition, int itemCount);

    void setScrollViewOnScrollChangeListener();

    void navigateToPhotoDialogActivity(File file);

    void navigateToVideoPlayerActivity(File file);

    void navigateToMerchantDetailActivity(User storyUser);

    void navigateToStoryDialogActivity(Story story, int position);

    void navigateToCommentActivity(Story story, int position);

    void navigateToSearchTagActivity(String tagName);

    void navigateToLoginActivity();

    void setMarketStoryAdapterLikeUnchecked(MarketStoryAdapter.MarketStoryViewHolder holder);

    void marketStoryAdapterNotifyItemChanged(int position);

    void marketStoryAdapterNotifyItemRemoved(int position);

    void showFile(MarketStoryAdapter.MarketStoryViewHolder holder);
}
