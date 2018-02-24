package com.pigdogbay.blackfins.model.fins

import com.pigdogbay.blackfins.utils.toInetAddress
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

/**
 * Created by mark on 24/02/18.
 * Wrapper for a UDP Socket
 */
class FinsSocket {
    var socket : DatagramSocket = DatagramSocket()
    lateinit var address : InetAddress
    var port = 9600
    val byteArray = ByteArray(256)

    fun setAddress(ipAddress : String, port : Int){
        this.port = port
        address = toInetAddress(ipAddress)
    }

    fun send(bytes : ByteArray) {
        val packet = DatagramPacket(bytes, 0, bytes.size, address, port)
        socket.send(packet)
    }

    fun receive() : ByteArray {
        val responsePacket = DatagramPacket(byteArray,256)
        socket.receive(responsePacket)
        return byteArray
    }

}