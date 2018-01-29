package com.stm.user.detail.merchant.fragment.main.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.stm.common.dao.Story;
import com.stm.common.dao.User;
import com.stm.common.flag.ActivityResultFlag;
import com.stm.common.flag.DefaultFileFlag;
import com.stm.common.flag.MerchantFragmentFlag;
import com.stm.common.util.ToastUtil;
import com.stm.dialog.photo.activity.PhotoDialogActivity;
import com.stm.dialog.story.activity.StoryDialogActivity;
import com.stm.login.base.activity.LoginActivity;
import com.stm.media.video.player.activity.VideoPlayerActivity;
import com.stm.repository.local.SharedPrefersManager;
import com.stm.story.searchtag.activity.SearchTagActivity;
import com.stm.user.detail.merchant.base.activity.MerchantDetailActivity;
import com.stm.user.detail.merchant.fragment.main.adapter.MerchantDetailMainPhotoAdapter;
import com.stm.user.detail.merchant.fragment.main.adapter.MerchantDetailMainStoryAdapter;
import com.stm.user.detail.merchant.fragment.main.adapter.MerchantDetailMainVideoAdapter;
import com.stm.user.detail.merchant.fragment.main.presenter.MerchantDetailMainPresenter;
import com.stm.user.detail.merchant.fragment.main.presenter.impl.MerchantDetailMainPresenterImpl;
import com.stm.user.detail.merchant.fragment.main.view.MerchantDetailMainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ㅇㅇ on 2017-07-05.
 */

public class MerchantDetailMainFragment extends Fragment implements MerchantDetailMainView, NestedScrollView.OnScrollChangeListener, ViewPager.OnPageChangeListener {
    private MerchantDetailMainPresenter merchantDetailMainPresenter;
    private SharedPrefersManager sharedPrefersManager;
    private Context context;
    private ProgressDialog progressDialog;
    private ToastUtil toastUtil;
    private Handler progressDialogHandler;
    private Handler videoAdapterHandler;
    private MerchantDetailMainStoryAdapter merchantDetailMainStoryAdapter;
    private MerchantDetailMainPhotoAdapter merchantDetailMainPhotoAdapter;
    private MerchantDetailMainVideoAdapter merchantDetailMainVideoAdapter;

    @BindView(R.id.ll_merchantdetailmain_intro)
    LinearLayout ll_merchantdetailmain_intro;

    @BindView(R.id.ll_merchantdetailmain_phone)
    LinearLayout ll_merchantdetailmain_phone;

    @BindView(R.id.ll_merchantdetailmain_follow)
    LinearLayout ll_merchantdetailmain_follow;

    @BindView(R.id.ll_merchantdetailmain_photo)
    LinearLayout ll_merchantdetailmain_photo;

    @BindView(R.id.ll_merchantdetailmain_video)
    LinearLayout ll_merchantdetailmain_video;

    @BindView(R.id.tv_merchantdetailmain_intro)
    TextView tv_merchantdetailmain_intro;

    @BindView(R.id.tv_merchantdetailmain_phone)
    TextView tv_merchantdetailmain_phone;

    @BindView(R.id.tv_merchantdetailmain_follow)
    TextView tv_merchantdetailmain_follow;

    @BindView(R.id.rv_merchantdetailmain_story)
    RecyclerView rv_merchantdetailmain_story;

    @BindView(R.id.rv_merchantdetailmain_photo)
    RecyclerView rv_merchantdetailmain_photo;

    @BindView(R.id.nsv_merchantdetailmain_story)
    NestedScrollView nsv_merchantdetailmain_story;

    @BindView(R.id.vp_merchantdetailmain_video)
    ViewPager vp_merchantdetailmain_video;

    @BindString(R.string.cloud_front_story_video)
    String storyVideoUrl;

    public MerchantDetailMainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity();
        this.sharedPrefersManager = new SharedPrefersManager(context);
        this.progressDialog = new ProgressDialog(context);
        this.toastUtil = new ToastUtil(context);
        this.progressDialogHandler = new Handler();
        this.videoAdapterHandler = new Handler();

