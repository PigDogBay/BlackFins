package com.pigdogbay.blackfins.model

/**
 * Created by mark on 02/03/18.
 * Decorator for ILiveDataSource to retry if read fails
 */
class RetryLiveDataSource(val innerLiveDataSource : ILiveDataSource, val maxAttempts : Int) : ILiveDataSource{

    override fun getLiveData(): LiveData {
        var count = 0
        var exception : Exception? = null
        while (count < maxAttempts) {
            try {
                return innerLiveDataSource.getLiveData()
            } catch (e: Exception) {
                count++
                exception = e
            }
        }
        throw exception!!
    }
}