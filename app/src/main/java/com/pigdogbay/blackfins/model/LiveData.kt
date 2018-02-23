package com.pigdogbay.blackfins.model

/**
 * Created by mark on 23/02/18.
 * Temperature in °C and relative humidity is %
 */
data class LiveData(val temperature : Float, val temperatureSetpoint: Float, val relativeHumidity : Int, val relativeHumiditySetpoint: Int)

/**
 * Interface for receiving live data updates
 */
interface ILiveDataReceived {
    fun onLiveDataReceived(liveData: LiveData)
}

/**
 * Interface for registering / unregistering to a live data source
 */
interface ILiveDataSource {
    fun addObserver(observer : ILiveDataReceived)
    fun removeObserver(observer : ILiveDataReceived)
}

