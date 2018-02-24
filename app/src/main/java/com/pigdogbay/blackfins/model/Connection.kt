package com.pigdogbay.blackfins.model

/**
 * Created by mark on 24/02/18.
 * Handles connection to PLC
 */
enum class ConnectionStatus(){
    DISCONNECTED, CONNECTING, CONNECTED, CONNECTION_ERROR
}

interface IConnectionChangeListener {
    fun onConnectionChanged(connectionStatus: ConnectionStatus)
}
class Connection(val liveDataThread: LiveDataThread) : ILiveDataReceived {

    private val listeners = ArrayList<IConnectionChangeListener>()
    init {
        liveDataThread.addObserver(this)
    }

    var status = ConnectionStatus.DISCONNECTED
    var error = ""

    fun addListener(listener: IConnectionChangeListener){
        listeners.add(listener)
    }
    fun removeListener(listener: IConnectionChangeListener){
        listeners.remove(listener)
    }

    fun onStatusChanged(newStatus: ConnectionStatus){
        status = newStatus
        for (l in listeners){
            l.onConnectionChanged(status)
        }
    }

    fun connect() {
        liveDataThread.startMessaging()
        onStatusChanged(ConnectionStatus.CONNECTING)

    }

    fun disconnect() {
        liveDataThread.stopMessaging()
        onStatusChanged(ConnectionStatus.DISCONNECTED)
    }

    override fun onLiveDataReceived(liveData: LiveData) {
        if (status == ConnectionStatus.CONNECTING) onStatusChanged(ConnectionStatus.CONNECTED)
    }

    override fun onLiveDataError(message: String) {
        liveDataThread.stopMessaging()
        error = message
        onStatusChanged(ConnectionStatus.CONNECTION_ERROR)
    }
}