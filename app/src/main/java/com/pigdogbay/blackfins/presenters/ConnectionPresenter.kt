package com.pigdogbay.blackfins.presenters

import com.pigdogbay.blackfins.model.*

/**
 * Created by mark on 22/02/18.
 * Presenter and View interface for the Connection Fragment
 */
interface IConnectionView {
    fun setStatus(status : String)
    fun setConnectEnabled( enabled : Boolean)
    fun setDisconnectEnabled( enabled : Boolean)
    fun showError(message : String)
}
class ConnectionPresenter(val connection: Connection) : IConnectionChangeListener {

    lateinit var view : IConnectionView

    fun onResume(){
        connection.addListener(this)
        modelToView(connection.status)
    }
    fun onPause(){
        connection.removeListener(this)
    }

    private fun modelToView(status: ConnectionStatus){
        when (status){
            ConnectionStatus.DISCONNECTED ->{
                view.setStatus("Not Connected")
                view.setConnectEnabled(true)
                view.setDisconnectEnabled(false)
            }
            ConnectionStatus.CONNECTING ->{
                view.setStatus("Connecting")
                view.setConnectEnabled(false)
                view.setDisconnectEnabled(false)
            }
            ConnectionStatus.CONNECTED ->{
                view.setStatus("Connected")
                view.setConnectEnabled(false)
                view.setDisconnectEnabled(true)
            }
            ConnectionStatus.CONNECTION_ERROR ->{
                view.setStatus("Connection Error")
                view.setConnectEnabled(true)
                view.setDisconnectEnabled(false)
            }
        }
    }


    fun connect() {
        connection.connect()
    }
    fun disconnect() {
        connection.disconnect()
    }

    override fun onConnectionChanged(connectionStatus: ConnectionStatus) {
        modelToView(connectionStatus)
        if (connectionStatus == ConnectionStatus.CONNECTION_ERROR){
            view.showError(connection.liveError.message)
        }
    }

}