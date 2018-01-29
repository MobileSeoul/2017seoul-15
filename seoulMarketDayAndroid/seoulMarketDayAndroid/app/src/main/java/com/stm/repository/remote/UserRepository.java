package com.stm.repository.remote;

import com.stm.common.dao.File;
import com.stm.common.dao.FirebaseNotification;
import com.stm.common.dao.Market;
import com.stm.common.dao.MerchantFollower;
import com.stm.common.dao.Story;
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
 * 유저 관련 레파지토리 레이어 인터페이스
 * <p>
 * 유저 관련 api url 함수 정보
 */

public interface UserRepository {
    /**
     * GET 요청메소드
     */

    @GET("emailCheck.json")
    Call<Boolean> findCheckByEmail(@Query("email") String email);

    @GET("userByEmail.json")
    Call<User> findUserByEmail(@Query("email") String email);

    @GET("login.json")
    Call<User> findUserByEmailAndPassword(@Query("email") String email, @Query("password") String password);

    @GET("{id}.json")
    Call<User> findUserById(@Path("id") long id);

    @GET("{id}.json")
    Call<User> findUserByIdAndUserId(@Path("id") long id, @Query("userId") long userId);

    @GET("{id}/stories.json")
    Call<List<Story>> findStoryListByStoryUserIdAndOffset(@Path("id") long id, @Query("offset") long offset);

    @GET("{id}/stories.json")
    Call<List<Story>> findStoryListByStoryUserIdAndUserIdAndOffset(@Path("id") long id, @Query("userId") long userId, @Query("offset") long offset);

    @GET("{id}/files/{type}.json")
    Call<List<File>> findFileListByIdAndTypeAndOffset(@Path("id") long id, @Path("type") long type, @Query("offset") long offset);

    @GET("{id}/followers.json")
    Call<List<User>> findFollowerListByStoryUserIdAndOffset(@Path("id") long id, @Query("offset") long offset);

    @GET("best5.json")
    Call<List<User>> findBestMerchantListPerMonthLimitFive();

    @GET("{id}/notifications.json")
    Call<List<FirebaseNotification>> findFirebaseNotificationListById(@Path("id") long id, @Query("offset") long offset);

    @GET("{id}/followingMarkets.json")
    Call<List<Market>> findFollowingMarketListByIdAndOffset(@Path("id") long id, @Query("offset") long offset);

    @GET("{id}/followingMerchants.json")
    Call<List<User>> findFollowingMerchantListByIdAndOffset(@Path("id") long id, @Query("offset") long offset);

    @GET("search.json")
    Call<List<User>> findUserListByKeywordAndOffset(@Query("keyword") String keyword, @Query("userId") long userId, @Query("offset") long offset);

    @GET("search.json")
    Call<List<User>> findUserListByKeywordAndOffset(@Query("keyword") String keyword, @Query("offset") long offset);

    /**
     * POST 요청메소드
     */

    @POST("./")
    Call<User> saveUser(@Body User user);

    @POST("{id}/follow")
    Call<ResponseBody> saveMerchantFollower(@Path("id") long id, @Body MerchantFollower merchantFollower);

    @POST("resetPassword")
    Call<ResponseBody> updateUserByResettingPassword(@Body User user);


    /**
     * PUT 요청메소드
     */

    @Multipart
    @PUT("{id}")
    Call<User> updateUser(@Path("id") long id, @Part(value = "user", encoding = "8-bit") User user);

    @Multipart
    @POST("{id}")
    Call<User> updateUser(@Path("id") long id, @Part(value = "user", encoding = "8-bit") User user, @Part List<MultipartBody.Part> files);

    /**
     * DELETE 요청메소드
     */
    @HTTP(method = "DELETE", path = "{id}/follow", hasBody = true)
    Call<ResponseBody> deleteMerchantFollower(@Path("id") long id, @Body MerchantFollower merchantFollower);

}
