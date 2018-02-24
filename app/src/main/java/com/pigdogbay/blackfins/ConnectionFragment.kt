package com.pigdogbay.blackfins

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pigdogbay.blackfins.model.Injector
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
        presenter = ConnectionPresenter(Injector.connection)
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
        activity.runOnUiThread {
            textTemperature.text = status
        }
    }

    override fun setConnectEnabled(enabled: Boolean) {
        activity.runOnUiThread {
            btnConnect.isEnabled = enabled
        }
    }

    override fun setDisconnectEnabled(enabled: Boolean) {
        activity.runOnUiThread {
            btnDisconnect.isEnabled = enabled
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
