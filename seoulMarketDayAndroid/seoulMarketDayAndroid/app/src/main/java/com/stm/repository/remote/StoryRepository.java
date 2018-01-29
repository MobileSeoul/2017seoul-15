package com.stm.repository.remote;

import com.stm.common.dao.Story;
import com.stm.common.dao.StoryComment;
import com.stm.common.dto.StoryDto;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 스토리 관련 레파지토리 레이어 인터페이스
 * <p>
 * 스토리 관련 api url 함수 정보
 */

public interface StoryRepository {
    /**
     * GET 요청메소드
     */
    @GET("{id}.json")
    Call<Story> findStoryById(@Path("id") long id);

    @GET("{id}.json")
    Call<Story> findStoryById(@Path("id") long id, @Query("userId") long userId);

//    @GET("{id}.json")
//    Call<Story> findStoryByIdAndUserId(@Path("id") long id, @Query("userId") long userId);

    @GET("{id}/comments.json")
    Call<List<StoryComment>> findStoryCommentListById(@Path("id") long id, @Query("offset") long offset);

    @GET("{id}/comments.json")
    Call<List<StoryComment>> findStoryCommentListById(@Path("id") long id, @Query("userId") long userId, @Query("offset") long offset);

    @GET("best5.json")
    Call<List<Story>> findBestStoryListPerMonthLimitFive();

    /**
     * POST 요청메소드
     */

    @Multipart
    @POST("./")
    Call<Integer> saveStory(
            @Part(value = "story", encoding = "8-bit") Story story,
            @Part List<MultipartBody.Part> files);

    @Multipart
    @POST("./")
    Call<Integer> saveStory(@Part(value = "story", encoding = "8-bit") Story story);

    @POST("{id}/like")
    Call<ResponseBody> saveStoryLikeByIdAndStoryUserIdAndUserId(@Path("id") long id, @Query("storyUserId") long storyUserId, @Query("userId") long userId, @Query("userName") String userName);

    /**
     * PUT 요청메소드
     */


    @Multipart
    @POST("{id}")
    Call<Story> updateStory(@Path("id") long id, @Part(value = "storyDto", encoding = "8-bit") StoryDto storyDto,
                            @Part List<MultipartBody.Part> files);


    @Multipart
    @POST("{id}")
    Call<Story> updateStory(@Path("id") long id, @Part(value = "storyDto", encoding = "8-bit") StoryDto storyDto);

    /**
     * DELETE 요청메소드
     */

    @DELETE("{id}/like")
    Call<ResponseBody> deleteStoryLikeByIdAndStoryUserIdAndUserId(@Path("id") long id, @Query("userId") long userId);

    @HTTP(method = "DELETE", path = "{id}", hasBody = true)
    Call<ResponseBody> deleteStory(@Path("id") long id, @Body Story story);
}
