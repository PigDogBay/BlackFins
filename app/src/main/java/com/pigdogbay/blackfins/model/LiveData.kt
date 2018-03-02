package com.pigdogbay.blackfins.model

import java.util.*

/**
 * Created by mark on 23/02/18.
 * Temperature in °C and relative humidity is %
 */
data class LiveData(val temperature : Float, val temperatureSetpoint: Float, val relativeHumidity : Int, val relativeHumiditySetpoint: Int){
    var date = Date()
}

data class LiveError(val message : String){
    var date = Date()
}

/**
 * Observer interface for receiving live data updates
 */
interface ILiveDataReceived {
    fun onLiveDataReceived(liveData: LiveData)
    fun onLiveDataError(liveError : LiveError)
}

/**
 * Interface for getting LiveData objects
 */
interface ILiveDataSource {
    fun getLiveData() : LiveData
}

