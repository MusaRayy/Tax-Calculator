package com.musarayy.taxcalculator.utils

import com.musarayy.taxcalculator.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

open class OSALogger : Interceptor {

    private val TAG = "OSA_LOGGER"

    init {

        val formatStrategy = PrettyFormatStrategy
            .newBuilder()
            .methodCount(0)
            .showThreadInfo(false)
            .methodOffset(2)
            .tag(TAG)
            .build()

        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {

            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }


    // Not only String & Collections are supported (only available for debug logs)
    fun d(message: Any) = Logger.d(message)

    fun e(message: String) = Logger.e(message)

    fun w(message: String) = Logger.w(message)

    fun v(message: String) = Logger.v(message)

    fun i(message: String) = Logger.i(message)

    fun json(message: String) = Logger.json(message)

    fun xml(message: String) = Logger.xml(message)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        d(response)
        return response
    }

}