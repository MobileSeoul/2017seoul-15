package com.stm.user.detail.merchant.fragment.main.presenter.impl;

import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.dto.HttpErrorDto;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.InfiniteScrollFlag;
import com.stm.common.flag.UserPrivacyRangeFlag;
import com.stm.user.detail.merchant.fragment.main.adapter.MerchantDetailMainStoryAdapter;
import com.stm.user.detail.merchant.fragment.main.interactor.MerchantDetailMainInteractor;
import com.stm.user.detail.merchant.fragment.main.interactor.impl.MerchantDetailMainInteractorImpl;
import com.stm.user.detail.merchant.fragment.main.presenter.MerchantDetailMainPresenter;
import com.stm.user.detail.merchant.fragment.main.view.MerchantDetailMainView;

import java.util.List;

/**
 * Created by ㅇㅇ on 2017-07-06.
 */

public class MerchantDetailMainPresenterImpl implements MerchantDetailMainPresenter {
    private MerchantDetailMainInteractor merchantDetailMainInteractor;
    private MerchantDetailMainView merchantDetailMainView;

    public MerchantDetailMainPresenterImpl(MerchantDetailMainView merchantDetailMainView) {
        this.merchantDetailMainInteractor = new MerchantDetailMainInteractorImpl(this);
        this.merchantDetailMainView = merchantDetailMainView;
    }

    @Override
    public void init(User user, User storyUser) {
        merchantDetailMainInteractor.setUser(user);
        merchantDetailMainInteractor.setStoryUser(storyUser);

        if (user != null) {
            String accessToken = user.getAccessToken();
            merchantDetailMainInteractor.setUserRepository(accessToken);
            merchantDetailMainInteractor.setStoryRepository(accessToken);
            merchantDetailMainInteractor.setFileRepository(accessToken);
        } else {
            merchantDetailMainInteractor.setUserRepository();
            merchantDetailMainInteractor.setStoryRepository();
            merchantDetailMainInteractor.setFileRepository();
        }
    }

    @Override
    public void onCreateView() {
        merchantDetailMainView.showProgressDialog();

        User storyUser = merchantDetailMainInteractor.getStoryUser();
        long storyUserId = storyUser.getId();

        String intro = storyUser.getIntro();
        String phone = storyUser.getPhone();
        int followCount = storyUser.getFollowerCount();
        String followMessage = followCount + "명의 팔로워가 있습니다.";

        merchantDetailMainView.showFollowText(followMessage);
        merchantDetailMainView.setScrollViewOnScrollChangeListener();
        merchantDetailMainView.setPhotoRecyclerViewOnScrollListener();

        long phoneRangeCategoryId = storyUser.getPhoneRangeCategory().getId();
        Boolean isPhonePublic = (phoneRangeCategoryId == UserPrivacyRangeFlag.PUBLIC);
        if (phone != null) {
            if (isPhonePublic) {
                merchantDetailMainView.showPhoneText(phone);
            } else {
                merchantDetailMainView.gonePhone();
            }
        } else {
            merchantDetailMainView.gonePhone();
        }

        if (intro != null) {
            merchantDetailMainView.showIntroText(intro);
        } else {
            merchantDetailMainView.goneIntro();
        }


        long offset = InfiniteScrollFlag.DEFAULT_OFFSET;
        User user = merchantDetailMainInteractor.getUser();

        merchantDetailMainInteractor.setFirstCreated(false);
        if (user != null) {
            long userId = user.getId();
            merchantDetailMainInteractor.getStoryListByStoryUserIdAndUserIdAndOffset(storyUserId, userId, offset);
        } else {
            merchantDetailMainInteractor.getStoryListByStoryUserIdAndOffset(storyUserId, offset);
        }

        merchantDetailMainInteractor.getFileListByIdAndTypeAndOffset(storyUserId, DefaultFileFlag.PHOTO_TYPE, offset);
        merchantDetailMainInteractor.getFileListByIdAndTypeAndOffset(storyUserId, DefaultFileFlag.VIDEO_THUMBNAIL_TYPE, offset);
    }

    @Override
    public void onHashTagClicked(String tagName) {
        merchantDetailMainView.navigateToSearchTagActivity(tagName);
    }

    @Override
    public void onClickPlayerButton(File file) {
        merchantDetailMainView.showProgressDialog();
        merchantDetailMainInteractor.updateFileByHits(file);
    }

    @Override
    public void onSuccessGetStoryListByStoryUserIdAndOffset(List<Story> newStories) {
        int newStorySize = newStories.size();

        List<Story> stories = merchantDetailMainInteractor.getStories();
        int storySize = stories.size();

        if (storySize == 0) {
            merchantDetailMainInteractor.setStories(newStories);
            merchantDetailMainView.clearMerchantDetailMainStoryAdapter();
            merchantDetailMainView.setMerchantDetailMainStoryAdapterItem(newStories);
        } else {
            merchantDetailMainInteractor.setStoriesAddAll(newStories);
            merchantDetailMainView.storyAdapterNotifyItemRangeInserted(storySize, newStorySize);
        }

        merchantDetailMainView.goneProgressDialog();
        merchantDetailMainInteractor.setFirstCreated(true);
    }

