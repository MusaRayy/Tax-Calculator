package com.musarayy.taxcalculator.views.particlesView.engine

import androidx.annotation.VisibleForTesting
import com.musarayy.taxcalculator.views.particlesView.model.Scene

internal class FrameAdvancer(private val particleGenerator: ParticleGenerator) {
    fun advanceToNextFrame(
        scene: Scene,
        step: Float
    ) {
        val particlesCount: Int = scene.density
        for (i in 0 until particlesCount) {
            var x = scene.getParticleX(i)
            var y = scene.getParticleY(i)
            val speedFactor = scene.getParticleSpeedFactor(i)
            val dCos = scene.getParticleDirectionCos(i)
            val dSin = scene.getParticleDirectionSin(i)
            x += step * scene.speedFactor * speedFactor * dCos
            y += step * scene.speedFactor * speedFactor * dSin
            if (particleOutOfBounds(scene, x, y)) {
                particleGenerator.applyFreshParticleOffScreen(scene, i)
            } else {
                scene.setParticleX(i, x)
                scene.setParticleY(i, y)
            }
        }
    }

    /**
     * Used for checking if the particle is off-screen and farther than line length and it's
     * radius.
     *
     * @param x the particle x
     * @param y the particle y
     * @return true if the particle is off-screen and guaranteed not to be used to draw a line to
     * the closest particle on screen.
     */
    @VisibleForTesting
    fun particleOutOfBounds(
        scene: Scene,
        x: Float,
        y: Float
    ): Boolean {
        val offset: Float = scene.particleRadiusMin + scene.lineLength
        return x + offset < 0 || x - offset > scene.width || y + offset < 0 || y - offset > scene.height
    }
}