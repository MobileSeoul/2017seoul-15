package com.stm.market.fragment.main.view;

import com.stm.R;
import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.market.fragment.main.adapter.MarketMainStoryAdapter;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by Dev-0 on 2017-07-03.
 */

public interface MarketMainView {

    void showStoreText(String message);

    void showMerchantText(String message);

    void showFollowText(String message);

    void showPhoneText(String message);

    void showHomepageText(String message);

    void showSubnameText(String message);

    void showItemsText(String message);

    void showPhone();

    void showHomepage();

    void showSubname();

    void showItems();

    void gonePhone();

    void goneHomepage();

    void goneSubname();

    void goneItems();

    void gonePhoto();

    void goneVideo();

    void onClickPhoneText();

    void onClickHomepageText();

    void onClickMoreInfo();

    void navigateToWeb(String message);

    void navigateToDial(String message);

    void navigateToPhotoDialogActivity(File file);

    void navigateToVideoPlayerActivity(File file);

    void navigateToPhotoDialogActivity(List<File> files, int position);

    void showMessage(String message);

    void marketMainStoryAdapterNotifyItemRangeInserted(int startPosition, int itemCount);

    void marketMainPhotoAdapterNotifyItemRangeInserted(int photoSize, int newFileSize);

    void marketMainStoryAdapterNotifyItemChanged(int position);

    void videoAdapterNotifyDataSetChanged();

    void marketMainStoryAdapterNotifyItemRemoved(int position);

    void setMarketMainPhotoAdapterItem(List<File> files);

    void clearMarketMainPhotoAdapter();

    void setScrollViewOnScrollChangeListener();

    void setPhotoRecyclerViewOnScrollListener();

    void showProgressDialog();

    void goneProgressDialog();

    void clearMarketMainVideoAdapter();

    void setMarketMainVideoAdapterItem(List<File> files);


    void onClickMorePhoto();

    void onClickMoreVideo();

    void clearMarketMainStoryAdapter();

    void setMarketMainStoryAdapterItem(List<Story> stories);

    void setMarketMainFileAdapterItem(MarketMainStoryAdapter.MarketMainStoryViewHolder holder, List<File> files);

    void setMarketMainStoryAdapterLikeUnchecked(MarketMainStoryAdapter.MarketMainStoryViewHolder holder);

    void showPosition(MarketMainStoryAdapter.MarketMainStoryViewHolder holder);

    void gonePosition(MarketMainStoryAdapter.MarketMainStoryViewHolder holder);

    void showIndicator(MarketMainStoryAdapter.MarketMainStoryViewHolder holder);

    void goneIndicator(MarketMainStoryAdapter.MarketMainStoryViewHolder holder);

    void showFile(MarketMainStoryAdapter.MarketMainStoryViewHolder holder);

    void goneFile(MarketMainStoryAdapter.MarketMainStoryViewHolder holder);

    void navigateToLoginActivity();

    void navigateToCommentActivity(Story story, int position);

    void navigateToSearchTagActivity(String hashTag);

    void navigateToStoryDialogActivity(Story story, int position);

    void navigateToMerchantDetailActivity(User storyUser);


}
