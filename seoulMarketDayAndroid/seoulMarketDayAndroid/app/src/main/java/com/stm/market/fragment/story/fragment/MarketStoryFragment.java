package com.stm.market.fragment.story.fragment;

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
import com.stm.common.dao.Market;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.photo.activity.PhotoDialogActivity;
import com.stm.dialog.story.activity.StoryDialogActivity;
import com.stm.login.base.activity.LoginActivity;
import com.stm.market.fragment.story.adapter.MarketStoryAdapter;
import com.stm.market.fragment.story.presenter.MarketStoryPresenter;
import com.stm.market.fragment.story.presenter.impl.MarketStoryPresenterImpl;
import com.stm.market.fragment.story.view.MarketStoryView;
import com.stm.media.video.player.activity.VideoPlayerActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.story.searchtag.activity.SearchTagActivity;
import com.stm.user.detail.merchant.base.activity.MerchantDetailActivity;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ㅇㅇ on 2017-06-23.
 */

public class MarketStoryFragment extends Fragment implements MarketStoryView, NestedScrollView.OnScrollChangeListener {
    private MarketStoryPresenter marketStoryPresenter;
    private Context context;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private MarketStoryAdapter marketStoryAdapter;

    @BindView(R.id.nsv_marketstory)
    NestedScrollView nsv_marketstory;

    @BindView(R.id.ll_marketstory_empty)
    LinearLayout ll_marketstory_empty;

    @BindView(R.id.rv_marketstory)
    RecyclerView rv_marketstory;

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
        Market market = (Market) getArguments().getSerializable("market");

        this.marketStoryPresenter = new MarketStoryPresenterImpl(this);
        this.marketStoryPresenter.init(user, market);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_marketstory, container, false);
        ButterKnife.bind(this, view);

        marketStoryPresenter.onCreateView();
        return view;
    }

    @Override
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(context);
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
        }, 400);
    }

    @Override
    public void showPosition(MarketStoryAdapter.MarketStoryViewHolder holder) {
        marketStoryAdapter.showPosition(holder);
    }

    @Override
    public void showIndicator(MarketStoryAdapter.MarketStoryViewHolder holder) {
        marketStoryAdapter.showIndicator(holder);
    }

    @Override
    public void gonePosition(MarketStoryAdapter.MarketStoryViewHolder holder) {
        marketStoryAdapter.gonePosition(holder);
    }

    @Override
    public void goneIndicator(MarketStoryAdapter.MarketStoryViewHolder holder) {
        marketStoryAdapter.goneIndicator(holder);
    }

    @Override
    public void goneFile(MarketStoryAdapter.MarketStoryViewHolder holder) {
        marketStoryAdapter.goneFile(holder);
    }

    @Override
    public void showEmptyView() {
        ll_marketstory_empty.setVisibility(View.VISIBLE);
    }


    @Override
    public void setMarketStoryFileAdapterItem(MarketStoryAdapter.MarketStoryViewHolder holder, List<File> files) {
        marketStoryAdapter.setMarketStoryFileAdapterItem(holder, files);

    }

    @Override
    public void clearMarketStoryAdapter() {
        if (marketStoryAdapter != null) {
            marketStoryAdapter = null;
        }
    }

    @Override
    public void setMarketStoryAdapterItem(List<Story> stories) {
        marketStoryAdapter = new MarketStoryAdapter(marketStoryPresenter, stories, context, R.layout.item_marketstory);
        rv_marketstory.setAdapter(marketStoryAdapter);
        rv_marketstory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void marketStoryAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (marketStoryAdapter != null) {
            marketStoryAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void setScrollViewOnScrollChangeListener() {
        nsv_marketstory.setOnScrollChangeListener(this);
    }

    @Override
    public void navigateToPhotoDialogActivity(File file) {
        Intent intent = new Intent(context, PhotoDialogActivity.class);
        intent.putExtra("file", file);
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
    public void navigateToMerchantDetailActivity(User storyUser) {
        long storyUserId = storyUser.getId();
        Intent intent = new Intent(context, MerchantDetailActivity.class);
        intent.putExtra("storyUserId", storyUserId);
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
    public void navigateToCommentActivity(Story story, int position) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra("story", story);
        intent.putExtra("position", position);
        startActivityForResult(intent, ActivityResultFlag.STORY_COMMENT_REQEUST);
        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @Override
    public void navigateToSearchTagActivity(String tagName) {
        Intent intent = new Intent(context, SearchTagActivity.class);
        intent.putExtra("tagName", tagName);
        startActivity(intent);
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void setMarketStoryAdapterLikeUnchecked(MarketStoryAdapter.MarketStoryViewHolder holder) {
        marketStoryAdapter.setMarketStoryAdapterLikeUnchecked(holder);
    }

    @Override
    public void marketStoryAdapterNotifyItemChanged(int position) {
        if(marketStoryAdapter != null){
            marketStoryAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void marketStoryAdapterNotifyItemRemoved(int position) {
        if(marketStoryAdapter != null){
            marketStoryAdapter.notifyItemRemoved(position);
            marketStoryAdapter.notifyItemRangeChanged(position, marketStoryAdapter.getItemCount());
        }
    }

    @Override
    public void showFile(MarketStoryAdapter.MarketStoryViewHolder holder) {
        marketStoryAdapter.showFile(holder);
    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
        int difference = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
        marketStoryPresenter.onScrollChange(difference);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActivityResultFlag.STORY_COMMENT_REQEUST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        int position = data.getIntExtra("position", 0);
                        int commentCountAdded = data.getIntExtra("commentCountAdded", 0);

                        marketStoryPresenter.onActivityResultForCommentResultOk(position, commentCountAdded);
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
                        marketStoryPresenter.onActivityResultForStoryEditResultOk(story, position);
                        break;

                    case ActivityResultFlag.RESULT_DELETE:
                        position = data.getIntExtra("position", 0);
                        marketStoryPresenter.onActivityResultForStoryResultDelete(position);
                        break;

                    case ActivityResultFlag.RESULT_CANCEL:
                        break;
                }

                break;
        }
    }
}
