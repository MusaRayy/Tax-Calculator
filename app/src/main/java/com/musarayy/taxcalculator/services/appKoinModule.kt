package com.musarayy.taxcalculator.services

import com.google.gson.Gson
import com.musarayy.taxcalculator.network.RetroClient
import com.musarayy.taxcalculator.utils.DataCache
import com.musarayy.taxcalculator.utils.OSALogger
import okhttp3.OkHttpClient
import org.koin.dsl.module

val koinModule = module {
    single { OSALogger() }
    single { DataCache() }
    single { OkHttpClient() }
    single { Gson() }
    single { RetroClient(get()) }
}