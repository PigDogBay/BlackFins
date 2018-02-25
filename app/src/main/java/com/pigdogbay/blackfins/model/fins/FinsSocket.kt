package com.pigdogbay.blackfins.model.fins

import com.pigdogbay.blackfins.utils.toInetAddress
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

interface ISocket {
    fun setAddress(ipAddress : String, port : Int)
    fun send(bytes : ByteArray)
    fun receive() : ByteArray
}

/**
 * Created by mark on 24/02/18.
 * FINS UDP Socket
 */
class FinsSocket : ISocket {
    var socket : DatagramSocket = DatagramSocket()

    init {
        socket.soTimeout=2000
    }

    lateinit var address : InetAddress
    var port = 9600
    val byteArray = ByteArray(256)

    override fun setAddress(ipAddress : String, port : Int){
        this.port = port
        address = toInetAddress(ipAddress)
    }

    override fun send(bytes : ByteArray) {
        val packet = DatagramPacket(bytes, 0, bytes.size, address, port)
        socket.send(packet)
    }

    override fun receive() : ByteArray {
        val responsePacket = DatagramPacket(byteArray,256)
        socket.receive(responsePacket)
        return byteArray
    }

}