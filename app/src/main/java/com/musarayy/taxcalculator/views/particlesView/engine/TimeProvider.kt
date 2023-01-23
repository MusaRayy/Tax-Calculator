package com.musarayy.taxcalculator.views.particlesView.engine

import android.os.SystemClock

internal class TimeProvider {
    fun uptimeMillis(): Long {
        return SystemClock.uptimeMillis()
    }
}