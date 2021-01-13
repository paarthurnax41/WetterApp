package com.example.wetterappb;

import com.example.wetterappb.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WeatherInformationIF {
    @GET("/current")
    Call<WeatherResponse> wetter(@Query("access_key") String accessKey, @Query("query") String query);
    //@POST
}
