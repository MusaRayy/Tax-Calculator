package com.musarayy.taxcalculator.utils

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OSAHttpLogger : HttpLoggingInterceptor.Logger, KoinComponent {

    private val logger: OSALogger by inject()

    override fun log(message: String) {

        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val prettyPrintJson = GsonBuilder().setPrettyPrinting().create()
                    .toJson(JsonParser.parseString(message))
                logger.json(prettyPrintJson)
            } catch (m: JsonSyntaxException) {
                logger.e(message)
            }
        } else {
            logger.d(message)
            return
        }
    }
}