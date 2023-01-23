package com.musarayy.taxcalculator.utils

import com.musarayy.taxcalculator.TaxApplication


class DataCache {

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "TaxCalculator"
    private val DEVICE_TOKEN = "DEVICE_TOKEN"

    fun setDeviceTokenId(deviceToken: String) {

        TaxApplication.instance
            .getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            .edit()
            .putString(DEVICE_TOKEN, deviceToken)
            .apply()
    }

    fun getDeviceToken(): String {
        return TaxApplication.instance
            .getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            .getString(DEVICE_TOKEN, "").toString()
    }
}