package com.pigdogbay.blackfins.model

/**
 * Created by mark on 23/02/18.
 * Temperature in °C and relative humidity is %
 */
data class LiveData(val temperature : Float, val temperatureSetpoint: Float, val relativeHumidity : Int, val relativeHumiditySetpoint: Int)

/**
 * Observer interface for receiving live data updates
 */
interface ILiveDataReceived {
    fun onLiveDataReceived(liveData: LiveData)
    fun onLiveDataError(message : String)
}

/**
 * Interface for getting LiveData objects
 */
interface ILiveDataSource {
    fun getLiveData() : LiveData
}

