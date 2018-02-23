package com.pigdogbay.blackfins.presenters

/**
 * Created by mark on 22/02/18.
 * Presenter and View Interface for Live Data Fragment
 */
interface ILiveDataView {
    fun setTemperature(temperature : String)
    fun setRelativeHumidity(relHumidity : String)
}
class LiveDataPresenter {
    lateinit var view : ILiveDataView

    fun onResume(){
        view.setRelativeHumidity("42%")
        //alt-gr + shift + 0  for °
        view.setTemperature("83.9°C")
    }

    fun onPause(){

    }


}