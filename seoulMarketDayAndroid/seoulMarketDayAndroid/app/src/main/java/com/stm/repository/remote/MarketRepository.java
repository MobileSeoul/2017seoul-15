package com.stm.repository.remote;

import com.stm.common.dao.File;
import com.stm.common.dao.Market;
import com.stm.common.dao.MarketFollower;
import com.stm.common.dao.Story;
import com.stm.common.dao.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 시장 관련 레파지토리 레이어 인터페이스
 * <p>
 * 시장 관련 api url 함수 정보
 */

public interface MarketRepository {

    /**
     * GET 요청메소드
     */

    @GET("nearness.json")
    Call<List<Market>> findMarketListByLocation(@QueryMap HashMap<String, Double> map);

    @GET("sortByFollower.json")
    Call<List<Market>> findMarketListByFollower();

    @GET("search.json")
    Call<List<Market>> findMarketListByKeywordAndOffset(@Query("keyword") String keyword, @Query("offset") long offset);

    @GET("search.json")
    Call<List<Market>> findMarketListByKeywordAndOffset(@Query("keyword") String keyword, @Query("userId") long userId, @Query("offset") long offset);

    @GET("{id}.json")
    Call<Market> findMarketById(@Path("id") long id);

    @GET("{id}.json")
    Call<Market> findMarketByIdAndUserId(@Path("id") long id, @Query("userId") long userId);

    @GET("{id}/users.json")
    Call<List<User>> findUserListByIdAndOffset(@Path("id") long id, @Query("offset") long offset);

    @GET("{id}/users.json")
    Call<List<User>> findUserListByIdAndUserIdAndOffset(@Path("id") long id, @Query("userId") long userId, @Query("offset") long offset);


    @GET("{id}.json")
    Call<Market> findMarketById(@Path("id") long id, @Query("userId") long userId);

    @GET("{id}/files/{type}.json")
    Call<List<File>> findFileListByIdAndTypeAndOffset(@Path("id") long id, @Path("type") int type, @Query("offset") long offset);

    @GET("{id}/stories.json")
    Call<List<Story>> findStoryListByIdAndOffset(@Path("id") long id, @Query("userId") long userId, @Query("offset") long offset);

    @GET("{id}/stories.json")
    Call<List<Story>> findStoryListByIdAndOffset(@Path("id") long id, @Query("offset") long offset);



    /**
     * POST 요청메소드
     */

    @POST("{id}/follow")
    Call<ResponseBody> saveMarketFollower(@Path("id") long id, @Body MarketFollower marketFollower);

    /**
     * PUT 요청메소드
     */

    /**
     * DELETE 요청메소드
     */
    @HTTP(method = "DELETE", path = "{id}/follow", hasBody = true)
    Call<ResponseBody> deleteMarketFollower(@Path("id") long id, @Body MarketFollower marketFollower);
}
