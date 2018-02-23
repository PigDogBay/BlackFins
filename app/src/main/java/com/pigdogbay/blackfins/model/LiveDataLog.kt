package com.pigdogbay.blackfins.model

import java.util.*

/**
 * Created by mark on 23/02/18.
 * Logs Live Data
 */
class LiveDataLog(val source : ILiveDataSource) : ILiveDataReceived {

    private val log =  Collections.synchronizedList(ArrayList<LiveData>())

    fun getLog() : List<LiveData> = log

    fun startLogging(){
        source.addObserver(this)
    }
    fun stopLogging(){
        source.removeObserver(this)
    }

    override fun onLiveDataReceived(liveData: LiveData) {
        log.add(liveData)
    }
}