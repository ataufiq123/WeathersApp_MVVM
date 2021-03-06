package com.example.ataufiq.weathersapp_mvvm.data.source

import android.util.Log
import com.example.ataufiq.weathersapp_mvvm.data.Weather
import com.example.ataufiq.weathersapp_mvvm.data.source.local.WeathersLocalDataSource

class WeathersRepository(
        val remoteDataSource: WeathersDataSource,
        val localDataSource: WeathersLocalDataSource
) : WeathersDataSource {
    override fun getWeather(callback: WeathersDataSource.GetWeatherCallback) {
        remoteDataSource.getWeather(object : WeathersDataSource.GetWeatherCallback {
            override fun onWeatherLoaded(weathers: MutableList<Weather>?) {
                callback.onWeatherLoaded(weathers)
            }

            override fun onNotAvailable() {
                callback.onNotAvailable()
            }

            override fun onError(msg: String?) {
                callback.onError(msg)
            }

        })
    }

    companion object {
        private var INSTANCE: WeathersRepository? = null

        @JvmStatic
        fun getInstance(weathersRemoteDataSource: WeathersDataSource, weathersLocalDataSource: WeathersLocalDataSource) =
                INSTANCE ?: synchronized(WeathersRepository::class.java){
                    INSTANCE ?: WeathersRepository(weathersRemoteDataSource,weathersLocalDataSource)
                            .also { INSTANCE = it }
                }

        @JvmStatic
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}