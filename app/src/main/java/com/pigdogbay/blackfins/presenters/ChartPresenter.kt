package com.pigdogbay.blackfins.presenters

import android.util.Log
import com.pigdogbay.blackfins.model.ILiveDataReceived
import com.pigdogbay.blackfins.model.LiveData
import com.pigdogbay.blackfins.model.LiveDataLog
import com.pigdogbay.blackfins.model.LiveDataThread

/**
 * Created by mark on 22/02/18.
 * Presenter and View interface for the Chart fragment
 */
interface IChartView {
    fun addData(x : Float, y : Float, atSetIndex : Int)
    fun updateChart()
    fun showError(message : String)
}
class ChartPresenter(private val log: LiveDataLog, private val liveDataThread: LiveDataThread) : ILiveDataReceived {
    lateinit var view : IChartView

    fun onResume(){
        liveDataThread.addObserver(this)
        addExisitingData()
    }

    fun onPause(){
        liveDataThread.removeObserver(this)
    }

    private fun addExisitingData(){
        //can throw an exception if you flick back and forth quickly between screens
        try {
            for (ld in log.getLog()) {
                addPoint(ld)
            }
            //chart crashes if empty
            if (log.getLog().size == 0) {
                view.addData(0.0f, 0.0f, 0)
                view.addData(0.0f, 0.0f, 1)
            }
            view.updateChart()
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun onLiveDataReceived(liveData: LiveData) {
        addPoint(liveData)
        view.updateChart()
    }

    override fun onLiveDataError(message: String) {
        view.showError(message)
    }

    private fun addPoint(liveData: LiveData){
        val elapsedTime = log.elapsedTime(liveData)/1000L
        val xvalue = (elapsedTime.toFloat())/60f
        view.addData(xvalue,liveData.temperature,0)
        view.addData(xvalue,liveData.temperatureSetpoint,1)
    }
}