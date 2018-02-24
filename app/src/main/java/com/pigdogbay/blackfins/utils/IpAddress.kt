package com.pigdogbay.blackfins.utils

import java.net.InetAddress

/**
 * Created by mark on 24/02/18.
 * Converts string ip address, eg 192.168.0.200 to InetAddress
 */

fun toInetAddress(address : String) : InetAddress{
    val parts = address.split(".")
    val byteArray = ByteArray(4)
    byteArray[0] = Integer.parseInt(parts[0]).toByte()
    byteArray[1] = Integer.parseInt(parts[1]).toByte()
    byteArray[2] = Integer.parseInt(parts[2]).toByte()
    byteArray[3] = Integer.parseInt(parts[3]).toByte()
    return InetAddress.getByAddress(byteArray)

}