package com.pigdogbay.blackfins.model.fins

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
        val parts = ipAddress.split(".")
        val byteArray = ByteArray(4)
        byteArray[0] = Integer.parseInt(parts[0]).toByte()
        byteArray[1] = Integer.parseInt(parts[1]).toByte()
        byteArray[2] = Integer.parseInt(parts[2]).toByte()
        byteArray[3] = Integer.parseInt(parts[3]).toByte()
        address = InetAddress.getByAddress(byteArray)
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