package com.pigdogbay.blackfins.model

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import java.net.SocketTimeoutException

/**
 * Created by mark on 24/02/18.
 * Runs a worker thread generating computed data
 */
class LiveDataThread(private val liveDataSource: ILiveDataSource) : HandlerThread("MockData") {

    companion object {
        private const val MESSAGE_GET_LIVE_DATA = 42
    }
    private val listeners = ArrayList<ILiveDataReceived>()
    private lateinit var handler: Handler
    private var isRunning = false
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
    }

    private fun runMessageGetLiveData(){
        try {
            val liveData = liveDataSource.getLiveData()
            onNewLiveData(liveData)
        }catch (stoe: SocketTimeoutException){
            onError(stoe.message ?: "Timed Out - Check connection")
        } catch (e : Exception){
            e.printStackTrace()
            onError(e.message ?: "Error")
        }
        //keep updating
        if (isRunning) {
            val message = handler.obtainMessage(Companion.MESSAGE_GET_LIVE_DATA)
            handler.sendMessageDelayed(message, updateDelay)
        }
    }

    private fun onNewLiveData(liveData: LiveData){
        for (l in listeners){
            l.onLiveDataReceived(liveData)
        }
    }
    private fun onError(message : String){
        for (l in listeners){
            l.onLiveDataError(LiveError(message))
        }
    }

    fun startMessaging(){
        isRunning = true
        //start immediately
        val message = handler.obtainMessage(Companion.MESSAGE_GET_LIVE_DATA)
        handler.sendMessage(message)

    }

    fun stopMessaging(){
        isRunning = false
        handler.removeMessages(Companion.MESSAGE_GET_LIVE_DATA)
    }

    fun dispose() {
        stopMessaging()
        quit()
    }

}
