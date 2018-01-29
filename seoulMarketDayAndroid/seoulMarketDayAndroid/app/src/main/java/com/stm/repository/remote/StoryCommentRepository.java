package com.stm.repository.remote;

import com.stm.common.dao.StoryComment;
import com.stm.common.dao.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Dev-0 on 2017-08-08.
 */

public interface StoryCommentRepository {
    /**
     * GET 요청메소드
     */
    @GET("{groupId}/replies.json")
    Call<List<StoryComment>> findStoryCommentReplyListByGroupIdAndUserId(@Path("groupId") long groupId, @Query("userId") long userId, @Query("offset") long offset);

    @GET("{groupId}/replies.json")
    Call<List<StoryComment>> findStoryCommentReplyListByGroupId(@Path("groupId") long groupId, @Query("offset") long offset);

    /**
     * POST 요청메소드
     */
    @Multipart
    @POST("./")
    Call<StoryComment> saveStoryComment(@Part(value = "storyComment", encoding = "8-bit") StoryComment storyComment, @Part List<MultipartBody.Part> files, @Part(value = "commentUser", encoding = "8-bit") User commentUser);

    @Multipart
    @POST("./")
    Call<StoryComment> saveStoryComment(@Part(value = "storyComment", encoding = "8-bit") StoryComment storyComment, @Part(value = "commentUser", encoding = "8-bit") User commentUser);

    @Multipart
    @POST("./")
    Call<StoryComment> saveStoryComment(@Part(value = "storyComment", encoding = "8-bit") StoryComment storyComment, @Part List<MultipartBody.Part> files);

    @Multipart
    @POST("./")
    Call<StoryComment> saveStoryComment(@Part(value = "storyComment", encoding = "8-bit") StoryComment storyComment);

    /**
     * PUT 요청메소드
     */

    @PUT("{id}")
    Call<ResponseBody> updateStoryComment(@Path("id") long id, @Body StoryComment storyComment);
    /**
     * DELETE 요청메소드
     */

    @HTTP(method = "DELETE", path = "{id}", hasBody = true)
    Call<ResponseBody> deleteStoryComment(@Path("id") long id, @Body StoryComment storyComment);
}