    @Override
    public void onClickComment(Story story, int position) {
        User storyUser = merchantDetailMainInteractor.getStoryUser();
        story.setUser(storyUser);

        merchantDetailMainView.navigateToCommentActivity(story, position);
    }

    @Override
    public void setFileAdapterItem(MerchantDetailMainStoryAdapter.MerchantDetailStoryViewHolder holder, List<File> files) {
        int fileSize = files.size();

        if (fileSize > 1) {
            merchantDetailMainView.showFile(holder);
            merchantDetailMainView.setMerchantDetailMainFileAdapterItem(holder, files);

        } else if (fileSize == 1) {
            merchantDetailMainView.showFile(holder);
            merchantDetailMainView.setMerchantDetailMainFileAdapterItem(holder, files);
            merchantDetailMainView.gonePosition(holder);
            merchantDetailMainView.goneIndicator(holder);

        } else {
            merchantDetailMainView.goneFile(holder);
            merchantDetailMainView.goneIndicator(holder);
        }
    }

    @Override
    public void onLikeChecked(int position, Story story, boolean checked) {
        long storyId = story.getId();

        User user = merchantDetailMainInteractor.getUser();
        if (user != null) {
            long userId = user.getId();
            String userName = user.getName();
            User storyUser = merchantDetailMainInteractor.getStoryUser();
            long storyUserId = storyUser.getId();

            if (checked) {
                merchantDetailMainInteractor.setStoryLikeByStoryIdAndStoryUserIdAndUserId(storyId, storyUserId, userId, userName, position);
            } else {
                merchantDetailMainInteractor.deleteStoryLikeByStoryIdAndStoryUserIdAndUserId(storyId, userId, position);
            }

        } else {
            merchantDetailMainView.navigateToLoginActivity();
            merchantDetailMainView.showMessage("로그인이 필요한 서비스입니다.");
        }
    }

    @Override
    public void onSuccessSetStoryLikeByStoryIdAndStoryUserIdAndUserId(int position) {
        List<Story> stories = merchantDetailMainInteractor.getStories();
        Story story = stories.get(position);

        int commentCount = story.getCommentCount();
        int likeCount = story.getLikeCount();
        story.setLikeCount(likeCount + 1);
        story.setCommentCount(commentCount);
        story.setFirstLikeChecked(false);
        story.setLikeChecked(true);
        merchantDetailMainView.storyAdapterNotifyItemChanged(position);
    }

    @Override
    public void onSuccessDeleteStoryLikeByStoryIdAndStoryUserIdAndUserId(int position) {
        List<Story> stories = merchantDetailMainInteractor.getStories();
        Story story = stories.get(position);

        int commentCount = story.getCommentCount();
        int likeCount = story.getLikeCount();
        story.setLikeCount(likeCount - 1);
        story.setCommentCount(commentCount);
        story.setFirstLikeChecked(false);
        story.setLikeChecked(false);
        merchantDetailMainView.storyAdapterNotifyItemChanged(position);
    }


    @Override
    public void onSuccessGetFileListByIdAndTypeAndOffset(List<File> newFiles, int type) {
        int newFileSize = newFiles.size();

        if (type == DefaultFileFlag.PHOTO_TYPE) {
            List<File> photos = merchantDetailMainInteractor.getPhotos();
            int photoSize = photos.size();
            if (photoSize == 0) {
                if (newFileSize == 0) {
                    merchantDetailMainView.gonePhoto();
                } else {
                    merchantDetailMainInteractor.setPhotos(newFiles);
                    merchantDetailMainView.clearMerchantDetailMainPhotoAdapter();
                    merchantDetailMainView.setMerchantDetailMainPhotoAdapterItem(newFiles);
                }

            } else {
                merchantDetailMainInteractor.setPhotosAddAll(newFiles);
                merchantDetailMainView.photoAdapterNotifyItemRangeInserted(photoSize, newFileSize);
            }

        }

        if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
            List<File> videos = merchantDetailMainInteractor.getVideos();
            int videoSize = videos.size();
            if (videoSize == 0) {
                if (newFileSize == 0) {
                    merchantDetailMainView.goneVideo();
                } else {
                    merchantDetailMainInteractor.setVideos(newFiles);
                    merchantDetailMainView.clearMerchantDetailMainVideoAdapter();
                    merchantDetailMainView.setMerchantDetailMainVideoAdapterItem(newFiles);
                }
            } else {
                merchantDetailMainInteractor.setVideosAddAll(newFiles);
                merchantDetailMainView.videoAdapterNotifyDataSetChanged();
            }

        }

