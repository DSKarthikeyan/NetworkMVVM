package com.dsk.network.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsk.network.data.model.WeatherResponse
import com.dsk.network.data.repository.WeatherRepository
import com.dsk.network.utils.APIResponse
import com.dsk.network.utils.NetworkHelper
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherData = MutableLiveData<APIResponse<WeatherResponse>>()
    val weatherData: LiveData<APIResponse<WeatherResponse>> = _weatherData

    fun fetchWeather(city: String, apiKey: String, context: Context) {
        if (NetworkHelper.isNetworkConnected(context)) {
            viewModelScope.launch {
                _weatherData.postValue(APIResponse.Loading())
                try {
                    val response = repository.getWeather(city, apiKey)
                    _weatherData.postValue(response)
                } catch (e: Exception) {
                    Log.d("DsK","Offline cache Unknown error ${e.localizedMessage} ")
                    _weatherData.postValue(APIResponse.Error(e.message ?: "An unknown error occurred"))
                }
            }
        } else {
            _weatherData.postValue(APIResponse.Error("No network connection"))
        }
    }
}