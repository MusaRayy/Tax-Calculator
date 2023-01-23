package com.musarayy.taxcalculator.network

import com.google.gson.Gson
import com.musarayy.taxcalculator.utils.OSAHttpLogger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class RetroClient(gSon: Gson) {

    private val configurationService: ConfigurationService

    init {

        val headerInterceptor = HeaderInterceptor()

        val httpLoggingInterceptor = HttpLoggingInterceptor(OSAHttpLogger()).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .callTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(headerInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("BASE_URL")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gSon))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        configurationService = retrofit.create(ConfigurationService::class.java)
    }

//    fun sendEmail(mailRequest: EmailRequestModel): Call<EmailResponseModel> {
//        return configurationService.sendEmail(mailRequest)
//    }
}