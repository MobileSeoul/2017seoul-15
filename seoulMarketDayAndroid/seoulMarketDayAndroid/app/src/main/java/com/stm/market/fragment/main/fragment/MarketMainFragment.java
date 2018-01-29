package com.stm.market.fragment.main.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stm.R;
import com.stm.comment.base.activity.CommentActivity;
import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.MarketFragmentFlag;
import com.stm.common.flag.MerchantFragmentFlag;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.photo.activity.PhotoDialogActivity;
import com.stm.dialog.story.activity.StoryDialogActivity;
import com.stm.login.base.activity.LoginActivity;
import com.stm.market.base.activity.MarketActivity;
import com.stm.market.fragment.main.adapter.MarketMainPhotoAdapter;
import com.stm.market.fragment.main.adapter.MarketMainStoryAdapter;
import com.stm.market.fragment.main.adapter.MarketMainVideoAdpater;
import com.stm.market.fragment.main.presenter.MarketMainPresenter;
import com.stm.market.fragment.main.presenter.impl.MarketMainPresenterImpl;
import com.stm.market.fragment.main.view.MarketMainView;
import com.stm.media.video.player.activity.VideoPlayerActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.story.searchtag.activity.SearchTagActivity;
import com.stm.user.detail.merchant.base.activity.MerchantDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-06-23.
 */

public class MarketMainFragment extends Fragment implements MarketMainView, NestedScrollView.OnScrollChangeListener, ViewPager.OnPageChangeListener {
    private MarketMainPresenter marketMainPresenter;
    private Context context;
    private ToastUtil toastUtil;
    private SharedPrefersManager sharedPrefersManager;
    private ProgressDialog progressDialog;
    private Handler progressDialogHandler;
    private Handler videoAdapterHandler;
    private MarketMainPhotoAdapter marketMainPhotoAdapter;
    private MarketMainVideoAdpater marketMainVideoAdpater;
    private MarketMainStoryAdapter marketMainStoryAdapter;

    @BindView(R.id.tv_marketmain_store)
    TextView tv_marketmain_store;

    @BindView(R.id.tv_marketmain_merchant)
    TextView tv_marketmain_merchant;

    @BindView(R.id.tv_marketmain_follow)
    TextView tv_marketmain_follow;

    @BindView(R.id.tv_marketmain_phone)
    TextView tv_marketmain_phone;

    @BindView(R.id.tv_marketmain_homepage)
    TextView tv_marketmain_homepage;

    @BindView(R.id.tv_marketmain_subname)
    TextView tv_marketmain_subname;

    @BindView(R.id.tv_marketmain_items)
    TextView tv_marketmain_items;

    @BindView(R.id.tv_marketmain_moreinfo)
    TextView tv_marketmain_moreinfo;

    @BindView(R.id.ll_marketmain_phone)
    LinearLayout ll_marketmain_phone;

    @BindView(R.id.ll_marketmain_homepage)
    LinearLayout ll_marketmain_homepage;

    @BindView(R.id.ll_marketmain_subname)
    LinearLayout ll_marketmain_subname;

    @BindView(R.id.ll_marketmain_items)
    LinearLayout ll_marketmain_items;

    @BindView(R.id.ll_marketmain_photo)
    LinearLayout ll_marketmain_photo;

    @BindView(R.id.ll_marketmain_video)
    LinearLayout ll_marketmain_video;

    @BindView(R.id.rv_marketmain_photo)
    RecyclerView rv_marketmain_photo;

    @BindView(R.id.vp_marketmain_video)
    ViewPager vp_marketmain_video;

    @BindView(R.id.nsv_marketmain)
    NestedScrollView nsv_marketmain;

    @BindView(R.id.rv_marketmain_story)
    RecyclerView rv_marketmain_story;

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
        this.videoAdapterHandler = new Handler();

        User user = (User) getArguments().getSerializable("user");
        Market market = (Market) getArguments().getSerializable("market");

