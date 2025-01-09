package com.dsk.network.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsk.network.utils.APIResponse
import com.dsk.network.utils.AppConstants
import com.dsk.network.R
import com.dsk.network.WeatherApplication
import com.dsk.network.databinding.ActivityMainBinding
import com.dsk.network.ui.adapter.HourlyWeatherAdapter
import com.dsk.network.utils.NetworkConnection
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val repository = WeatherApplication.getWeatherRepository(this)
        val factory = GenericViewModelFactory { MainViewModel(repository) }
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setupRecyclerView()
        observeNetwork()
        observeWeatherData()

        mainViewModel.fetchWeather("New York", AppConstants.WEATHER_LIST_APP_ID,this) // Replace "API_KEY"
    }

    private fun setupRecyclerView() {
        binding.rvHourlyWeather.layoutManager = LinearLayoutManager(this)
        hourlyWeatherAdapter = HourlyWeatherAdapter(emptyList())
        binding.rvHourlyWeather.adapter = hourlyWeatherAdapter
    }

    private fun observeWeatherData() {
        mainViewModel.weatherData.observe(this) { response ->
            when (response) {
                is APIResponse.Success -> {
                    hourlyWeatherAdapter.setWeatherData(response.data?.let { listOf(it) } ?: emptyList())
                }
                is APIResponse.Error -> {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
                is APIResponse.Loading -> {
                    Log.d("MainActivity", "Loading weather data...")
                }
            }
        }
    }

    private fun observeNetwork() {
        NetworkConnection(this).observe(this) { isConnected ->
            if (isConnected) {
                snackbar?.dismiss()
                mainViewModel.fetchWeather(
                    "New York",
                    AppConstants.WEATHER_LIST_APP_ID,
                    this
                ) // Replace "API_KEY"
            } else {
                snackbar = Snackbar.make(
                    binding.root,
                    "No Internet Connection",
                    Snackbar.LENGTH_INDEFINITE
                )
                mainViewModel.fetchWeather(
                    "New York",
                    AppConstants.WEATHER_LIST_APP_ID,
                    this
                ) // Replace "API_KEY"
                snackbar?.show()
            }
        }
    }
}

