package com.musarayy.taxcalculator.views.particlesView.contract

import androidx.annotation.Keep

@Keep
interface SceneController {
    /**
     * Use this if you want to manually set to next frame, while animations are stopped.
     */
    fun nextFrame()

    /**
     * Resets and makes new random frame. This is useful for re-generating new fancy static
     * backgrounds when not using animations.
     */
    fun makeFreshFrame()

    /**
     * Resets and makes new random frame where all particles are out of screen bounds and will be
     * moving into the screen once animation starts.
     */
    fun makeFreshFrameWithParticlesOffscreen()
}