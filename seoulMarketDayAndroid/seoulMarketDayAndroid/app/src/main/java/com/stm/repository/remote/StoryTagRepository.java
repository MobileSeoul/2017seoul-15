package com.stm.repository.remote;

import com.stm.common.dao.Story;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ㅇㅇ on 2017-08-18.
 */

public interface StoryTagRepository {
    /**
     * GET 요청메소드
     */
    @GET("stories.json")
    Call<List<Story>> findStoryListByTagNameAndOffset(@Query("tagName") String tagName, @Query("userId") long userId, @Query("offset") long offset);

    @GET("stories.json")
    Call<List<Story>> findStoryListByTagNameAndOffset(@Query("tagName") String tagName, @Query("offset") long offset);


    /**
     * POST 요청메소드
     */

    /**
     * PUT 요청메소드
     */

    /**
     * DELETE 요청메소드
     */
}
