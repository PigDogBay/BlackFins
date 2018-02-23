package com.pigdogbay.blackfins.presenters

import android.graphics.PointF

/**
 * Created by mark on 22/02/18.
 * Presenter and View interface for the Chart fragment
 */
interface IChartView {
    fun setTemperature(points : List<PointF>)
    fun setRelativeHumidity(points : List<PointF>)
    fun addTemperature(point : Float)
    fun addRelativeHumidity(point : Float)
}
class ChartPresenter {
    lateinit var view : IChartView

    fun onResume(){

    }

    fun onPause(){

    }
}