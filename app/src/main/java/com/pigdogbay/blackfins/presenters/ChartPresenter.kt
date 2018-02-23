package com.pigdogbay.blackfins.presenters

import android.graphics.PointF
import com.github.mikephil.charting.data.Entry

/**
 * Created by mark on 22/02/18.
 * Presenter and View interface for the Chart fragment
 */
interface IChartView {
    fun addData(x : Float, y : Float, atSetIndex : Int)
    fun updateChart()
}
class ChartPresenter {
    lateinit var view : IChartView

    fun onResume(){
        for (i in 0..300 step 10) {
            val x = i.toFloat() + 1
            val y = 80.0f - Math.min(80.0f, 2000.0f/x) + Math.random().toFloat()*3.0f
            view.addData(x,y,0)
        }
        view.addData(0.0f,80.0f,1)
        view.addData(300.0f,80.0f,1)
        view.updateChart()
    }

    fun onPause(){

    }
}