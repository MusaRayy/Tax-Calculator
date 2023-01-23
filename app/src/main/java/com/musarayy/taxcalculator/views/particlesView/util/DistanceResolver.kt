package com.musarayy.taxcalculator.views.particlesView.util

import kotlin.math.sqrt

object DistanceResolver {
    /**
     * Calculates the distance between two points
     *
     * @return distance between two points
     */
    fun distance(
        ax: Float, ay: Float,
        bx: Float, by: Float
    ): Float {
        return sqrt(
            (ax - bx) * (ax - bx) +
                    (ay - by) * (ay - by)
                .toDouble()
        ).toFloat()
    }
}