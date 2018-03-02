package com.pigdogbay.blackfins.model

import java.util.*

/**
 * Created by mark on 23/02/18.
 * Logs Live Data
 */
class LiveDataLog(val liveDataThread: LiveDataThread) : ILiveDataReceived {
    private val log =  Collections.synchronizedList(ArrayList<LiveData>())
    private val errorLog =  Collections.synchronizedList(ArrayList<LiveError>())

    fun getLog() : List<LiveData> = log
    fun getErrorLog() : List<LiveError> = errorLog

    fun startLogging(){
        liveDataThread.addObserver(this)
    }
    fun stopLogging(){
        liveDataThread.removeObserver(this)
    }

    fun elapsedTime(liveData: LiveData) : Long {
        return liveData.date.time - log[0].date.time
    }


    val latest : LiveData?
        get() = if (log.size==0) null else log.last()


    override fun onLiveDataReceived(liveData: LiveData) {
        log.add(liveData)
    }

    override fun onLiveDataError(liveError: LiveError) {
        errorLog.add(liveError)
    }
}