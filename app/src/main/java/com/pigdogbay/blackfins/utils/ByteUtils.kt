package com.pigdogbay.blackfins.utils

/**
 * Created by mark on 21/02/18.
 * Helper functions for ByteArray
 */

fun toByteArray(integers : List<Int>) : ByteArray {
    val byteArray = ByteArray(integers.size)
    for (i in 0 until integers.size){
        byteArray[i] = integers[i].toByte()
    }
    return byteArray
}

fun toIntArray(byteArray: ByteArray) : List<Int> {
    val list = ArrayList<Int>()
    for (b in byteArray){
        list.add(b.toInt() and 0xff)
    }
    return list
}

fun print(byteArray: ByteArray,from : Int, upto : Int) : String{
    val sb = StringBuilder()
    for (i in from until upto){
        val x = byteArray[i].toInt() and 0xff
        sb.append("%02X ".format(x))
    }
    return sb.toString()
}