        merchantDetailMainView.goneProgressDialog();
    }

    @Override
    public void onScrollChange(int difference) {

        if (difference <= 0) {
            merchantDetailMainView.showProgressDialog();
            User storyUser = merchantDetailMainInteractor.getStoryUser();
            long storyUserId = storyUser.getId();

            List<Story> stories = merchantDetailMainInteractor.getStories();
            long offset = stories.size();


            User user = merchantDetailMainInteractor.getUser();
            boolean isFirstCreated = merchantDetailMainInteractor.isFirstCreated();
            if (isFirstCreated) {
                if (user != null) {
                    long userId = user.getId();
                    merchantDetailMainInteractor.getStoryListByStoryUserIdAndUserIdAndOffset(storyUserId, userId, offset);
                } else {
                    merchantDetailMainInteractor.getStoryListByStoryUserIdAndOffset(storyUserId, offset);
                }
            }
        }
    }

    @Override
    public void onScrolled(int scrollOffset, int scrollRange, int scrollExtent) {
        int invisibleExtent = (scrollRange - scrollExtent);
        if (invisibleExtent > 0) {
            int difference = scrollOffset / invisibleExtent;
            if (difference > 0) {
                merchantDetailMainView.showProgressDialog();

                User storyUser = merchantDetailMainInteractor.getStoryUser();
                long storyUserId = storyUser.getId();

                List<File> photos = merchantDetailMainInteractor.getPhotos();
                int offset = photos.size();

                merchantDetailMainInteractor.getFileListByIdAndTypeAndOffset(storyUserId, DefaultFileFlag.PHOTO_TYPE, offset);
            }
        }
    }

    @Override
    public void onClickVideo(File file) {
        merchantDetailMainView.showProgressDialog();
        merchantDetailMainInteractor.updateFileByHits(file);
    }

    @Override
    public void onClickPhoto(File file) {
        merchantDetailMainView.showProgressDialog();
        merchantDetailMainInteractor.updateFileByHits(file);
    }

    @Override
    public void onClickPhotoLayout(File file, int position) {
        merchantDetailMainView.showProgressDialog();
        List<File> files = merchantDetailMainInteractor.getPhotos();
        merchantDetailMainView.navigateToPhotoDialogActivity(files, position);
        merchantDetailMainView.goneProgressDialog();
    }

    @Override
    public void onActivityResultForStoryResultDelete(int position) {
        merchantDetailMainInteractor.setStoriesRemoveAtThePosition(position);
        merchantDetailMainView.storyAdapterNotifyItemRemoved(position);
    }

    @Override
    public void onActivityResultForStoryEditResultOk(Story story, int position) {
        merchantDetailMainInteractor.replaceStoriesItemAtThePosition(position, story);
        merchantDetailMainView.storyAdapterNotifyItemChanged(position);
    }


    @Override
    public void onActivityResultForCommentResultOk(int position, int commentCountAdded) {
        List<Story> stories = merchantDetailMainInteractor.getStories();
        Story story = stories.get(position);
        int commentCount = story.getCommentCount();
        story.setCommentCount(commentCount + commentCountAdded);
        story.setFirstLikeChecked(false);
        merchantDetailMainView.storyAdapterNotifyItemChanged(position);
    }

    @Override
    public void onSuccessUpdateFileByHits(File file) {
        merchantDetailMainView.goneProgressDialog();

        int type = file.getType();
        if (type == DefaultFileFlag.PHOTO_TYPE) {
            merchantDetailMainView.navigateToPhotoDialogActivity(file);
        }
        if (type == DefaultFileFlag.VIDEO_THUMBNAIL_TYPE) {
            merchantDetailMainView.navigateToVideoPlayerActivity(file);
        }
    }

    @Override
    public void onSuccessUpdateFileByHits(int position) {
        merchantDetailMainView.goneProgressDialog();

        List<File> files = merchantDetailMainInteractor.getPhotos();
        merchantDetailMainView.navigateToPhotoDialogActivity(files, position);
    }

    @Override
    public void onClickMenu(Story story, int position) {
        User storyUser = merchantDetailMainInteractor.getStoryUser();
        story.setUser(storyUser);
        merchantDetailMainView.navigateToStoryDialogActivity(story, position);
    }

    @Override
    public void onPageSelected(int position) {
        List<File> videos = merchantDetailMainInteractor.getVideos();
        int offset = videos.size();

        if (position == offset - 1) {
            merchantDetailMainView.showProgressDialog();
            User storyUser = merchantDetailMainInteractor.getStoryUser();
            long storyUserId = storyUser.getId();

            merchantDetailMainInteractor.getFileListByIdAndTypeAndOffset(storyUserId, DefaultFileFlag.VIDEO_THUMBNAIL_TYPE, offset);
        }
    }

    @Override
    public void onNetworkError(HttpErrorDto httpErrorDto) {
        if (httpErrorDto == null) {
            merchantDetailMainView.showMessage("네트워크 불안정합니다. 다시 시도하세요.");
        } else {
            merchantDetailMainView.showMessage(httpErrorDto.message());
        }
    }
}
