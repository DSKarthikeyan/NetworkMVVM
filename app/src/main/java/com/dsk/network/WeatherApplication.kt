package com.dsk.network

import android.app.Application
import android.content.Context
import com.dsk.network.data.network.ApiService
import com.dsk.network.data.repository.WeatherRepository
import com.dsk.network.utils.NetworkHelper

class WeatherApplication : Application() {

    // Repository initialization
    private val apiService by lazy {
        ApiService.initialize(this)
    }

    val weatherRepository: WeatherRepository by lazy {
        apiService?.let { WeatherRepository(it) }!!
    }

    val networkHelper: NetworkHelper by lazy {
        NetworkHelper
    }

    companion object {
        // Provide access to the repositories and helpers
        fun getWeatherRepository(context: Context): WeatherRepository {
            return (context.applicationContext as WeatherApplication).weatherRepository
        }

        fun getNetworkHelper(context: Context): NetworkHelper {
            return (context.applicationContext as WeatherApplication).networkHelper
        }
    }
}
