package com.stm.repository.remote;

import com.stm.common.dao.FirebaseNotification;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Dev-0 on 2017-08-30.
 */

public interface FirebaseNotificationRepository {
    /**
     * GET 요청메소드
     */


    /**
     * POST 요청메소드
     */

    /**
     * PUT 요청메소드
     */
    @PUT("{id}")
    Call<ResponseBody> updateFirebaseNotification(@Path("id") long id, @Body FirebaseNotification firebaseNotification);


    /**
     * DELETE 요청메소드
     */
}
