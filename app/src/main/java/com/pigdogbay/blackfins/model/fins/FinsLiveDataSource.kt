package com.pigdogbay.blackfins.model.fins

import com.pigdogbay.blackfins.model.ILiveDataSource
import com.pigdogbay.blackfins.model.LiveData
import com.pigdogbay.blackfins.utils.toByteArray
import com.pigdogbay.blackfins.utils.toIntArray
import java.io.InvalidObjectException

/**
 * Created by mark on 24/02/18.
 * Communicates with PLC to get data for a LiveData object
 */
class FinsLiveDataSource(val finsSocket: FinsSocket) : ILiveDataSource{

    private val finsCmdTemperature : FinsCommand = FinsCommandBuilder()
            .atMemoryAddress("258")
            .inMemoryArea(FinsMemoryArea.DMWord)
            .build()

    override fun getLiveData(): LiveData {
        val data = toByteArray(finsCmdTemperature.FinsDatagram)
        finsSocket.send(data)
        val responseData = toIntArray(finsSocket.receive())
        if (!finsCmdTemperature.parseFinsResponse(responseData)){
            throw InvalidObjectException("Unexpected response from the PLC")
        }
        var temperature = finsCmdTemperature.getResponseWord().toFloat()
        //Temperature in 0.1°C
        temperature = temperature/10.0f
        return LiveData(temperature,80f,0,0)
    }
}