        this.marketMainPresenter = new MarketMainPresenterImpl(this);
        this.marketMainPresenter.init(user, market);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_marketmain, container, false);
        ButterKnife.bind(this, view);
        this.marketMainPresenter.onCreateView();
        return view;
    }

    @Override
    public void showStoreText(String message) {
        tv_marketmain_store.setText(message);
    }

    @Override
    public void showMerchantText(String message) {
        tv_marketmain_merchant.setText(message);
    }

    @Override
    public void showFollowText(String message) {
        tv_marketmain_follow.setText(message);
    }

    @Override
    public void showPhoneText(String message) {
        tv_marketmain_phone.setText(message);
    }

    @Override
    public void showHomepageText(String message) {
        tv_marketmain_homepage.setText(message);
    }

    @Override
    public void showSubnameText(String message) {
        tv_marketmain_subname.setText(message);
    }

    @Override
    public void showItemsText(String message) {
        tv_marketmain_items.setText(message);
    }

    @Override
    public void showPhone() {
        ll_marketmain_phone.setVisibility(View.VISIBLE);
    }

    @Override
    public void showHomepage() {
        ll_marketmain_homepage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSubname() {
        ll_marketmain_subname.setVisibility(View.VISIBLE);
    }

    @Override
    public void showItems() {
        ll_marketmain_items.setVisibility(View.VISIBLE);
    }

    @Override
    public void gonePhone() {
        ll_marketmain_phone.setVisibility(View.GONE);
    }

    @Override
    public void goneHomepage() {
        ll_marketmain_homepage.setVisibility(View.GONE);
    }

    @Override
    public void goneSubname() {
        ll_marketmain_subname.setVisibility(View.GONE);
    }

    @Override
    public void goneItems() {
        ll_marketmain_items.setVisibility(View.GONE);
    }

    @Override
    public void gonePhoto() {
        ll_marketmain_photo.setVisibility(View.GONE);
    }

    @Override
    public void goneVideo() {
        ll_marketmain_video.setVisibility(View.GONE);
    }


    @Override
    public void gonePosition(MarketMainStoryAdapter.MarketMainStoryViewHolder holder) {
        marketMainStoryAdapter.gonePosition(holder);
    }

    @Override
    public void showIndicator(MarketMainStoryAdapter.MarketMainStoryViewHolder holder) {
        marketMainStoryAdapter.showIndicator(holder);
    }

    @Override
    public void goneIndicator(MarketMainStoryAdapter.MarketMainStoryViewHolder holder) {
        marketMainStoryAdapter.goneIndicator(holder);
    }

    @Override
    public void showFile(MarketMainStoryAdapter.MarketMainStoryViewHolder holder) {
        marketMainStoryAdapter.showFile(holder);
    }

    @Override
    public void goneFile(MarketMainStoryAdapter.MarketMainStoryViewHolder holder) {
        marketMainStoryAdapter.goneFile(holder);
    }



    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
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
    public void navigateToStoryDialogActivity(Story story, int position) {
        Intent intent = new Intent(context, StoryDialogActivity.class);
        intent.putExtra("story", story);
        intent.putExtra("position", position);
        startActivityForResult(intent, ActivityResultFlag.STORY_DIALOG_REQUEST);
    }

    @Override
    public void navigateToWeb(String message) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message));
        startActivity(intent);
    }

    @Override
    public void navigateToDial(String message) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + message));
        startActivity(intent);
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
    public void navigateToPhotoDialogActivity(List<File> files, int position) {
        Intent intent = new Intent(context, PhotoDialogActivity.class);
        intent.putParcelableArrayListExtra("files", (ArrayList) files);
        intent.putExtra("position", position);
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
    public void showMessage(String message) {
        this.toastUtil.showMessage(message);
    }

    @Override
    public void marketMainPhotoAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (marketMainPhotoAdapter != null) {
            marketMainPhotoAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void marketMainStoryAdapterNotifyItemChanged(int position) {
        if (marketMainStoryAdapter != null) {
            marketMainStoryAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void marketMainStoryAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (marketMainStoryAdapter != null) {
            marketMainStoryAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void videoAdapterNotifyDataSetChanged() {
        videoAdapterHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (marketMainVideoAdpater != null) {
                    marketMainVideoAdpater.notifyDataSetChanged();
                }
            }
        }, 10);
    }

    @Override
    public void marketMainStoryAdapterNotifyItemRemoved(int position) {
        if (marketMainStoryAdapter != null) {
            marketMainStoryAdapter.notifyItemRemoved(position);
         //   marketMainStoryAdapter.notifyItemRangeChanged(position, marketMainStoryAdapter.getItemCount());
        }
    }

    @Override
    public void setMarketMainPhotoAdapterItem(List<File> files) {
        marketMainPhotoAdapter = new MarketMainPhotoAdapter(marketMainPresenter, files, context, R.layout.item_marketmain_photo);
        rv_marketmain_photo.setAdapter(marketMainPhotoAdapter);
        rv_marketmain_photo.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void clearMarketMainPhotoAdapter() {
        if (marketMainPhotoAdapter != null) {
            marketMainPhotoAdapter = null;
        }
    }

    @Override
    public void setScrollViewOnScrollChangeListener() {
        nsv_marketmain.setOnScrollChangeListener(this);
    }

    @Override
    public void setPhotoRecyclerViewOnScrollListener() {
        rv_marketmain_photo.addOnScrollListener(onScrollListener);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int scrollOffset = recyclerView.computeHorizontalScrollOffset();
            int scrollRange = recyclerView.computeHorizontalScrollRange();
            int scrollExtent = recyclerView.computeHorizontalScrollExtent();

            marketMainPresenter.onScrolled(scrollOffset, scrollRange, scrollExtent);
        }
    };

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
        int difference = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
        marketMainPresenter.onScrollChange(difference);
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
    public void clearMarketMainVideoAdapter() {
        if (marketMainVideoAdpater != null) {
            marketMainVideoAdpater = null;
        }
    }

    @Override
    public void setMarketMainVideoAdapterItem(List<File> files) {
        marketMainVideoAdpater = new MarketMainVideoAdpater(marketMainPresenter, files, context, R.layout.item_marketmain_video);
        vp_marketmain_video.setAdapter(marketMainVideoAdpater);
        vp_marketmain_video.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        marketMainPresenter.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void clearMarketMainStoryAdapter() {
        if (marketMainStoryAdapter != null) {
            marketMainStoryAdapter = null;
        }
    }

    @Override
    public void setMarketMainStoryAdapterItem(List<Story> stories) {
        marketMainStoryAdapter = new MarketMainStoryAdapter(marketMainPresenter, stories, context, R.layout.item_marketmain_story);
        rv_marketmain_story.setAdapter(marketMainStoryAdapter);
        rv_marketmain_story.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }


    @Override
    public void setMarketMainFileAdapterItem(MarketMainStoryAdapter.MarketMainStoryViewHolder holder, List<File> files) {
        marketMainStoryAdapter.setMarketMainFileAdapterItem(holder, files);
    }

    @Override
    public void setMarketMainStoryAdapterLikeUnchecked(MarketMainStoryAdapter.MarketMainStoryViewHolder holder) {
        marketMainStoryAdapter.setMarketMainStoryAdapterLikeUnchecked(holder);
    }

    @Override
    public void showPosition(MarketMainStoryAdapter.MarketMainStoryViewHolder holder) {
        marketMainStoryAdapter.showPosition(holder);
    }

    @Override
    @OnClick(R.id.tv_marketmain_phone)
    public void onClickPhoneText() {
        String phone = tv_marketmain_phone.getText().toString();
        marketMainPresenter.onClickPhoneText(phone);
    }

    @Override
    @OnClick(R.id.tv_marketmain_homepage)
    public void onClickHomepageText() {
        String homepage = tv_marketmain_homepage.getText().toString();
        marketMainPresenter.onClickHomepageText(homepage);
    }

    @Override
    @OnClick(R.id.tv_marketmain_moreinfo)
    public void onClickMoreInfo() {
        ((MarketActivity) context).getViewPager().setCurrentItem(MarketFragmentFlag.INFO);
    }

    @Override
    @OnClick(R.id.tv_marketmain_morephoto)
    public void onClickMorePhoto() {
        ((MarketActivity) context).getViewPager().setCurrentItem(MerchantFragmentFlag.PHOTO);
    }

    @Override
    @OnClick(R.id.tv_marketmain_morevideo)
    public void onClickMoreVideo() {
        ((MarketActivity) context).getViewPager().setCurrentItem(MerchantFragmentFlag.VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActivityResultFlag.STORY_COMMENT_REQEUST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        int position = data.getIntExtra("position", 0);
                        int commentCountAdded = data.getIntExtra("commentCountAdded", 0);

                        marketMainPresenter.onActivityResultForCommentResultOk(position, commentCountAdded);
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
                        marketMainPresenter.onActivityResultForStoryEditResultOk(story, position);
                        break;

                    case ActivityResultFlag.RESULT_DELETE:
                        position = data.getIntExtra("position", 0);
                        marketMainPresenter.onActivityResultForStoryResultDelete(position);
                        break;

                    case ActivityResultFlag.RESULT_CANCEL:
                        break;
                }

                break;

        }
    }
}
