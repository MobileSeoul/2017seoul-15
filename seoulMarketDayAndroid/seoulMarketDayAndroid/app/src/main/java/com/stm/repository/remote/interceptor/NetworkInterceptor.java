package com.stm.repository.remote.interceptor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stm.repository.remote.converter.NullOnEmptyConverterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * HTTP connection 인터셉터
 *
 * 엑세스 토큰 적용 Retrofit 정보
 */

public class NetworkInterceptor implements Interceptor {
    private final String BASE_API = "http://ec2-52-78-209-196.ap-northeast-2.compute.amazonaws.com/";


    private final String STORY_IMAGE_BASE_API = "http://d3fmxlpcykzndk.cloudfront.net/stm/images/stories/";
    private final String STORY_COMMENT_IMAGE_BASE_API = "http://d3fmxlpcykzndk.cloudfront.net/stm/images/comments/";
    private final String USER_AVATAR_BASE_API = "http://d3fmxlpcykzndk.cloudfront.net/stm/images/users/avatars/";
    private final String USER_COVER_BASE_API = "http://d3fmxlpcykzndk.cloudfront.net/stm/images/users/covers/";
    private final String GOOGLE_BASE_API = "https://maps.googleapis.com/";


    private final String MARKET_URL = "markets/";
    private final String MARKET_CATEGORY_URL = "marketcategories/";
    private final String REGION_CATEGORY_URL = "regioncategories/";
    private final String USER_URL = "users/";
    private final String STORY_URL = "stories/";
    private final String FILE_URL = "files/";
    private final String STORY_COMMENT_URL = "storycomments/";
    private final String REPORT_URL = "reports/";
    private final String STORY_TAG_URL = "storytags/";
    private final String NOTIFICATION_URL ="notifications/";
    private final String OPINION_URL = "opinions/";
    private final String OPINION_CATEGORY_URL ="opinioncategories/";
    private final String EXIT_URL = "exits/";
    private final String EXIT_CATEGORY_URL ="exitcategories/";


    private String accessToken = null;
    public Retrofit retrofit;
    private OkHttpClient client;
    private Gson gson = new GsonBuilder().setLenient().create();



    public NetworkInterceptor() {
        super();
        client = new OkHttpClient.Builder()
                .addInterceptor(this)
                .build();
    }

    public NetworkInterceptor(String accessToken) {
        this.accessToken = accessToken;
        client = new OkHttpClient.Builder()
                .addInterceptor(this)
                .build();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (accessToken != null) {
            request = request.newBuilder()
                    .addHeader("Authorization", accessToken)
                    .build();
        } else {
            request = request.newBuilder().build();
        }

        Response response = chain.proceed(request);

        return response;
    }

    public Retrofit getRetrofitForMarketRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API + MARKET_URL).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForMarketCategoryRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API + MARKET_CATEGORY_URL).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForRegionCategoryRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API + REGION_CATEGORY_URL).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForUserRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API + USER_URL).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForStoryRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API + STORY_URL).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForFileRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API + FILE_URL).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForStoryCommentRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API + STORY_COMMENT_URL).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForReportRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API + REPORT_URL).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForStoryTagRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API + STORY_TAG_URL).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForFirebaseNotificationRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API + NOTIFICATION_URL).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForOpinionCategoryRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API + OPINION_CATEGORY_URL).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForOpinionRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API + OPINION_URL).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForExitCategoryRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API + EXIT_CATEGORY_URL).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForExitRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API + EXIT_URL).addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForStoryFileDownloadRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(STORY_IMAGE_BASE_API)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForCommentFileDownloadRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(STORY_COMMENT_IMAGE_BASE_API)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForAvatarFileDownloadRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(USER_AVATAR_BASE_API)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForCoverFileDownloadRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(USER_COVER_BASE_API)
                .build();

        return retrofit;
    }

    public Retrofit getRetrofitForGoogleRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(GOOGLE_BASE_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

}
