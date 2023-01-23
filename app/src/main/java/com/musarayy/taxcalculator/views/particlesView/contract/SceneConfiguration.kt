package com.musarayy.taxcalculator.views.particlesView.contract

import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.Keep

/**
 * Particles scene configuration, makes sure all configuration fields are available.
 */
@Keep
interface SceneConfiguration {
    /**
     * Returns the number of particles in the scene
     *
     * @return the number of particles in the scene
     */
    /**
     * Set number of particles to draw per scene.
     *
     * @param density the number of particles to draw per scene
     * @throws IllegalArgumentException if density is negative
     */
    var density: Int
    /**
     * Returns a delay per frame in milliseconds.
     *
     * @return delay between frames
     */
    /**
     * Set a delay per frame in milliseconds.
     *
     * @param delay delay between frames
     * @throws IllegalArgumentException if delay is a negative number
     */
    var frameDelay: Int
    /**
     * Returns the connection line color
     *
     * @return the connection line color
     */
    /**
     * Set the line color. Note that the color alpha is ignored and will be calculated depending on
     * distance between particles.
     *
     * @param lineColor line color to use
     */
    @get:ColorInt
    var lineColor: Int
    /**
     * Returns the maximum distance when the connection line is still drawn between particles
     *
     * @return maximum distance for connection lines
     */
    /**
     * Set the maximum distance when the connection line is still drawn between particles.
     *
     * @param lineLength maximum distance for connection lines
     */
    var lineLength: Float
    /**
     * Returns the connection like thickness
     *
     * @return the connection line thickness
     */
    /**
     * Set a line thickness
     *
     * @param lineThickness line thickness
     */
    var lineThickness: Float
    /**
     * Returns the particle color
     *
     * @return the particle color
     */
    /**
     * Set the particle color
     *
     * @param color particle color to use
     */
    @get:ColorInt
    var particleColor: Int

    /**
     * Set particle radius range
     *
     * @param minRadius smallest particle radius
     * @param maxRadius largest particle radius
     */
    fun setParticleRadiusRange(
        @FloatRange(from = 0.5) minRadius: Float,
        @FloatRange(from = 0.5) maxRadius: Float
    )

    /**
     * Largest particle radius
     *
     * @return largest particle radius
     */
    val particleRadiusMax: Float

    /**
     * Returns smallest particle radius
     *
     * @return smallest particle radius
     */
    val particleRadiusMin: Float
    /**
     * Returns the speed factor.
     *
     * @return the speed factor
     */
    /**
     * Sets speed factor. Use this to control speed.
     *
     * @param speedFactor speed factor
     */
    var speedFactor: Float
}