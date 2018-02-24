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
class FinsLiveDataSource(val socket: ISocket) : ILiveDataSource{

    //2 byte reponse, BCD 0.1°C
    private val finsCmdTemperature : FinsCommand = FinsCommandBuilder()
            .atMemoryAddress("258")
            .inMemoryArea(FinsMemoryArea.DMWord)
            .build()
    //2 byte reponse, BCD 0.1°C
    private val finsCmdTemperatureSetpoint : FinsCommand = FinsCommandBuilder()
            .atMemoryAddress("280")
            .inMemoryArea(FinsMemoryArea.DMWord)
            .build()
    //2 byte reponse, BCD 0-100%
    private val finsCmdRelativeHumidity : FinsCommand = FinsCommandBuilder()
            .atMemoryAddress("412")
            .inMemoryArea(FinsMemoryArea.DMWord)
            .build()
    //2 byte reponse, BCD 0-100%
    private val finsCmdRelativeHumiditySetpoint : FinsCommand = FinsCommandBuilder()
            .atMemoryAddress("730")
            .inMemoryArea(FinsMemoryArea.DMWord)
            .build()

    override fun getLiveData(): LiveData {
        val temperature = readTemperature()
        val tempSetpoint = readTemperatureSetpoint()
        val humidity = readRelativeHumidity()
        val humiditySetpoint = readRelativeHumiditySetpoint()
        return LiveData(temperature,tempSetpoint,humidity,humiditySetpoint)
    }

    private fun readTemperature() : Float {
        val data = toByteArray(finsCmdTemperature.FinsDatagram)
        socket.send(data)
        val responseData = toIntArray(socket.receive())
        if (!finsCmdTemperature.parseFinsResponse(responseData)){
            throw InvalidObjectException("Unexpected response reading temperature from the PLC")
        }
        //Temperature in 0.1°C
        return finsCmdTemperature.getResponseWord().toFloat()/10f
    }

    private fun readTemperatureSetpoint() : Float {
        val data = toByteArray(finsCmdTemperatureSetpoint.FinsDatagram)
        socket.send(data)
        val responseData = toIntArray(socket.receive())
        if (!finsCmdTemperatureSetpoint.parseFinsResponse(responseData)){
            throw InvalidObjectException("Unexpected response reading temperature setpoint from the PLC")
        }
        //Temperature Setpoint in 0.1°C
        return finsCmdTemperatureSetpoint.getResponseWord().toFloat()/10f
    }

    private fun readRelativeHumidity() : Int {
        val data = toByteArray(finsCmdRelativeHumidity.FinsDatagram)
        socket.send(data)
        val responseData = toIntArray(socket.receive())
        if (!finsCmdRelativeHumidity.parseFinsResponse(responseData)){
            throw InvalidObjectException("Unexpected response reading rel humidity from the PLC")
        }
        return finsCmdRelativeHumidity.getResponseWord()
    }

    private fun readRelativeHumiditySetpoint() : Int {
        val data = toByteArray(finsCmdRelativeHumiditySetpoint.FinsDatagram)
        socket.send(data)
        val responseData = toIntArray(socket.receive())
        if (!finsCmdRelativeHumiditySetpoint.parseFinsResponse(responseData)){
            throw InvalidObjectException("Unexpected response reading rel humidity setpoint from the PLC")
        }
        return finsCmdRelativeHumiditySetpoint.getResponseWord()
    }
}