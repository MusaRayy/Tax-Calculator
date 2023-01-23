package com.musarayy.taxcalculator.views.particlesView.contract

import androidx.annotation.Keep

/**
 * Used for scheduling redraw
 */
@Keep
interface SceneScheduler {
    fun scheduleNextFrame(delay: Long)
    fun unscheduleNextFrame()
    fun requestRender()
}