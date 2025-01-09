package com.dsk.network.data.network

import android.content.Context

object ApiService {

    private const val BASE_URL = "http://api.weatherstack.com/"
    private var weatherApi: WeatherApi? = null

    fun initialize(context: Context): WeatherApi {
        if (weatherApi == null) {
            weatherApi = RetrofitClient.createRetrofitService(BASE_URL, context).create(WeatherApi::class.java)
        }
        return getWeatherApi()
    }

    private fun getWeatherApi(): WeatherApi {
        return weatherApi ?: throw IllegalStateException("ApiService not initialized. Call initialize(context) first.")
    }
}
