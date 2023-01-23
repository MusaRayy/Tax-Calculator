package com.musarayy.taxcalculator.views.particlesView.engine

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.VisibleForTesting
import com.musarayy.taxcalculator.views.particlesView.contract.SceneConfiguration
import com.musarayy.taxcalculator.views.particlesView.model.Scene
import java.util.*
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

internal class ParticleGenerator @VisibleForTesting constructor(private val random: Random) {
    /**
     * Path calculation padding.
     *
     * @see .applyFreshParticleOffScreen
     */
    private val pcc = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 18f, Resources.getSystem().displayMetrics
    )

    constructor() : this(Random())

    /**
     * Set new point coordinates somewhere on screen and apply new direction
     *
     * @param position the point position to apply new values to
     */
    fun applyFreshParticleOnScreen(
        scene: Scene,
        position: Int
    ) {
        val w = scene.width
        val h = scene.height
        check(!(w == 0 || h == 0)) { "Cannot generate particles if scene width or height is 0" }
        val direction = Math.toRadians(random.nextInt(360).toDouble())
        val dCos = cos(direction).toFloat()
        val dSin = sin(direction).toFloat()
        val x = random.nextInt(w).toFloat()
        val y = random.nextInt(h).toFloat()
        val speedFactor = newRandomIndividualParticleSpeedFactor()
        val radius = newRandomIndividualParticleRadius(scene)
        scene.setParticleData(
            position,
            x,
            y,
            dCos,
            dSin,
            radius,
            speedFactor
        )
    }

    /**
     * Set new particle coordinates somewhere off screen and apply new direction towards the screen
     *
     * @param position the particle position to apply new values to
     */
    fun applyFreshParticleOffScreen(
        scene: Scene,
        position: Int
    ) {
        val w = scene.width
        val h = scene.height
        check(!(w == 0 || h == 0)) { "Cannot generate particles if scene width or height is 0" }
        var x = random.nextInt(w).toFloat()
        var y = random.nextInt(h).toFloat()

        // The offset to make when creating point of out bounds
        val offset = (scene.particleRadiusMin + scene.lineLength).toInt().toShort()

        // Point angle range
        val startAngle: Float
        var endAngle: Float
        when (random.nextInt(4)) {
            0 -> {
                // offset to left
                x = -offset.toFloat()
                startAngle = angleDeg(pcc, pcc, x, y)
                endAngle = angleDeg(pcc, h - pcc, x, y)
            }
            1 -> {
                // offset to top
                y = -offset.toFloat()
                startAngle = angleDeg(w - pcc, pcc, x, y)
                endAngle = angleDeg(pcc, pcc, x, y)
            }
            2 -> {
                // offset to right
                x = (w + offset).toFloat()
                startAngle = angleDeg(w - pcc, h - pcc, x, y)
                endAngle = angleDeg(w - pcc, pcc, x, y)
            }
            3 -> {
                // offset to bottom
                y = (h + offset).toFloat()
                startAngle = angleDeg(pcc, h - pcc, x, y)
                endAngle = angleDeg(w - pcc, h - pcc, x, y)
            }
            else -> throw IllegalArgumentException("Supplied value out of range")
        }
        if (endAngle < startAngle) {
            endAngle += 360f
        }

        // Get random angle from angle range
        val randomAngleInRange = startAngle + random
            .nextInt(abs(endAngle - startAngle).toInt())
        val direction = Math.toRadians(randomAngleInRange.toDouble())
        val dCos = cos(direction).toFloat()
        val dSin = sin(direction).toFloat()
        val speedFactor = newRandomIndividualParticleSpeedFactor()
        val radius = newRandomIndividualParticleRadius(scene)
        scene.setParticleData(
            position,
            x,
            y,
            dCos,
            dSin,
            radius,
            speedFactor
        )
    }

    /**
     * Generates new speed factor for individual particle.
     * The value is in [0.5:1.5] range
     *
     * @return new speed factor for individual particle.
     */
    private fun newRandomIndividualParticleSpeedFactor(): Float {
        return 1f + 0.1f * (random.nextInt(11) - 5)
    }

    /**
     * Generates new individual particle radius based on min and max radius setting.
     *
     * @return new particle radius
     */
    private fun newRandomIndividualParticleRadius(scene: SceneConfiguration): Float {
        return if (scene.particleRadiusMin == scene.particleRadiusMax) scene.particleRadiusMin else scene.particleRadiusMin + random.nextInt(
            ((scene.particleRadiusMax - scene.particleRadiusMin) * 100f).toInt()
        ) / 100f
    }

    companion object {
        /**
         * Returns angle in degrees between two points
         *
         * @param ax x of the point 1
         * @param ay y of the point 1
         * @param bx x of the point 2
         * @param by y of the point 2
         * @return angle in degrees between two points
         */
        private fun angleDeg(
            ax: Float, ay: Float,
            bx: Float, by: Float
        ): Float {
            val angleRad = atan2(ay - by.toDouble(), ax - bx.toDouble())
            var angle = Math.toDegrees(angleRad)
            if (angleRad < 0) {
                angle += 360.0
            }
            return angle.toFloat()
        }
    }
}