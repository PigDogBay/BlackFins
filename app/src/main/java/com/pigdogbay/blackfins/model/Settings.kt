package com.pigdogbay.blackfins.model

import com.pigdogbay.blackfins.R
import com.pigdogbay.lib.utils.PreferencesHelper

/**
 * Created by mark on 23/02/18.
 * Handles Preference Settings
 */
class Settings(val preferencesHelper: PreferencesHelper) {

    var ipAddress : String
        get() = preferencesHelper.getString(R.string.key_pref_plc_ip_address, "192.168.0.200")
        set(value){preferencesHelper.setString(R.string.key_pref_plc_ip_address,value)}
    var port : Int
        get() = preferencesHelper.getInt(R.string.key_pref_plc_port, 9600)
        set(value){preferencesHelper.setInt(R.string.key_pref_plc_port,value)}
    var updateFrequency : Int
        get() = preferencesHelper.getInt(R.string.key_pref_logging_update_frequency, 10)
        set(value){preferencesHelper.setInt(R.string.key_pref_logging_update_frequency,value)}
}