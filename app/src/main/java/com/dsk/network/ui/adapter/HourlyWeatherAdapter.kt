package com.dsk.network.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dsk.network.data.model.WeatherResponse
import com.dsk.network.databinding.ItemHourlyWeatherBinding

class HourlyWeatherAdapter(
private var weatherData: List<WeatherResponse>
) : RecyclerView.Adapter<HourlyWeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemHourlyWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weatherResponse = weatherData[position]
        holder.bind(weatherResponse)
    }

    override fun getItemCount(): Int = weatherData.size

    fun setWeatherData(data: List<WeatherResponse>) {
        this.weatherData = data
        notifyDataSetChanged()
    }

    class WeatherViewHolder(private val binding: ItemHourlyWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: WeatherResponse) {
            binding.tvLocation.text = "${weather.location.name}, ${weather.location.country}"
            binding.tvTemperature.text = "${weather.current.temperature}°C"
            binding.tvCondition.text = weather.current.weather_descriptions.joinToString()
            binding.tvWindSpeed.text = "${weather.current.wind_speed} km/h"
            binding.tvHumidity.text = "${weather.current.humidity}%"
            binding.tvFeelsLike.text = "Feels like: ${weather.current.feelslike}°C"

            // Load weather icon
            Glide.with(binding.ivWeatherIcon.context)
                .load(weather.current.weather_icons.first())
                .into(binding.ivWeatherIcon)
        }
    }
}
