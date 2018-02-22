package com.pigdogbay.blackfins

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_chart.*

class ChartFragment : Fragment() {

    companion object {
        const val TAG = "chart"
    }

    var primaryColor: Int = 0
    var primaryDarkColor: Int = 0
    var accentColor: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        primaryColor = ContextCompat.getColor(activity,R.color.colorPrimary)
        primaryDarkColor = ContextCompat.getColor(activity,R.color.colorPrimaryDark)
        accentColor = ContextCompat.getColor(activity,R.color.colorAccent)
        setUpChart()
        createData()
    }

    private fun setUpChart() {
        chart.description.isEnabled = true
        chart.description.text = "Temperature (°C)  versus  Time (minutes)"

        chart.setTouchEnabled(true)
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setDrawGridBackground(false)
        chart.setPinchZoom(true)

        chart.setBackgroundColor(Color.WHITE)

        chart.legend.isEnabled = false

        val lineData = LineData()
        lineData.setValueTextColor(Color.BLACK)
        chart.data = lineData

        val xAxis = chart.xAxis
        xAxis.textColor = Color.BLACK
        xAxis.setDrawGridLines(true)
        xAxis.setAvoidFirstLastClipping(true)
        xAxis.axisMinimum = 0.0f
        xAxis.axisMaximum = 300.0f
        xAxis.isEnabled = true
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val leftAxis = chart.axisLeft
        leftAxis.textColor = Color.BLACK
        leftAxis.setDrawGridLines(true)
        leftAxis.axisMaximum = 100.0f
        leftAxis.axisMinimum = 0.0f


        chart.axisRight.isEnabled = false
    }

    private fun createData() {
        val data = chart.data
        val set = createSet()
        data.addDataSet(set)
        for (i in 0..300 step 10) {
            val x = i.toFloat() + 1
            val y = 80.0f - Math.min(80.0f, 2000.0f/x) + Math.random().toFloat()*3.0f
            data.addEntry(Entry(x, y), 0)
        }

        val set2 = createSet2()
        data.addDataSet(set2)
        data.addEntry(Entry(0.0f,80.0f),1)
        data.addEntry(Entry(300.0f,80.0f),1)

        data.notifyDataChanged()
        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    private fun createSet(): LineDataSet {

        val set = LineDataSet(null, "Plant")
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.color = primaryDarkColor
        set.setCircleColor(primaryColor)
        set.lineWidth = 2f
        set.circleRadius = 4f
        set.fillAlpha = 65
        set.fillColor = primaryColor
        set.highLightColor = accentColor
        set.valueTextColor = primaryColor
        set.valueTextSize = 9f
        set.setDrawValues(false)
        return set
    }
    private fun createSet2(): LineDataSet {

        val set = LineDataSet(null, "Set Point")
        set.axisDependency = YAxis.AxisDependency.LEFT
        val color = accentColor
        set.color = color
        set.setCircleColor(color)
        set.lineWidth = 1f
        set.circleRadius = 2f
        set.fillAlpha = 65
        set.fillColor = color
        set.highLightColor = color
        set.valueTextColor = color
        set.valueTextSize = 9f
        set.setDrawValues(false)
        return set
    }


}