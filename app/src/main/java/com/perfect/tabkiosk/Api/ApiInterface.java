package com.perfect.tabkiosk.Api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("FeedBack/feedBackEntry")
    Call<String> getCustomerfeed(@Body RequestBody body);

}
