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
}