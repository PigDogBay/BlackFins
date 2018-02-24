package com.pigdogbay.blackfins.presenters

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
            var xvalue = 0.0f
            for (ld in log.getLog()) {
                view.addData(xvalue, ld.temperature, 0)
                view.addData(xvalue, ld.temperatureSetpoint, 1)
                xvalue++
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
        val xvalue = log.getLog().size.toFloat()
        view.addData(xvalue,liveData.temperature,0)
        view.addData(xvalue,liveData.temperatureSetpoint,1)
        view.updateChart()
    }

    override fun onLiveDataError(message: String) {
        view.showError(message)
    }


}