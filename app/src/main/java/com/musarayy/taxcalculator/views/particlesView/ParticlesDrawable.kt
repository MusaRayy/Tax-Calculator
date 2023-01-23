package com.musarayy.taxcalculator.views.particlesView

import android.content.res.Resources
import android.content.res.Resources.Theme
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.util.AttributeSet
import androidx.annotation.FloatRange
import androidx.annotation.Keep
import com.musarayy.taxcalculator.views.particlesView.contract.SceneConfiguration
import com.musarayy.taxcalculator.views.particlesView.contract.SceneController
import com.musarayy.taxcalculator.views.particlesView.contract.SceneRenderer
import com.musarayy.taxcalculator.views.particlesView.contract.SceneScheduler
import com.musarayy.taxcalculator.views.particlesView.engine.Engine
import com.musarayy.taxcalculator.views.particlesView.engine.SceneConfigurator
import com.musarayy.taxcalculator.views.particlesView.model.Scene
import com.musarayy.taxcalculator.views.particlesView.renderer.CanvasSceneRenderer
import com.musarayy.taxcalculator.views.particlesView.renderer.DefaultSceneRenderer
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

/**
 * The Particles Drawable
 */
@Keep
class ParticlesDrawable : Drawable(), Animatable, SceneConfiguration, SceneController,
    SceneScheduler {
    private val canvasRenderer = CanvasSceneRenderer()
    private val scene = Scene()
    private val sceneConfigurator = SceneConfigurator()
    private val renderer: SceneRenderer = DefaultSceneRenderer(canvasRenderer)
    private val engine = Engine(scene, this, renderer)

    @Throws(XmlPullParserException::class, IOException::class)
    override fun inflate(
        r: Resources,
        parser: XmlPullParser,
        attrs: AttributeSet,
        theme: Theme?
    ) {
        super.inflate(r, parser, attrs, theme)
        sceneConfigurator.configureSceneFromAttributes(scene, r, attrs)
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        engine.setDimensions(right - left, bottom - top)
    }

    override fun draw(canvas: Canvas) {
        canvasRenderer.setCanvas(canvas)
        engine.draw()
        canvasRenderer.setCanvas(null)
        engine.run()
    }

    override fun scheduleNextFrame(delay: Long) {
        if (delay == 0L) {
            requestRender()
        } else {
            scheduleSelf(invalidateSelfRunnable, SystemClock.uptimeMillis() + delay)
        }
    }

    override fun unscheduleNextFrame() {
        unscheduleSelf(invalidateSelfRunnable)
    }

    override fun requestRender() {
        invalidateSelf()
    }

    override fun setAlpha(alpha: Int) {
        engine.alpha = alpha
    }

    override fun getAlpha(): Int {
        return engine.alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        canvasRenderer.setColorFilter(colorFilter)
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun start() {
        engine.start()
    }

    override fun stop() {
        engine.stop()
    }

    override fun isRunning(): Boolean {
        return engine.isRunning
    }

    /**
     * {@inheritDoc}
     */
    override fun nextFrame() {
        engine.nextFrame()
    }

    /**
     * {@inheritDoc}
     */
    override fun makeFreshFrame() {
        engine.makeFreshFrame()
    }

    /**
     * {@inheritDoc}
     */
    override fun makeFreshFrameWithParticlesOffscreen() {
        engine.makeFreshFrameWithParticlesOffscreen()
    }
    /**
     * {@inheritDoc}
     */
    /**
     * {@inheritDoc}
     */
    override var frameDelay: Int
        get() = scene.frameDelay
        set(delay) {
            scene.frameDelay = delay
        }
    /**
     * {@inheritDoc}
     */
    /**
     * {@inheritDoc}
     */
    override var speedFactor: Float
        get() = scene.speedFactor
        set(speedFactor) {
            scene.speedFactor = speedFactor
        }

    /**
     * {@inheritDoc}
     */
    override fun setParticleRadiusRange(
        @FloatRange(from = 0.5) minRadius: Float,
        @FloatRange(from = 0.5) maxRadius: Float
    ) {
        scene.setParticleRadiusRange(minRadius, maxRadius)
    }

    /**
     * {@inheritDoc}
     */
    override val particleRadiusMin: Float
        get() = scene.particleRadiusMin

    /**
     * {@inheritDoc}
     */
    override val particleRadiusMax: Float
        get() = scene.particleRadiusMax
    /**
     * {@inheritDoc}
     */
    /**
     * {@inheritDoc}
     */
    override var lineThickness: Float
        get() = scene.lineThickness
        set(lineThickness) {
            scene.lineThickness = lineThickness
        }
    /**
     * {@inheritDoc}
     */
    /**
     * {@inheritDoc}
     */
    override var lineLength: Float
        get() = scene.lineLength
        set(lineLength) {
            scene.lineLength = lineLength
        }
    /**
     * {@inheritDoc}
     */
    /**
     * {@inheritDoc}
     */
    override var density: Int
        get() = scene.density
        set(newNum) {
            scene.density = newNum
        }
    /**
     * {@inheritDoc}
     */
    /**
     * {@inheritDoc}
     */
    override var particleColor: Int
        get() = scene.particleColor
        set(color) {
            scene.particleColor = color
        }
    /**
     * {@inheritDoc}
     */
    /**
     * {@inheritDoc}
     */
    override var lineColor: Int
        get() = scene.lineColor
        set(lineColor) {
            scene.lineColor = lineColor
        }
    private val invalidateSelfRunnable = Runnable { invalidateSelf() }
}