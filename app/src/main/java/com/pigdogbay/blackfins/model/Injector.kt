package com.pigdogbay.blackfins.model

import android.annotation.SuppressLint
import android.content.Context
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

    private var isBuilt = false

    fun create(context : Context){
        if (isBuilt){
            return
        }
        preferencesHelper = PreferencesHelper(context.applicationContext)
        settings = Settings(preferencesHelper)
        settings.updateUserSettings()

        val liveDataSource = MockLiveDataSource()
        liveDataThread = LiveDataThread(liveDataSource, settings.userSettings)
        liveDataLog = LiveDataLog(liveDataThread)
        liveDataThread.start()

        isBuilt = true
    }

    fun dispose(){
        liveDataLog.stopLogging()
        liveDataThread.dispose()
    }
}