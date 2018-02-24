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
        settings.updateUserSettings()

        //val liveDataSource = MockLiveDataSource()
        socket = FinsSocket()
        socket.setAddress(settings.ipAddress, settings.port)
        val liveDataSource = FinsLiveDataSource(socket)

        liveDataThread = LiveDataThread(liveDataSource, settings.userSettings)
        liveDataThread.start()
        liveDataLog = LiveDataLog(liveDataThread)
        liveDataLog.startLogging()
        connection = Connection(liveDataThread)

        isBuilt = true
    }

    fun dispose(){
        liveDataLog.stopLogging()
        liveDataThread.dispose()
    }
}