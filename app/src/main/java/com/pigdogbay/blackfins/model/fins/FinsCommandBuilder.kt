package com.pigdogbay.blackfins.model.fins

/**
 * Created by mark on 21/02/18.
 * Helper class to create FinsCommand objects
 */
class FinsCommandBuilder {

    companion object {
        var serviceId = 0
    }
    private var memoryAddress : String? = null
    private var memoryArea : FinsMemoryArea? = null
    private var writeData : List<Int>? = null

    fun build() : FinsCommand {
        val cmd = FinsCommand()
        cmd.serviceId = serviceId++
        if (memoryAddress!=null){
            cmd.memoryAddress = this.memoryAddress!!
        }
        if (memoryArea!=null){
            cmd.memoryArea = memoryArea!!
        }
        cmd.mainRequestCode = 0x01
        cmd.subRequestCode = 0x01
        cmd.dataReturnExpected = true
        cmd.numElements = 1
        if (writeData!=null){
            cmd.subRequestCode = 0x02
            cmd.data = writeData
            cmd.dataReturnExpected = false
        }
        return cmd
    }

    fun write(data : List<Int>) : FinsCommandBuilder {
        this.writeData = data
        return this
    }
    fun atMemoryAddress(address : String) : FinsCommandBuilder {
        this.memoryAddress = address
        return this
    }
    fun inMemoryArea(area : FinsMemoryArea) : FinsCommandBuilder {
        this.memoryArea = area
        return this
    }
}