package com.dsk.network.data.repository

import android.util.Log
import com.dsk.network.data.model.WeatherResponse
import com.dsk.network.data.network.WeatherApi
import com.dsk.network.utils.APIResponse
import java.io.IOException

class WeatherRepository(private val apiService: WeatherApi) {

    suspend fun getWeather(city: String, apiKey: String): APIResponse<WeatherResponse> {
        return try {
            val response = apiService.getWeather(city, apiKey)
            if (response.isSuccessful) {
                APIResponse.Success(response.body())
            } else {
                APIResponse.Error("Failed to fetch data: ${response.errorBody()?.string()}")
            }
        } catch (e: IOException) {
            APIResponse.Error(e.localizedMessage ?: "Network error")
        } catch (e: Exception) {
            APIResponse.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}