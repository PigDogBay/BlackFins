package com.pigdogbay.blackfins.presenters

/**
 * Created by mark on 22/02/18.
 * Presenter and View interface for the Connection Fragment
 */
interface IConnectionView {
    fun setStatus(status : String)
    fun setConnectEnabled( enabled : Boolean)
    fun setDisconnectEnabled( enabled : Boolean)

}
class ConnectionPresenter {

    lateinit var view : IConnectionView

}