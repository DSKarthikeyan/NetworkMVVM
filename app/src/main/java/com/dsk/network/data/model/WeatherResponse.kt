package com.dsk.network.data.model

data class WeatherResponse(
    val request: Request,
    val location: LocationInfo,
    val current: CurrentWeather,
    val historical: Map<String, HistoricalWeather>
)

data class Request(
    val type: String,
    val query: String,
    val language: String,
    val unit: String
)

data class LocationInfo(
    val name: String,
    val country: String,
    val region: String,
    val lat: String,
    val lon: String,
    val timezone_id: String,
    val localtime: String,
    val localtime_epoch: Long,
    val utc_offset: String
)

data class CurrentWeather(
    val observation_time: String,
    val temperature: Int,
    val weather_code: Int,
    val weather_icons: List<String>,
    val weather_descriptions: List<String>,
    val wind_speed: Int,
    val wind_degree: Int,
    val wind_dir: String,
    val pressure: Int,
    val precip: Double,
    val humidity: Int,
    val cloudcover: Int,
    val feelslike: Int,
    val uv_index: Int,
    val visibility: Int
)

data class HistoricalWeather(
    val date: String,
    val date_epoch: Long,
    val astro: Astro,
    val mintemp: Int,
    val maxtemp: Int,
    val avgtemp: Int,
    val totalsnow: Int,
    val sunhour: Double,
    val uv_index: Int,
    val hourly: List<HourlyWeather>
)

data class Astro(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val moon_phase: String,
    val moon_illumination: String
)

data class HourlyWeather(
    val time: String,
    val temperature: Int,
    val wind_speed: Int,
    val wind_degree: Int,
    val wind_dir: String,
    val weather_code: Int,
    val weather_icons: List<String>,
    val weather_descriptions: List<String>,
    val precip: Double,
    val humidity: Int,
    val visibility: Int,
    val pressure: Int,
    val cloudcover: Int,
    val feelslike: Int
)
