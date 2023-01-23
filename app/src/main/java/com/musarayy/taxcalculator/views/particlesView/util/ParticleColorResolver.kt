package com.musarayy.taxcalculator.views.particlesView.util

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.IntRange

object ParticleColorResolver {
    @ColorInt
    fun resolveParticleColorWithSceneAlpha(
        @ColorInt particleColor: Int,
        @IntRange(from = 0, to = 255) sceneAlpha: Int
    ): Int {
        val alpha = Color.alpha(particleColor) * sceneAlpha / 255
        return particleColor and 0x00FFFFFF or (alpha shl 24)
    }
}