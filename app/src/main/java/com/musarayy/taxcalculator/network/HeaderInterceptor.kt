package com.musarayy.taxcalculator.network

import okhttp3.Interceptor
import org.koin.core.component.KoinComponent
import java.io.IOException

class HeaderInterceptor : Interceptor, KoinComponent {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("Authorization", "A8p5e3y@aanmoaKkahrsa")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build()
        )
    }
}