package com.stm.repository.remote;

import com.stm.common.dto.Directions;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ㅇㅇ on 2017-08-25.
 */

public interface GoogleRepository {
    /**
     * GET 요청메소드
     */
    @GET("/maps/api/directions/json")
    Call<Directions> findDirections(@Query("origin") String origin, @Query("destination") String destination, @Query("mode") String mode, @Query("key") String key);

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
