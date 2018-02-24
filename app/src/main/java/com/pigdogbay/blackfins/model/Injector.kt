package com.pigdogbay.blackfins.model

import android.annotation.SuppressLint
import android.content.Context
import com.pigdogbay.lib.utils.PreferencesHelper

/**
 * Created by mark on 23/02/18.
 * Dependency Injection Container
 */
object Injector {

    lateinit var log : LiveDataLog
    @SuppressLint("StaticFieldLeak")
    lateinit var preferencesHelper: PreferencesHelper
    lateinit var settings: Settings

    private var isBuilt = false

    fun create(context : Context){
        if (isBuilt){
            return
        }
        preferencesHelper = PreferencesHelper(context.applicationContext)
        settings = Settings(preferencesHelper)
        isBuilt = true
    }

    fun dispose(){

    }
}