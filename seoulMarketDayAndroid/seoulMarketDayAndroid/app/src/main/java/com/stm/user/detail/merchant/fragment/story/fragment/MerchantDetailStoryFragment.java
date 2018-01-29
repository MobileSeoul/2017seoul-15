package com.stm.user.detail.merchant.fragment.story.fragment;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.stm.R;
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
import com.stm.comment.base.activity.CommentActivity;
import com.stm.story.searchtag.activity.SearchTagActivity;
import com.stm.user.detail.merchant.base.activity.MerchantDetailActivity;
import com.stm.user.detail.merchant.fragment.story.adapter.MerchantDetailStoryAdapter;
import com.stm.user.detail.merchant.fragment.story.presenter.MerchantDetailStoryPresenter;
import com.stm.user.detail.merchant.fragment.story.presenter.impl.MerchantDetailStoryPresenterImpl;
import com.stm.user.detail.merchant.fragment.story.view.MerchantDetailStoryView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-07-28.
 */

public class MerchantDetailStoryFragment extends Fragment implements MerchantDetailStoryView, NestedScrollView.OnScrollChangeListener {
    private MerchantDetailStoryPresenter merchantDetailStoryPresenter;
    private Context context;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private ToastUtil toastUtil;
    private Handler progressDialogHandler;
    private MerchantDetailStoryAdapter merchantDetailStoryAdapter;

    @BindView(R.id.nsv_merchantdetailstory)
    NestedScrollView nsv_merchantdetailstory;

    @BindView(R.id.rv_merchantdetailstory)
    RecyclerView rv_merchantdetailstory;

    @BindView(R.id.ll_merchantdetailstory_empty)
    LinearLayout ll_merchantdetailstory_empty;

    @BindString(R.string.cloud_front_story_video)
    String storyVideoUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity();
        this.sharedPrefersManager = new SharedPrefersManager(context);
        this.progressDialog = new ProgressDialog(context);
        this.toastUtil = new ToastUtil(context);
        this.progressDialogHandler = new Handler();

        User storyUser = (User) getArguments().getSerializable("storyUser");
        User user = sharedPrefersManager.getUser();
        this.merchantDetailStoryPresenter = new MerchantDetailStoryPresenterImpl(this);
        this.merchantDetailStoryPresenter.init(user, storyUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchantdetailstory, container, false);
        ButterKnife.bind(this, view);

        merchantDetailStoryPresenter.onCreateView();
        return view;
    }

    @Override
    public void setScrollViewOnScrollChangeListener() {
        nsv_merchantdetailstory.setOnScrollChangeListener(this);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = v.getChildAt(v.getChildCount() - 1);
        int difference = (view.getBottom() - (v.getHeight() + v.getScrollY()));
        merchantDetailStoryPresenter.onScrollChange(difference);
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
    public void gonePosition(MerchantDetailStoryAdapter.MerchantDetailStoryViewHolder holder) {
        merchantDetailStoryAdapter.gonePosition(holder);
    }

    @Override
    public void goneIndicator(MerchantDetailStoryAdapter.MerchantDetailStoryViewHolder holder) {
        merchantDetailStoryAdapter.goneIndicator(holder);
    }

    @Override
    public void goneFile(MerchantDetailStoryAdapter.MerchantDetailStoryViewHolder holder) {
        merchantDetailStoryAdapter.goneFile(holder);
    }

    @Override
    public void showFile(MerchantDetailStoryAdapter.MerchantDetailStoryViewHolder holder) {
        merchantDetailStoryAdapter.showFile(holder);
    }

    @Override
    public void showEmptyView() {
        ll_merchantdetailstory_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void setMerchantDetailStoryFileAdapterItem(MerchantDetailStoryAdapter.MerchantDetailStoryViewHolder holder, List<File> files) {
        merchantDetailStoryAdapter.setMerchantDetailStoryFileAdapterItem(holder, files);
    }

    @Override
    public void navigateToCommentActivity(Story story, int position) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra("story", story);
        intent.putExtra("position", position);
        this.startActivityForResult(intent, ActivityResultFlag.STORY_COMMENT_REQEUST);
        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActivityResultFlag.STORY_COMMENT_REQEUST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        int position = data.getIntExtra("position", 0);
                        int commentCountAdded = data.getIntExtra("commentCountAdded", 0);

                        merchantDetailStoryPresenter.onActivityResultForCommentResultOk(position, commentCountAdded);
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
                        merchantDetailStoryPresenter.onActivityResultForStoryEditResultOk(story, position);
                        break;

                    case ActivityResultFlag.RESULT_DELETE:
                        position = data.getIntExtra("position", 0);
                        merchantDetailStoryPresenter.onActivityResultForStoryResultDelete(position);
                        break;

                    case ActivityResultFlag.RESULT_CANCEL:

                        break;
                }
                break;

            case ActivityResultFlag.SEARCH_TAG_REQUEST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        //merchantDetailStoryAdapter.notifyDataSetChanged();
                        break;

                    case ActivityResultFlag.RESULT_CANCEL:
                        break;
                }
            break;

        }
    }

    @Override
    public void navigateToVideoPlayerActivity(File file) {
        String uri = storyVideoUrl + file.getName() + "." + DefaultFileFlag.VIDEO_EXT;
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra("uri", uri);
        startActivity(intent);
    }

    @Override
    public void clearMerchantDetailStoryAdapter() {
        if (merchantDetailStoryAdapter != null) {
            merchantDetailStoryAdapter = null;
        }
    }

    @Override
    public void setStoryByIdAndOffsetItem(List<Story> stories) {
        merchantDetailStoryAdapter = new MerchantDetailStoryAdapter(merchantDetailStoryPresenter, stories, context, R.layout.item_merchantdetailstory);
        rv_merchantdetailstory.setAdapter(merchantDetailStoryAdapter);
        rv_merchantdetailstory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void storyAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (merchantDetailStoryAdapter != null) {
            merchantDetailStoryAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }


    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void storyAdapterNotifyItemChanged(int position) {
        if (merchantDetailStoryAdapter != null) {
            merchantDetailStoryAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void storyAdapterNotifyItemRemoved(int position) {
        if (merchantDetailStoryAdapter != null) {
            merchantDetailStoryAdapter.notifyItemRemoved(position);
            merchantDetailStoryAdapter.notifyItemRangeChanged(position, merchantDetailStoryAdapter.getItemCount());
        }
    }

    @Override
    public void navigateToStoryDialogActivity(Story story, int position) {
        Intent intent = new Intent(context, StoryDialogActivity.class);
        intent.putExtra("story", story);
        intent.putExtra("position", position);
        startActivityForResult(intent, ActivityResultFlag.STORY_DIALOG_REQUEST);
    }

    @Override
    public void navigateToPhotoDialogActivity(File file) {
        Intent intent = new Intent(context, PhotoDialogActivity.class);
        intent.putExtra("file", file);
        startActivity(intent);
    }

    @Override
    public void navigateToSearchTagActivity(String tagName) {
        Intent intent = new Intent(context, SearchTagActivity.class);
        intent.putExtra("tagName", tagName);
        startActivityForResult(intent, ActivityResultFlag.SEARCH_TAG_REQUEST);
    }


    @Override
    public void setStoryRefresh(){
        merchantDetailStoryPresenter.setStoryRefresh();
    }

}
