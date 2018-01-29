package com.stm.market.fragment.main.presenter.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.market.fragment.main.adapter.MarketMainStoryAdapter;
import com.stm.market.fragment.main.interactor.MarketMainInteractor;
import com.stm.market.fragment.main.interactor.impl.MarketMainInteractorImpl;
import com.stm.market.fragment.main.presenter.MarketMainPresenter;
import com.stm.market.fragment.main.view.MarketMainView;

import java.util.List;

/**
 * Created by Dev-0 on 2017-07-03.
 */

public class MarketMainPresenterImpl implements MarketMainPresenter {
    private MarketMainView marketMainView;
    private MarketMainInteractor marketMainInteractor;

    public MarketMainPresenterImpl(MarketMainView marketMainView) {
        this.marketMainView = marketMainView;
        this.marketMainInteractor = new MarketMainInteractorImpl(this);
    }

    @Override
    public void init(User user, Market market) {
        marketMainView.showProgressDialog();

        marketMainInteractor.setUser(user);
        marketMainInteractor.setMarket(market);

        if (user != null) {
            String accessToken = user.getAccessToken();
            marketMainInteractor.setFileRepository(accessToken);
            marketMainInteractor.setMarketRepository(accessToken);
            marketMainInteractor.setStoryRepository(accessToken);
        } else {
            marketMainInteractor.setFileRepository();
            marketMainInteractor.setMarketRepository();
            marketMainInteractor.setStoryRepository();
        }
    }

    @Override
    public void onCreateView() {
        User user = marketMainInteractor.getUser();
        Market market = marketMainInteractor.getMarket();
        long marketId = market.getId();
        long offset = InfiniteScrollFlag.DEFAULT_OFFSET;
        marketMainInteractor.getFileListByIdAndTypeAndOffset(marketId, DefaultFileFlag.PHOTO_TYPE, offset);
        marketMainInteractor.getFileListByIdAndTypeAndOffset(marketId, DefaultFileFlag.VIDEO_THUMBNAIL_TYPE, offset);

        marketMainView.setScrollViewOnScrollChangeListener();
        marketMainView.setPhotoRecyclerViewOnScrollListener();

        marketMainInteractor.setFirstCreated(false);
        if (user != null) {
            long userId = user.getId();
            marketMainInteractor.getMarketById(marketId, userId);
            marketMainInteractor.getStoryListByIdAndOffset(marketId, userId, offset);
        } else {
            marketMainInteractor.getMarketById(marketId);
            marketMainInteractor.getStoryListByIdAndOffset(marketId, offset);
        }

    }

    @Override
    public void onClickPhoneText(String phone) {
        marketMainView.navigateToDial(phone);
    }

    @Override
    public void onClickHomepageText(String homepage) {
        marketMainView.navigateToWeb(homepage);
    }

    @Override
    public void onClickPhoto(File file) {
        marketMainView.showProgressDialog();
        marketMainInteractor.updateFileByHits(file);
    }

    @Override
    public void onClickPhotoLayout(File file, int position) {
        marketMainView.showProgressDialog();
        List<File> files = marketMainInteractor.getPhotos();
        marketMainView.navigateToPhotoDialogActivity(files, position);
        marketMainView.goneProgressDialog();
    }

    @Override
    public void onClickVideo(File file) {
        marketMainView.showProgressDialog();
        marketMainInteractor.updateFileByHits(file);
    }

    @Override
    public void onPageSelected(int position) {
        List<File> videos = marketMainInteractor.getVideos();
        int offset = videos.size();

        if (position == offset - 1) {
            marketMainView.showProgressDialog();
            Market market = marketMainInteractor.getMarket();
            long marketId = market.getId();
            marketMainInteractor.getFileListByIdAndTypeAndOffset(marketId, DefaultFileFlag.VIDEO_THUMBNAIL_TYPE, offset);
        }
    }

    @Override
    public void onFilePageSelected(File file) {
        marketMainInteractor.updateFileByHits(file);
    }

    @Override
    public void onClickPlayer(File file) {
        marketMainView.showProgressDialog();
        marketMainInteractor.updateFileByHits(file);
    }

    @Override
    public void onClickComment(Story story, int position) {
        marketMainView.navigateToCommentActivity(story, position);
    }

