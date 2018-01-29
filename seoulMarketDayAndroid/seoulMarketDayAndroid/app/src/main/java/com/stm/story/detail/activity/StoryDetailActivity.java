package com.stm.story.detail.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stm.R;
import com.stm.comment.base.activity.CommentActivity;
import com.stm.common.dao.File;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.photo.activity.PhotoDialogActivity;
import com.stm.dialog.story.activity.StoryDialogActivity;
import com.stm.login.base.activity.LoginActivity;
import com.stm.media.video.player.activity.VideoPlayerActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.story.detail.adapter.StoryDetailFileAdapter;
import com.stm.story.detail.presenter.StoryDetailPresenter;
import com.stm.story.detail.presenter.impl.StoryDetailPresenterImpl;
import com.stm.story.detail.view.StoryDetailView;
import com.stm.story.searchtag.activity.SearchTagActivity;
import com.stm.user.detail.merchant.base.activity.MerchantDetailActivity;
import com.viewpagerindicator.CirclePageIndicator;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnPageChange;

/**
 * Created by ㅇㅇ on 2017-08-28.
 */

public class StoryDetailActivity extends Activity implements StoryDetailView, HashTagHelper.OnHashTagClickListener {
    private StoryDetailPresenter storyDetailPresenter;
    private ToastUtil toastUtil;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private HashTagHelper hashTagHelper;
    private SharedPrefersManager sharedPrefersManager;
    private StoryDetailFileAdapter storyDetailFileAdapter;

    @BindView(R.id.iv_storydetail_avatar)
    ImageView iv_storydetail_avatar;

    @BindView(R.id.tv_storydetail_name)
    TextView tv_storydetail_name;

    @BindView(R.id.fl_storydetail_file)
    FrameLayout fl_storydetail_file;

    @BindView(R.id.vp_storydetail_file)
    ViewPager vp_storydetail_file;

    @BindView(R.id.ll_storydetail_position)
    LinearLayout ll_storydetail_position;

    @BindView(R.id.tv_storydetail_position)
    TextView tv_storydetail_position;

    @BindView(R.id.tv_storydetail_total)
    TextView tv_storydetail_total;

    @BindView(R.id.cpi_storydetail)
    CirclePageIndicator cpi_storydetail;

    @BindView(R.id.tv_storydetail_content)
    TextView tv_storydetail_content;

    @BindView(R.id.tv_storydetail_commentcount)
    TextView tv_storydetail_commentcount;

    @BindView(R.id.tv_storydetail_date)
    TextView tv_storydetail_date;

    @BindView(R.id.cb_storydetail_like)
    CheckBox cb_storydetail_like;

    @BindView(R.id.tv_storydetail_likecount)
    TextView tv_storydetail_likecount;

    @BindString(R.string.cloud_front_user_avatar)
    String userAvatarUrl;

    @BindString(R.string.cloud_front_story_video)
    String storyVideoUrl;

