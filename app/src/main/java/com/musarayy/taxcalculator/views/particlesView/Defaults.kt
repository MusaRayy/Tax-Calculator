package com.musarayy.taxcalculator.views.particlesView

import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import androidx.annotation.ColorInt

/**
 * Default values are here.
 */
class Defaults private constructor() {
    companion object {
        const val DENSITY = 60
        const val FRAME_DELAY = 10

        @ColorInt
        const val LINE_COLOR = Color.WHITE
        @JvmField
        val LINE_LENGTH = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 86f, Resources.getSystem().displayMetrics
        )
        @JvmField
        val LINE_THICKNESS = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 1f, Resources.getSystem().displayMetrics
        )

        @ColorInt
        const val PARTICLE_COLOR = Color.WHITE
        @JvmField
        val PARTICLE_RADIUS_MAX = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 3f, Resources.getSystem().displayMetrics
        )
        @JvmField
        val PARTICLE_RADIUS_MIN = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 1f, Resources.getSystem().displayMetrics
        )
        const val SPEED_FACTOR = 1f
    }

    init {
        throw UnsupportedOperationException()
    }
}