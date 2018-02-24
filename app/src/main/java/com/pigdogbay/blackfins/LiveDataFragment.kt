package com.pigdogbay.blackfins

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pigdogbay.blackfins.model.Injector
import com.pigdogbay.blackfins.presenters.ILiveDataView
import com.pigdogbay.blackfins.presenters.LiveDataPresenter
import kotlinx.android.synthetic.main.fragment_live_data.*

class LiveDataFragment : Fragment(), ILiveDataView {
    companion object {
        const val TAG = "live data"
    }

    private lateinit var presenter: LiveDataPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_live_data, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LiveDataPresenter(Injector.liveDataLog, Injector.liveDataThread)
        presenter.view = this
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun setLastUpdateTime(time: String) {
        activity.runOnUiThread {
            textTimeStamp.text = time
        }
    }
    override fun setTemperature(temperature: String, setpoint: String) {
        activity.runOnUiThread {
            textTemperature.text = temperature
            textTemperatureSetpoint.text = setpoint
        }
    }

    override fun setRelativeHumidity(relHumidity: String, setpoint: String) {
        activity.runOnUiThread {
            textRelHumidity.text = relHumidity
            textHumiditySetpoint.text = setpoint
        }
    }

    override fun showError(message: String) {
        activity.runOnUiThread {
            AlertDialog.Builder(activity)
                    .setTitle("Connection Error")
                    .setMessage("Unable to connect to the PLC due to: $message")
                    .setNeutralButton("OK",null)
                    .show()
        }
    }
}