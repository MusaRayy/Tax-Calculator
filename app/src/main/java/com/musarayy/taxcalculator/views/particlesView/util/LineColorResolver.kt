package com.musarayy.taxcalculator.views.particlesView.util

import androidx.annotation.ColorInt
import androidx.annotation.IntRange

object LineColorResolver {
    private const val OPAQUE = 255

    /**
     * Resolves line alpha based on distance comparing to max distance.
     * Where alpha is close to 0 for maxDistance, and close to 1 to 0 distance.
     *
     * @param distance    line length
     * @param maxDistance max line length
     * @return line alpha
     */
    @IntRange(from = 0, to = OPAQUE.toLong())
    private fun resolveLineAlpha(
        @IntRange(from = 0, to = OPAQUE.toLong()) sceneAlpha: Int,
        maxDistance: Float,
        distance: Float
    ): Int {
        val alphaPercent = 1f - distance / maxDistance
        val alpha = (OPAQUE.toFloat() * alphaPercent).toInt()
        return alpha * sceneAlpha / OPAQUE
    }

    @ColorInt
    fun resolveLineColorWithAlpha(
        @IntRange(from = 0, to = OPAQUE.toLong()) sceneAlpha: Int,
        @ColorInt lineColor: Int,
        maxDistance: Float,
        distance: Float
    ): Int {
        val alpha = resolveLineAlpha(sceneAlpha, maxDistance, distance)
        return lineColor and 0x00FFFFFF or (alpha shl 24)
    }
}