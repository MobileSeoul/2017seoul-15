package com.stm.main.fragment.main.search.fragment.tag.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import com.stm.main.fragment.main.search.base.activity.SearchActivity;
import com.stm.main.fragment.main.search.fragment.tag.adapter.SearchTagStoryAdapter;
import com.stm.main.fragment.main.search.fragment.tag.presenter.SearchTagPresenter;
import com.stm.main.fragment.main.search.fragment.tag.presenter.impl.SearchTagPresenterImpl;
import com.stm.main.fragment.main.search.fragment.tag.view.SearchTagView;
import com.stm.media.video.player.activity.VideoPlayerActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.story.searchtag.activity.SearchTagActivity;
import com.stm.user.detail.merchant.base.activity.MerchantDetailActivity;
import com.stm.user.detail.normal.activity.UserDetailActivity;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-09-01.
 */

public class SearchTagFragment extends Fragment implements SearchTagView, NestedScrollView.OnScrollChangeListener {
    private SearchTagPresenter searchTagPresenter;
    private Context context;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private SearchTagStoryAdapter searchTagStoryAdapter;

    @BindView(R.id.nsv_searchtag)
    NestedScrollView nsv_searchtag;

    @BindView(R.id.ll_searchtag_empty)
    LinearLayout ll_searchtag_empty;

    @BindView(R.id.rv_searchtag)
    RecyclerView rv_searchtag;

    @BindString(R.string.cloud_front_story_video)
    String storyVideoUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        this.toastUtil = new ToastUtil(context);
        this.sharedPrefersManager = new SharedPrefersManager(context);
        this.progressDialog = new ProgressDialog(context);
        this.progressDialogHandler = new Handler();

        User user = sharedPrefersManager.getUser();
        this.searchTagPresenter = new SearchTagPresenterImpl(this);
        this.searchTagPresenter.init(user);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchtag, container, false);
        ButterKnife.bind(this, view);
        searchTagPresenter.onCreateView();
        return view;
    }

    @Override
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
    }

    @Override
    public void showEmptyView() {
        ll_searchtag_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_dialog);
    }

    @Override
    public void goneEmptyView() {
        ll_searchtag_empty.setVisibility(View.GONE);
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
    public void gonePosition(SearchTagStoryAdapter.SearchTagStoryViewHolder holder) {
        searchTagStoryAdapter.gonePosition(holder);
    }

    @Override
    public void goneIndicator(SearchTagStoryAdapter.SearchTagStoryViewHolder holder) {
        searchTagStoryAdapter.goneIndicator(holder);
    }

    @Override
    public void goneFile(SearchTagStoryAdapter.SearchTagStoryViewHolder holder) {
        searchTagStoryAdapter.goneFile(holder);
    }

    @Override
    public void setOnScrollChangeListener() {
        nsv_searchtag.setOnScrollChangeListener(this);
    }

    @Override
    public void setSearchTagAdapterItem(List<Story> stories) {
        searchTagStoryAdapter = new SearchTagStoryAdapter(searchTagPresenter, stories, context, R.layout.item_searchtag_story);
        rv_searchtag.setAdapter(searchTagStoryAdapter);
        rv_searchtag.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        searchTagStoryAdapter.notifyDataSetChanged();

    }

    @Override
    public void setSearchTagFileAdapterItem(SearchTagStoryAdapter.SearchTagStoryViewHolder holder, List<File> files) {
        searchTagStoryAdapter.setSearchTagFileAdapterItem(holder, files);
    }

    @Override
    public void setSearchTagAdapterLikeUnChecked(SearchTagStoryAdapter.SearchTagStoryViewHolder holder) {
        searchTagStoryAdapter.setSearchTagAdapterLikeUnChecked(holder);
    }

    @Override
    public void getStoryListByKeyword(String keyword) {
        searchTagPresenter.getStoryListByKeyword(keyword);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = v.getChildAt(v.getChildCount() - 1);
        int difference = (view.getBottom() - (v.getHeight() + v.getScrollY()));
        searchTagPresenter.onScrollChange(difference);
    }

    @Override
    public void clearSearchTagAdapter() {
        if (searchTagStoryAdapter != null) {
            searchTagStoryAdapter = null;
        }
    }

    @Override
    public void notifyItemRangeInserted(int startPosition, int itemCount) {
        if (searchTagStoryAdapter != null) {
            searchTagStoryAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void navigateToCommentActivity(Story story, int position) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra("story", story);
        intent.putExtra("position", position);
        startActivityForResult(intent, ActivityResultFlag.STORY_COMMENT_REQEUST);
    }

    @Override
    public void navigateToMerchantDetailActivity(User user) {
        long userId = user.getId();
        Intent intent = new Intent(context, MerchantDetailActivity.class);
        intent.putExtra("storyUserId", userId);
        startActivity(intent);
    }

    @Override
    public void navigateToUserDetailActivity(User user) {
        long userId = user.getId();
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra("storyUserId", userId);
        startActivity(intent);
    }

    @Override
    public void navigateToVideoPlayerActivity(File file) {
        String uri = storyVideoUrl + file.getName() + "." + DefaultFileFlag.VIDEO_EXT;
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra("uri", uri);
        startActivity(intent);
    }

    @Override
    public void navigateToPhotoDialogActivity(File file) {
        Intent intent = new Intent(context, PhotoDialogActivity.class);
        intent.putExtra("file", file);
        startActivity(intent);
    }

    @Override
    public void navigateToStoryDialogActivity(Story story, int position) {
        Intent intent = new Intent(context, StoryDialogActivity.class);
        intent.putExtra("story", story);
        intent.putExtra("position", position);
        startActivityForResult(intent, ActivityResultFlag.STORY_DIALOG_REQUEST);
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void searchTagStoryAdapterNotifyItemRemoved(int position) {
        if(searchTagStoryAdapter != null){
            searchTagStoryAdapter.notifyItemRemoved(position);
        }
    }

    @Override
    public void searchTagStoryAdapterNotifyItemChanged(int position) {
        if(searchTagStoryAdapter != null){
            searchTagStoryAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void searchTagStoryAdapterNotifyDataSetChanged() {
        if(searchTagStoryAdapter != null){
            searchTagStoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                            break;

                        case ActivityResultFlag.RESULT_CANCEL:
                            break;
                    }
                    break;
            }

    }
}