    @Override
    public void onLikeChecked(MarketMainStoryAdapter.MarketMainStoryViewHolder holder, int position, Story story, boolean checked) {
        long storyId = story.getId();

        User user = marketMainInteractor.getUser();
        if (user != null) {
            long userId = user.getId();
            String userName = user.getName();
            User storyUser = story.getUser();
            long storyUserId = storyUser.getId();

            if (checked) {
                marketMainInteractor.setStoryLikeByIdAndStoryUserIdAndUserId(storyId, storyUserId, userId, userName, position);
            } else {
                marketMainInteractor.deleteStoryLikeByIdAndStoryUserIdAndUserId(storyId, userId, position);
            }

        } else {
            marketMainView.navigateToLoginActivity();
            marketMainView.showMessage("로그인이 필요한 서비스입니다.");
            marketMainView.setMarketMainStoryAdapterLikeUnchecked(holder);
        }
    }

    @Override
    public void onClickMenu(Story story, int position) {
        User storyUser = story.getUser();
        Market market = marketMainInteractor.getMarket();
        storyUser.setMarket(market);
        marketMainView.navigateToStoryDialogActivity(story, position);
    }

    @Override
    public void onClickAvatar(User storyUser) {
        marketMainView.navigateToMerchantDetailActivity(storyUser);
    }

    @Override
    public void onHashTagClicked(String hashTag) {
        marketMainView.navigateToSearchTagActivity(hashTag);
    }

    @Override
    public void setFileAdapterItem(MarketMainStoryAdapter.MarketMainStoryViewHolder holder, List<File> files) {
        int fileSize = files.size();

        if (fileSize > 1) {
            marketMainView.showFile(holder);
            marketMainView.setMarketMainFileAdapterItem(holder, files);
            marketMainView.showPosition(holder);
            marketMainView.showIndicator(holder);
        } else if (fileSize == 1) {
            marketMainView.showFile(holder);
            marketMainView.setMarketMainFileAdapterItem(holder, files);
            marketMainView.gonePosition(holder);
            marketMainView.goneIndicator(holder);

        } else {
            marketMainView.goneFile(holder);
            marketMainView.goneIndicator(holder);
        }
    }

    @Override
    public void onActivityResultForCommentResultOk(int position, int commentCountAdded) {
        List<Story> stories = marketMainInteractor.getStories();
        Story story = stories.get(position);
        int commentCount = story.getCommentCount();
        story.setCommentCount(commentCount + commentCountAdded);
        story.setFirstLikeChecked(false);
        marketMainView.marketMainStoryAdapterNotifyItemChanged(position);
    }

    @Override
    public void onActivityResultForStoryResultDelete(int position) {
        marketMainInteractor.setStoriesRemoveAtThePosition(position);
        marketMainView.marketMainStoryAdapterNotifyItemRemoved(position);
    }

    @Override
    public void onActivityResultForStoryEditResultOk(Story story, int position) {
        marketMainInteractor.replaceStoriesItemAtThePosition(position, story);
        marketMainView.marketMainStoryAdapterNotifyItemChanged(position);
    }


    @Override
    public void onSuccessGetMarketById(Market market) {
        int storeCount = market.getStoreCount();
        int merchantCount = market.getUserCount();
        int followCount = market.getFollowerCount();
        String phone = market.getPhone();
        String homepage = market.getHomepage();
        String subname = market.getSubName();
        String items = market.getTitleItems();

        marketMainView.showStoreText(storeCount + "개의 점포가 있습니다.");
        marketMainView.showMerchantText(merchantCount + "명의 상인이 있습니다.");
        marketMainView.showFollowText(followCount + "명의 팔로워가 있습니다.");

        if (phone.length() > 0) {
            marketMainView.showPhone();
            marketMainView.showPhoneText(phone);
            return;
        }

        if (homepage.length() > 0) {
            marketMainView.showHomepage();
            marketMainView.showHomepageText(homepage);
            return;
        }

        if (subname.length() > 0) {
            marketMainView.showSubname();
            marketMainView.showSubnameText(subname);
            return;
        }

        if (items.length() > 0) {
            marketMainView.showItems();
            marketMainView.showItemsText(items);
            return;
        }

    }

    @Override
    public void onSuccessGetFileListByIdAndTypeAndOffset(List<File> newFiles, int type) {
        int newFileSize = newFiles.size();

        if (type == DefaultFileFlag.PHOTO_TYPE) {
            List<File> photos = marketMainInteractor.getPhotos();
            int photoSize = photos.size();
            if (photoSize == 0) {
                if (newFileSize == 0) {
                    marketMainView.gonePhoto();
                } else {
                    marketMainInteractor.setPhotos(newFiles);
                    marketMainView.clearMarketMainPhotoAdapter();
                    marketMainView.setMarketMainPhotoAdapterItem(newFiles);
                }

            } else {
                marketMainInteractor.setPhotosAddAll(newFiles);
                marketMainView.marketMainPhotoAdapterNotifyItemRangeInserted(photoSize, newFileSize);
            }

        }

        if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
            List<File> videos = marketMainInteractor.getVideos();
            int videoSize = videos.size();
            if (videoSize == 0) {
                if (newFileSize == 0) {
                    marketMainView.goneVideo();
                } else {
                    marketMainInteractor.setVideos(newFiles);
                    marketMainView.clearMarketMainVideoAdapter();
                    marketMainView.setMarketMainVideoAdapterItem(newFiles);
                }
            } else {
                marketMainInteractor.setVideosAddAll(newFiles);
                marketMainView.videoAdapterNotifyDataSetChanged();
            }
        }

