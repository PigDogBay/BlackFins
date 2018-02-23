package com.pigdogbay.blackfins

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pigdogbay.blackfins.presenters.ConnectionPresenter
import com.pigdogbay.blackfins.presenters.IConnectionView
import kotlinx.android.synthetic.main.fragment_connection.*

class ConnectionFragment : Fragment(), IConnectionView {
    companion object {
        const val TAG = "connection"
    }

    private lateinit var presenter: ConnectionPresenter


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_connection, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ConnectionPresenter()
        presenter.view = this
        btnConnect.setOnClickListener{presenter.connect()}
        btnDisconnect.setOnClickListener{presenter.disconnect()}
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun setStatus(status: String) {
        textTemperature.text = status
    }

    override fun setConnectEnabled(enabled: Boolean) {
        btnConnect.isEnabled = enabled
    }

    override fun setDisconnectEnabled(enabled: Boolean) {
        btnDisconnect.isEnabled = enabled
    }
}
