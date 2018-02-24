package com.pigdogbay.blackfins.model

import android.os.Handler
import android.os.HandlerThread
import android.os.Message

/**
 * Created by mark on 24/02/18.
 * Runs a worker thread generating computed data
 */
class LiveDataThread(val liveDataSource: ILiveDataSource) : HandlerThread("MockData") {

    companion object {
        private const val MESSAGE_GET_LIVE_DATA = 42
    }
    private val listeners = ArrayList<ILiveDataReceived>()
    private var isRunning = false
    private lateinit var handler: Handler

    var updateDelay = 1000L

    fun addObserver(observer: ILiveDataReceived) {
        listeners.add(observer)
    }

    fun removeObserver(observer: ILiveDataReceived) {
        listeners.remove(observer)
    }

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        handler = object : Handler(looper) {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                when (msg?.what){
                    Companion.MESSAGE_GET_LIVE_DATA -> runMessageGetLiveData()
                }
            }
        }
        postLiveDataUpdate()
    }

    private fun postLiveDataUpdate(){
        val message = handler.obtainMessage(Companion.MESSAGE_GET_LIVE_DATA)
        handler.sendMessageDelayed(message,updateDelay)
    }

    private fun runMessageGetLiveData(){
        if (isRunning) {
            try {
                val liveData = liveDataSource.getLiveData()
                onNewLiveData(liveData)
            } catch (e : Exception){
                onError(e.message ?: "Error")
            }

            //keep updating
            postLiveDataUpdate()
        }
    }

    private fun onNewLiveData(liveData: LiveData){
        for (l in listeners){
            l.onLiveDataReceived(liveData)
        }
    }
    private fun onError(message : String){
        for (l in listeners){
            l.onLiveDataError(message)
        }
    }

    fun startUpdating(){
        isRunning = true
        postLiveDataUpdate()
    }

    fun stopUpdating(){
        handler.removeMessages(Companion.MESSAGE_GET_LIVE_DATA)
        isRunning = false

    }

    fun dispose() {
        handler.removeMessages(Companion.MESSAGE_GET_LIVE_DATA)
        quit()
    }

}
