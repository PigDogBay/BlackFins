package com.pigdogbay.blackfins.model

import android.annotation.SuppressLint
import android.content.Context
import com.pigdogbay.blackfins.model.fins.FinsLiveDataSource
import com.pigdogbay.blackfins.model.fins.FinsSocket
import com.pigdogbay.blackfins.model.fins.ISocket
import com.pigdogbay.lib.utils.PreferencesHelper

/**
 * Created by mark on 23/02/18.
 * Dependency Injection Container
 */
object Injector {

    @SuppressLint("StaticFieldLeak")
    lateinit var preferencesHelper: PreferencesHelper
    lateinit var settings: Settings
    lateinit var liveDataThread: LiveDataThread
    lateinit var liveDataLog: LiveDataLog
    lateinit var connection: Connection
    lateinit var socket : ISocket

    private var isBuilt = false

    fun create(context : Context){
        if (isBuilt){
            return
        }
        preferencesHelper = PreferencesHelper(context.applicationContext)
        settings = Settings(preferencesHelper)

        //val liveDataSource = MockLiveDataSource()
        socket = FinsSocket()
        val liveDataSource = FinsLiveDataSource(socket)

        liveDataThread = LiveDataThread(liveDataSource)
        liveDataThread.start()
        liveDataLog = LiveDataLog(liveDataThread)
        liveDataLog.startLogging()
        connection = Connection(liveDataThread)

        applySettings()

        isBuilt = true
    }

    fun applySettings(){
        socket.setAddress(settings.ipAddress, settings.port)
        liveDataThread.updateDelay = settings.updateFrequency*1000L
    }
    fun dispose(){
        liveDataLog.stopLogging()
        liveDataThread.dispose()
    }
}