package com.stm.story.searchtag.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.stm.story.searchtag.adapter.SearchTagStoryAdapter;
import com.stm.story.searchtag.presenter.SearchTagPresenter;
import com.stm.story.searchtag.presenter.impl.SearchTagPresenterImpl;
import com.stm.story.searchtag.view.SearchTagView;
import com.stm.user.detail.merchant.base.activity.MerchantDetailActivity;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public class SearchTagActivity extends Activity implements SearchTagView, NestedScrollView.OnScrollChangeListener {
    private SearchTagPresenter searchTagPresenter;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private SearchTagStoryAdapter searchTagStoryAdapter;

    private IncludedToolbarLayout includedToolbarLayout;

    @BindView(R.id.rv_searchhashtag)
    RecyclerView rv_searchhashtag;

    @BindView(R.id.nsv_searchhashtag)
    NestedScrollView nsv_searchhashtag;

    @BindView(R.id.ll_searchhashtag_empty)
    LinearLayout ll_searchhashtag_empty;

    @BindString(R.string.cloud_front_story_video)
    String storyVideoUrl;

    @BindView(R.id.in_searchtag_toolbar)
    View in_searchtag_toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchtag);
        ButterKnife.bind(this);

        this.toastUtil = new ToastUtil(this);
        this.sharedPrefersManager = new SharedPrefersManager(this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        String tagName = getIntent().getStringExtra("tagName");

        this.searchTagPresenter = new SearchTagPresenterImpl(this);
        this.searchTagPresenter.init(user, tagName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = sharedPrefersManager.getUser();
        searchTagPresenter.onResume(user);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
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
    public void setSearchTagStoryAdapterItem(List<Story> stories) {
        searchTagStoryAdapter = new SearchTagStoryAdapter(searchTagPresenter, stories, this, R.layout.item_searchtag_story);
        rv_searchhashtag.setAdapter(searchTagStoryAdapter);
        rv_searchhashtag.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setSearchTagFileAdapterItem(SearchTagStoryAdapter.SearchTagStoryViewHolder holder, List<File> files) {
        searchTagStoryAdapter.setSearchTagFileAdapterItem(holder, files);
    }

    @Override
    public void gonePosition(SearchTagStoryAdapter.SearchTagStoryViewHolder holder) {
        searchTagStoryAdapter.gonePosition(holder);
    }

    @Override
    public void goneIndicator(SearchTagStoryAdapter.SearchTagStoryViewHolder holder) {
        searchTagStoryAdapter.goneIndicator(holder);
    }

    @Override
    public void goneMedia(SearchTagStoryAdapter.SearchTagStoryViewHolder holder) {
        searchTagStoryAdapter.goneMedia(holder);
    }

    @Override
    public void showEmptyView() {
        ll_searchhashtag_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void setScrollViewOnScrollChangeListener() {
        nsv_searchhashtag.setOnScrollChangeListener(this);
    }

    @Override
    public void clearSearchTagStoryAdapter() {
        if (searchTagStoryAdapter != null) {
            searchTagStoryAdapter = null;
        }
    }

    @Override
    public void searchTagStoryAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (searchTagStoryAdapter != null) {
            searchTagStoryAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void searchTagStoryAdapterNotifyItemRemoved(int position) {
        if (searchTagStoryAdapter != null) {
            int itemCount = searchTagStoryAdapter.getItemCount();
            searchTagStoryAdapter.notifyItemRemoved(position);
            searchTagStoryAdapter.notifyItemRangeChanged(position, itemCount);
        }
    }

    @Override
    public void setSearchTagAdapterLikeUnChecked(SearchTagStoryAdapter.SearchTagStoryViewHolder holder) {
        searchTagStoryAdapter.setSearchTagAdapterLikeUnChecked(holder);
    }

    @Override
    public void navigateToVideoPlayerActivity(File file) {
        String uri = storyVideoUrl + file.getName() + "." + DefaultFileFlag.VIDEO_EXT;
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        intent.putExtra("uri", uri);
        startActivity(intent);
    }

    @Override
    public void navigateToPhotoDialogActivity(File file) {
        Intent intent = new Intent(this, PhotoDialogActivity.class);
        intent.putExtra("file", file);
        startActivity(intent);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = v.getChildAt(v.getChildCount() - 1);
        int difference = (view.getBottom() - (v.getHeight() + v.getScrollY()));
        searchTagPresenter.onScrollChange(difference);
    }

    @Override
    public void setToolbarLayout() {
        includedToolbarLayout = new IncludedToolbarLayout();
        ButterKnife.bind(includedToolbarLayout, in_searchtag_toolbar);
    }

    @Override
    public void showToolbarTitle(String message) {
        includedToolbarLayout.tv_toolbar_title.setText(message);
    }

    @Override
    @OnClick(R.id.ib_toolbar_back)
    public void onClickBack() {
        searchTagPresenter.onClickBack();
    }

    @Override
    public void navigateToBack() {
        finish();
    }

    @Override
    public void navigateToCommentActivity(Story story, int position) {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("story", story);
        intent.putExtra("position", position);
        startActivityForResult(intent, ActivityResultFlag.STORY_COMMENT_REQEUST);
    }

    @Override
    public void navigateToStoryDialogActivity(Story story, int position) {
        Intent intent = new Intent(this, StoryDialogActivity.class);
        intent.putExtra("story", story);
        intent.putExtra("position", position);
        startActivityForResult(intent, ActivityResultFlag.STORY_DIALOG_REQUEST);
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToMerchantDetailActivity(User user) {
        long storyUserId = user.getId();
        Intent intent = new Intent(this, MerchantDetailActivity.class);
        intent.putExtra("storyUserId", storyUserId);
        startActivity(intent);
    }

    @Override
    public void searchTagStoryAdapterNotifyItemChanged(int position) {
        if (searchTagStoryAdapter != null) {
            searchTagStoryAdapter.notifyItemChanged(position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActivityResultFlag.STORY_COMMENT_REQEUST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        int position = data.getIntExtra("position", 0);
                        int commentCountAdded = data.getIntExtra("commentCountAdded", 0);

                        searchTagPresenter.onActivityResultForCommentResultOk(position, commentCountAdded);
                        break;

                    case ActivityResultFlag.RESULT_CANCEL:
                        break;
                }
                break;

            case ActivityResultFlag.STORY_DIALOG_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_EDIT:
                        int position = data.getIntExtra("position", 0);
                        Story story = (Story) data.getSerializableExtra("story");
                        searchTagPresenter.onActivityResultForStoryResultEdit(position, story);
                        break;

                    case ActivityResultFlag.RESULT_DELETE:
                        position = data.getIntExtra("position", 0);
                        searchTagPresenter.onActivityResultForStoryResultDelete(position);

                        setResult(ActivityResultFlag.RESULT_OK);
                        break;

                    case ActivityResultFlag.RESULT_CANCEL:
                        setResult(ActivityResultFlag.RESULT_CANCEL);
                        break;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        searchTagPresenter.onBackPressed();
    }

    public class IncludedToolbarLayout {
        @BindView(R.id.ib_toolbar_back)
        ImageButton ib_toolbar_back;

        @BindView(R.id.tv_toolbar_title)
        TextView tv_toolbar_title;
    }

}
