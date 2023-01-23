@file:Suppress("DEPRECATION")

package com.musarayy.taxcalculator.network

import android.content.Context
import android.net.ConnectivityManager
import com.musarayy.taxcalculator.TaxApplication

fun checkInternetConnection(): Boolean {

    return (TaxApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        .activeNetworkInfo
        ?.isConnected
        ?: false
}