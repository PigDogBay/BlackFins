package com.pigdogbay.blackfins.presenters

import com.pigdogbay.blackfins.model.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by mark on 22/02/18.
 * Presenter and View Interface for Live Data Fragment
 */
interface ILiveDataView {
    fun setLastUpdateTime(time : String)
    fun setTemperature(temperature : String, setpoint : String)
    fun setRelativeHumidity(relHumidity : String, setpoint : String)
    fun showError(message: String)
}
class LiveDataPresenter(private val log: LiveDataLog, private val liveDataThread: LiveDataThread) : ILiveDataReceived {
    lateinit var view : ILiveDataView

    private val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.UK)

    fun onResume(){
        liveDataToView(log.latest)
        liveDataThread.addObserver(this)
    }

    fun onPause(){
        liveDataThread.removeObserver(this)
    }

    private fun liveDataToView(liveData: LiveData?){
        if (liveData==null){
            view.setLastUpdateTime("No Data")
            view.setRelativeHumidity("","")
            view.setTemperature("","")
        } else {
            view.setRelativeHumidity("${liveData.relativeHumidity}%", "${liveData.relativeHumiditySetpoint}%")
            //alt-gr + shift + 0  for °
            view.setTemperature("%.1f°C".format(liveData.temperature), "%.1f°C".format(liveData.temperatureSetpoint))
            view.setLastUpdateTime(simpleDateFormat.format(liveData.date))
        }
    }

    override fun onLiveDataReceived(liveData: LiveData) {
        liveDataToView(liveData)
    }

    override fun onLiveDataError(liveError: LiveError) {
        view.showError(liveError.message)
    }
}