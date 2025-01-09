package com.dsk.network.data.network

import com.dsk.network.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("current")
    suspend fun getWeather(
        @Query("query") city: String, @Query("access_key") apiKey: String
    ): Response<WeatherResponse>

}