    @BindColor(R.color.pointColor)
    int pointColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storydetail);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();
        this.hashTagHelper = HashTagHelper.Creator.create(pointColor, this);
        this.hashTagHelper.handle(tv_storydetail_content);


        User user = sharedPrefersManager.getUser();
        long storyId = getIntent().getLongExtra("storyId", 0);
        this.storyDetailPresenter = new StoryDetailPresenterImpl(this);
        this.storyDetailPresenter.init(user, storyId);

    }

    @Override
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_dialog);

    }

    @Override
    public void goneProgressDialog() {
        progressDialogHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }, 10);
    }

    @Override
    public void showUserName(String message) {
        tv_storydetail_name.setText(message);
    }

    @Override
    public void showUserAvatar(String message) {
        Glide.with(this).load(userAvatarUrl + message).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_storydetail_avatar);
    }

    @Override
    public void showContent(String message) {
        tv_storydetail_content.setText(message);
    }

    @Override
    public void showDate(String message) {
        tv_storydetail_date.setText(message);
    }

    @Override
    public void showLikeCount(String message) {
        tv_storydetail_likecount.setText(message);
    }

    @Override
    public void showCommentCount(String message) {
        tv_storydetail_commentcount.setText(message);
    }

    @Override
    public void showFileTotal(String message) {
        tv_storydetail_total.setText(message);
    }

    @Override
    public void gonePosition() {
        ll_storydetail_position.setVisibility(View.GONE);
    }

    @Override
    public void goneFile() {
        fl_storydetail_file.setVisibility(View.GONE);
    }

    @Override
    public void goneIndicator() {
        cpi_storydetail.setVisibility(View.GONE);
    }

    @Override
    public void showIndicator() {
        cpi_storydetail.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPosition() {
        ll_storydetail_position.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFile() {
        fl_storydetail_file.setVisibility(View.VISIBLE);
    }

    @Override
    public void setLikeChecked(boolean isLikeChecked) {
        cb_storydetail_like.setChecked(isLikeChecked);
    }

    @Override
    public void setStoryDetailFileAdapterItem(List<File> files) {
        storyDetailFileAdapter = new StoryDetailFileAdapter(storyDetailPresenter, files, this, R.layout.item_storydetail_file);
        vp_storydetail_file.setAdapter(storyDetailFileAdapter);
        cpi_storydetail.setViewPager(vp_storydetail_file);
    }

    @Override
    public void clearStoryDetailFileAdapter() {
        if (storyDetailFileAdapter != null) {
            storyDetailFileAdapter = null;
        }
    }

    @Override
    @OnClick({R.id.iv_storydetail_avatar, R.id.tv_storydetail_name})
    public void onClickAvatar() {
        storyDetailPresenter.onClickAvatar();
    }

    @Override
    @OnClick({R.id.ib_storydetail_comment, R.id.tv_storydetail_morecomment})
    public void onClickComment() {
        storyDetailPresenter.onClickComment();
    }

    @Override
    @OnClick(R.id.ib_storydetail_close)
    public void onClickClose() {
        storyDetailPresenter.onClickClose();
    }

    @Override
    @OnClick(R.id.ib_storydetail_menu)
    public void onClickMenu() {
        storyDetailPresenter.onClickMenu();
    }

    @Override
    @OnPageChange(R.id.vp_storydetail_file)
    public void onPageSelected(int position) {
        String positionMessage = (position + 1) + "/";
        tv_storydetail_position.setText(positionMessage);
    }

    @OnCheckedChanged(R.id.cb_storydetail_like)
    public void onLikeChecked(boolean checked) {
        storyDetailPresenter.onLikeChecked(checked);
    }

    @Override
    public void onHashTagClicked(String hashTag) {
        storyDetailPresenter.onHashTagClicked(hashTag);
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void navigateToCommentActivity(Story story) {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("story", story);
        startActivityForResult(intent, ActivityResultFlag.STORY_COMMENT_REQEUST);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @Override
    public void navigateToStoryDialogActivity(Story story) {
        Intent intent = new Intent(this, StoryDialogActivity.class);
        intent.putExtra("story", story);
        startActivityForResult(intent, ActivityResultFlag.STORY_DIALOG_REQUEST);
    }

    @Override
    public void navigateToPhotoDialogActivity(File file) {
        Intent intent = new Intent(this, PhotoDialogActivity.class);
        intent.putExtra("file", file);
        startActivity(intent);
    }

    @Override
    public void navigateToVideoPlayerActivity(File file) {
        String uri = storyVideoUrl + file.getName() + "." + DefaultFileFlag.VIDEO_EXT;
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        intent.putExtra("uri", uri);
        startActivity(intent);
    }

    @Override
    public void navigateToMerchantDetailActivity(User storyUser) {
        long storyUserId = storyUser.getId();
        Intent intent = new Intent(this, MerchantDetailActivity.class);
        intent.putExtra("storyUserId", storyUserId);
        startActivity(intent);
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToSearchTagActivity(String tagName) {
        Intent intent = new Intent(this, SearchTagActivity.class);
        intent.putExtra("tagName", tagName);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = sharedPrefersManager.getUser();
        storyDetailPresenter.onResume(user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActivityResultFlag.STORY_COMMENT_REQEUST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        int commentCountAdded = data.getIntExtra("commentCountAdded", 0);
                        storyDetailPresenter.onActivityResultForCommentResultOk(commentCountAdded);
                        break;

                    case ActivityResultFlag.RESULT_CANCEL:
                        break;

                }
                break;

            case ActivityResultFlag.STORY_DIALOG_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_EDIT:
                        Story story = (Story) data.getSerializableExtra("story");
                        storyDetailPresenter.onActivityResultForStoryEditResultOk(story);
                        break;

                    case ActivityResultFlag.RESULT_DELETE:
                        storyDetailPresenter.onActivityResultForStoryResultDelete();
                        break;

                }
                break;
        }
    }
}
