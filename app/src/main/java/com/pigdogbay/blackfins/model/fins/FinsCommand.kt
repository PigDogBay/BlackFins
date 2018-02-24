package com.pigdogbay.blackfins.model.fins

/**
 * Created by mark on 21/02/18.
 * FINS data structure
 */

enum class FinsMessageType { COMMAND, RESPONSE}
enum class FinsMemoryArea (val b: Int) {
    CIOBit(0x30),WRBit(0x31), HRBit(0x32), ARBit(0x33),
    CIOWord(0xB0), WRWord(0xB1), HRWord(0xB2), ARWord(0xB3),
    DMBit(0x81), DMWord(0x82)
}

class FinsCommand {

    companion object {
        private const val OFFSET_SERVICE_ID = 9
        private const val OFFSET_MAIN_RESPONSE_CODE = 12
        private const val OFFSET_SUB_RESPONSE_CODE = 13
        private const val OFFSET_DATA = 14
    }

    var responseRequired = false
    var messageType = FinsMessageType.COMMAND

    var numGateways = 0x02
    var destNetworkAddress = 0
    var destNodeAddress = 0
    var destUnitAddress = 0
    var srcNetworkAddress = 0
    var srcNodeAddress = 0xd8
    var srcUnitAddress = 0
    var serviceId = 0

    var mainRequestCode = 0
    var subRequestCode = 0
    var mainResponseCode = 0
    var subResponseCode = 0

    var responseBound = false

    var memoryArea = FinsMemoryArea.CIOBit
    var registerAddress = 0 //16bit quantity
    var registerBitPosition = 0

    var numElements = 0 //16bit quantity

    var data : List<Int>? = null
    var responseData : List<Int>? = null
    var dataReturnExpected = false

    val FinsDatagram : List<Int> get() {
        val finsMessage = ArrayList<Int>()
        var responseReq =  when (messageType){
            FinsMessageType.COMMAND-> 0x80
            FinsMessageType.RESPONSE-> 0x81
        }
        if (responseRequired){
            responseReq = responseReq or (1 shl 6)
        }
        finsMessage.add(responseReq)
        finsMessage.add(0)
        finsMessage.add(numGateways)
        finsMessage.add(destNetworkAddress)
        finsMessage.add(destNodeAddress)
        finsMessage.add(destUnitAddress)
        finsMessage.add(srcNetworkAddress)
        finsMessage.add(srcNodeAddress)
        finsMessage.add(srcUnitAddress)
        finsMessage.add(serviceId)
        finsMessage.add(mainRequestCode)
        finsMessage.add(subRequestCode)
        finsMessage.add(memoryArea.b)
        finsMessage.add(registerAddress shr 8)
        finsMessage.add(registerAddress and 0xff)
        finsMessage.add(registerBitPosition)
        if (dataReturnExpected){
            finsMessage.add(numElements shr 8)
            finsMessage.add(numElements and 0xff)
        } else {
            finsMessage.add(numElements shr 8)
            finsMessage.add(numElements and 0xff)
            finsMessage.addAll(data!!)
        }
        return finsMessage
    }


    private fun isMatchingResponse(datagram : List<Int>) : Boolean {
        return datagram[OFFSET_SERVICE_ID] == serviceId
                && datagram[OFFSET_MAIN_RESPONSE_CODE] == mainRequestCode
                && datagram[OFFSET_SUB_RESPONSE_CODE] == subRequestCode
    }

    fun parseFinsResponse(datagram : List<Int>) : Boolean{
        if (!isMatchingResponse(datagram)) return false
        mainResponseCode = datagram[OFFSET_MAIN_RESPONSE_CODE]
        subResponseCode = datagram[OFFSET_SUB_RESPONSE_CODE]
        responseData = if (datagram.size > OFFSET_DATA) datagram.subList(OFFSET_DATA,datagram.size) else null
        return true
    }

    fun getResponseWord() :Int{
        return ((responseData?.get(0) ?: 0) shr 8) or (responseData?.get(1) ?: 0)
    }

    var memoryAddress : String get() = "$registerAddress.$registerBitPosition"
    set(str) {
        val split = str.split(".")
        registerAddress = Integer.parseInt(split[0])
        if (split.size==2) registerBitPosition = Integer.parseInt(split[1])
    }

}