package com.dsk.network.data.network

import android.content.Context
import android.util.Log
import com.dsk.network.utils.NetworkHelper
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object RetrofitClient {

    // Function to set up cache
    private fun provideCache(context: Context): Cache {
        Log.d("Cache", "Offline provideCache")
        val cacheDir = File(context.cacheDir, "http_cache")
        val cacheSize = 10L * 1024 * 1024 // 10 MB
        return Cache(cacheDir, cacheSize)
    }

    // Offline Interceptor
    private fun provideOfflineInterceptor(context: Context): Interceptor {
        Log.d("Cache", "Offline Cache Called")
        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkHelper.isNetworkConnected(context)) {
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200") // Cache for 4 weeks
                    .build()
            }
            chain.proceed(request)
        }
    }

    // Online Interceptor
    private fun provideOnlineInterceptor(): Interceptor {
        Log.d("Cache", "Online Cache Called")
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())
            response.newBuilder()
                .header("Cache-Control", "public, max-age=60") // Cache for 1 minute
                .build()
        }
    }

    // OkHttp Client
    private fun provideOkHttpClient(context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        Log.d("Cache", "Cache size: ${provideCache(context).size()} bytes")
        Log.d("Cache", "Max size: ${provideCache(context).maxSize()} bytes")
        return OkHttpClient.Builder()
            .cache(provideCache(context))
            .addInterceptor(provideOfflineInterceptor(context))
            .addNetworkInterceptor(provideOnlineInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
    }

    fun createRetrofitService(baseUrl: String, context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(provideOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