        User storyUser = (User) getArguments().getSerializable("storyUser");
        User user = sharedPrefersManager.getUser();
        this.merchantDetailMainPresenter = new MerchantDetailMainPresenterImpl(this);
        this.merchantDetailMainPresenter.init(user, storyUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchantdetailmain, container, false);
        ButterKnife.bind(this, view);

        merchantDetailMainPresenter.onCreateView();
        return view;
    }

    @Override
    @OnClick(R.id.tv_merchantdetailmain_moreinfo)
    public void onClickMoreInfo() {
        ((MerchantDetailActivity) context).getViewPager().setCurrentItem(MerchantFragmentFlag.INFO);
    }

    @Override
    @OnClick(R.id.tv_merchantdetailmain_morephoto)
    public void onClickMorePhoto() {
        ((MerchantDetailActivity) context).getViewPager().setCurrentItem(MerchantFragmentFlag.PHOTO);
    }

    @Override
    @OnClick(R.id.tv_merchantdetailmain_morevideo)
    public void onClickMoreVideo() {
        ((MerchantDetailActivity) context).getViewPager().setCurrentItem(MerchantFragmentFlag.VIDEO);
    }

    @Override
    public void goneIntro() {
        ll_merchantdetailmain_intro.setVisibility(View.GONE);
    }

    @Override
    public void gonePhone() {
        ll_merchantdetailmain_phone.setVisibility(View.GONE);
    }

    @Override
    public void goneFollow() {
        ll_merchantdetailmain_follow.setVisibility(View.GONE);
    }

    @Override
    public void showIntroText(String message) {
        tv_merchantdetailmain_intro.setText(message);
    }

    @Override
    public void showPhoneText(String message) {
        tv_merchantdetailmain_phone.setText(message);
    }

    @Override
    public void showFollowText(String message) {
        tv_merchantdetailmain_follow.setText(message);
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showMessage(message);
    }

    @Override
    public void gonePhoto() {
        ll_merchantdetailmain_photo.setVisibility(View.GONE);
    }

    @Override
    public void goneVideo() {
        ll_merchantdetailmain_video.setVisibility(View.GONE);
    }

    @Override
    public void setScrollViewOnScrollChangeListener() {
        nsv_merchantdetailmain_story.setOnScrollChangeListener(this);
    }

    @Override
    public void setPhotoRecyclerViewOnScrollListener() {
        rv_merchantdetailmain_photo.addOnScrollListener(onScrollListener);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int scrollOffset = recyclerView.computeHorizontalScrollOffset();
            int scrollRange = recyclerView.computeHorizontalScrollRange();
            int scrollExtent = recyclerView.computeHorizontalScrollExtent();

            merchantDetailMainPresenter.onScrolled(scrollOffset, scrollRange, scrollExtent);
        }
    };

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
        int difference = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
        merchantDetailMainPresenter.onScrollChange(difference);
    }


    @Override
    public void navigateToVideoPlayerActivity(File file) {
        String uri = storyVideoUrl + file.getName() + "." + DefaultFileFlag.VIDEO_EXT;
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra("uri", uri);
        startActivity(intent);
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
    public void clearMerchantDetailMainStoryAdapter() {
        if (merchantDetailMainStoryAdapter != null) {
            merchantDetailMainStoryAdapter = null;
        }
    }

    @Override
    public void clearMerchantDetailMainPhotoAdapter() {
        if (merchantDetailMainPhotoAdapter != null) {
            merchantDetailMainPhotoAdapter = null;
        }
    }

    @Override
    public void clearMerchantDetailMainVideoAdapter() {
        if (merchantDetailMainVideoAdapter != null) {
            merchantDetailMainVideoAdapter = null;
        }
    }

    @Override
    public void setMerchantDetailMainStoryAdapterItem(List<Story> stories) {
        merchantDetailMainStoryAdapter = new MerchantDetailMainStoryAdapter(merchantDetailMainPresenter, stories, context, R.layout.item_merchatndetailmain_story);
        rv_merchantdetailmain_story.setAdapter(merchantDetailMainStoryAdapter);
        rv_merchantdetailmain_story.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setMerchantDetailMainPhotoAdapterItem(List<File> files) {
        merchantDetailMainPhotoAdapter = new MerchantDetailMainPhotoAdapter(merchantDetailMainPresenter, files, context, R.layout.item_merchantdetailmain_photo);
        rv_merchantdetailmain_photo.setAdapter(merchantDetailMainPhotoAdapter);
        rv_merchantdetailmain_photo.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void setMerchantDetailMainVideoAdapterItem(List<File> files) {
        merchantDetailMainVideoAdapter = new MerchantDetailMainVideoAdapter(merchantDetailMainPresenter, files, context, R.layout.item_merchantdetailmain_video);
        vp_merchantdetailmain_video.setAdapter(merchantDetailMainVideoAdapter);
        vp_merchantdetailmain_video.addOnPageChangeListener(this);
    }

    @Override
    public void storyAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (merchantDetailMainStoryAdapter != null) {
            merchantDetailMainStoryAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void storyAdapterNotifyItemChanged(int position) {
        if (merchantDetailMainStoryAdapter != null) {
            merchantDetailMainStoryAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void storyAdapterNotifyItemRemoved(int position) {
        if (merchantDetailMainStoryAdapter != null) {
            merchantDetailMainStoryAdapter.notifyItemRemoved(position);
          //  merchantDetailMainStoryAdapter.notifyItemRangeChanged(position, merchantDetailMainStoryAdapter.getItemCount());
        }
    }

    @Override
    public void photoAdapterNotifyItemRangeInserted(int startPosition, int itemCount) {
        if (merchantDetailMainPhotoAdapter != null) {
            merchantDetailMainPhotoAdapter.notifyItemRangeInserted(startPosition, itemCount);
        }
    }

    @Override
    public void videoAdapterNotifyDataSetChanged() {
        videoAdapterHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (merchantDetailMainVideoAdapter != null) {
                    merchantDetailMainVideoAdapter.notifyDataSetChanged();
                }
            }
        }, 10);
    }

    @Override
    public void setMerchantDetailMainFileAdapterItem(MerchantDetailMainStoryAdapter.MerchantDetailStoryViewHolder holder, List<File> files) {
        merchantDetailMainStoryAdapter.setMerchantDetailMainFileAdapterItem(holder, files);
    }

    @Override
    public void showFile(MerchantDetailMainStoryAdapter.MerchantDetailStoryViewHolder holder) {
        merchantDetailMainStoryAdapter.showFile(holder);
    }

    @Override
    public void goneFile(MerchantDetailMainStoryAdapter.MerchantDetailStoryViewHolder holder) {
        merchantDetailMainStoryAdapter.goneFile(holder);
    }

    @Override
    public void gonePosition(MerchantDetailMainStoryAdapter.MerchantDetailStoryViewHolder holder) {
        merchantDetailMainStoryAdapter.gonePosition(holder);
    }

    @Override
    public void goneIndicator(MerchantDetailMainStoryAdapter.MerchantDetailStoryViewHolder holder) {
        merchantDetailMainStoryAdapter.goneIndicator(holder);
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }


    @Override
    public void navigateToPhotoDialogActivity(File file) {
        Intent intent = new Intent(context, PhotoDialogActivity.class);
        intent.putExtra("file", file);
        startActivity(intent);
    }

    @Override
    public void navigateToPhotoDialogActivity(List<File> files, int position) {
        Intent intent = new Intent(context, PhotoDialogActivity.class);
        intent.putExtra("position", position);
        intent.putParcelableArrayListExtra("files", (ArrayList) files);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        merchantDetailMainPresenter.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ActivityResultFlag.STORY_COMMENT_REQEUST:
                switch (resultCode) {
                    case ActivityResultFlag.RESULT_OK:
                        int position = data.getIntExtra("position", 0);
                        int commentCountAdded = data.getIntExtra("commentCountAdded", 0);

                        merchantDetailMainPresenter.onActivityResultForCommentResultOk(position, commentCountAdded);
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
                        merchantDetailMainPresenter.onActivityResultForStoryEditResultOk(story, position);
                        break;

                    case ActivityResultFlag.RESULT_DELETE:
                        position = data.getIntExtra("position", 0);
                        merchantDetailMainPresenter.onActivityResultForStoryResultDelete(position);
                        break;

                    case ActivityResultFlag.RESULT_CANCEL:
                        break;
                }
                break;
        }
    }

}
