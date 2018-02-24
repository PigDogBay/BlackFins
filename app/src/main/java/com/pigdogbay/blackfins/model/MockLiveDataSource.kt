package com.pigdogbay.blackfins.model

import android.os.HandlerThread

/**
 * Created by mark on 23/02/18.
 * Runs a worker thread generating computed data
 */
class MockLiveDataSource : ILiveDataSource {

    var count = 0

    override fun getLiveData(): LiveData {
        val x = count.toFloat() + 1
        val y = 80.0f - Math.min(80.0f, 2000.0f/x) + Math.random().toFloat()*3.0f
        count++
        return LiveData(y,80.0f,42,50)

    }
}