        marketMainView.goneProgressDialog();
    }

    @Override
    public void onSuccessGetStoryListByIdAndOffset(List<Story> newStories) {
        int newStorySize = newStories.size();
        List<Story> stories = marketMainInteractor.getStories();
        int storySize = stories.size();


        if (storySize == 0) {
            marketMainInteractor.setStories(newStories);
            marketMainView.clearMarketMainStoryAdapter();
            marketMainView.setMarketMainStoryAdapterItem(newStories);
        } else {
            marketMainInteractor.setStoriesAddAll(newStories);
            marketMainView.marketMainStoryAdapterNotifyItemRangeInserted(storySize, newStorySize);
        }

        marketMainView.goneProgressDialog();
        marketMainInteractor.setFirstCreated(true);

    }

    @Override
    public void onSuccessUpdateFileByHits(File file) {
        int type = file.getType();

        if (type == DefaultFileFlag.PHOTO_TYPE) {
            marketMainView.navigateToPhotoDialogActivity(file);
        }

        if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
            marketMainView.navigateToVideoPlayerActivity(file);
        }

        marketMainView.goneProgressDialog();
    }

    @Override
    public void onSuccessUpdateFileByHits(int position) {
        List<File> files = marketMainInteractor.getPhotos();
        marketMainView.navigateToPhotoDialogActivity(files, position);
        marketMainView.goneProgressDialog();
    }

    @Override
    public void onSuccessSetStoryLikeByIdAndStoryUserIdAndUserId(int position) {
        List<Story> stories = marketMainInteractor.getStories();
        Story story = stories.get(position);

        int commentCount = story.getCommentCount();
        int likeCount = story.getLikeCount();
        story.setLikeCount(likeCount + 1);
        story.setCommentCount(commentCount);
        story.setFirstLikeChecked(false);
        story.setLikeChecked(true);
        marketMainView.marketMainStoryAdapterNotifyItemChanged(position);
    }

    @Override
    public void onSuccessDeleteStoryLikeByIdAndStoryUserIdAndUserId(int position) {
        List<Story> stories = marketMainInteractor.getStories();
        Story story = stories.get(position);

        int commentCount = story.getCommentCount();
        int likeCount = story.getLikeCount();
        story.setLikeCount(likeCount - 1);
        story.setCommentCount(commentCount);
        story.setFirstLikeChecked(false);
        story.setLikeChecked(false);
        marketMainView.marketMainStoryAdapterNotifyItemChanged(position);
    }

    @Override
    public void onScrolled(int scrollOffset, int scrollRange, int scrollExtent) {
        int invisibleExtent = (scrollRange - scrollExtent);

        if (invisibleExtent > 0) {
            int difference = scrollOffset / invisibleExtent;
            if (difference > 0) {
                marketMainView.showProgressDialog();
                Market market = marketMainInteractor.getMarket();
                long marketId = market.getId();

                List<File> photos = marketMainInteractor.getPhotos();
                int offset = photos.size();

                marketMainInteractor.getFileListByIdAndTypeAndOffset(marketId, DefaultFileFlag.PHOTO_TYPE, offset);
            }
        }
    }

    @Override
    public void onScrollChange(int difference) {
        if (difference <= 40) {
            marketMainView.showProgressDialog();
            Market market = marketMainInteractor.getMarket();
            long marketId = market.getId();

            List<Story> stories = marketMainInteractor.getStories();
            long offset = stories.size();

            User user = marketMainInteractor.getUser();

            boolean isFirstCreated = marketMainInteractor.isFirstCreated();
            if(isFirstCreated) {
                if (user != null) {
                    long userId = user.getId();
                    marketMainInteractor.getStoryListByIdAndOffset(marketId, userId, offset);
                } else {
                    marketMainInteractor.getStoryListByIdAndOffset(marketId, offset);
                }
            }
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            marketMainView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            marketMainView.showMessage(httpErrorDto.message());
        }
    }
}
