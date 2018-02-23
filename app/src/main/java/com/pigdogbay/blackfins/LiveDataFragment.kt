package com.pigdogbay.blackfins

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        presenter = LiveDataPresenter()
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

    override fun setTemperature(temperature: String) {
        textTemperature.text = temperature
    }

    override fun setRelativeHumidity(relHumidity: String) {
        textRelHumidity.text = relHumidity
    }
}