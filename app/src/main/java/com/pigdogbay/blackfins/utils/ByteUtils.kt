package com.pigdogbay.blackfins.utils

/**
 * Created by mark on 21/02/18.
 * Help functions for ByteArray
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