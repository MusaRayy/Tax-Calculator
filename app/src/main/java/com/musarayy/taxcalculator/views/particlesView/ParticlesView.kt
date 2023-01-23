package com.musarayy.taxcalculator.views.particlesView

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Animatable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.*
import com.musarayy.taxcalculator.views.particlesView.contract.SceneConfiguration
import com.musarayy.taxcalculator.views.particlesView.contract.SceneController
import com.musarayy.taxcalculator.views.particlesView.contract.SceneRenderer
import com.musarayy.taxcalculator.views.particlesView.contract.SceneScheduler
import com.musarayy.taxcalculator.views.particlesView.engine.Engine
import com.musarayy.taxcalculator.views.particlesView.engine.SceneConfigurator
import com.musarayy.taxcalculator.views.particlesView.model.Scene
import com.musarayy.taxcalculator.views.particlesView.renderer.CanvasSceneRenderer
import com.musarayy.taxcalculator.views.particlesView.renderer.DefaultSceneRenderer

/**
 * The Particles View.
 *
 *
 * Automatically starts on [.onAttachedToWindow] or when visibility is set to
 * [.VISIBLE]. Automatically stops on [.onDetachedFromWindow] or when visbility set
 * to [.INVISIBLE] or [.GONE].
 *
 *
 * You may also use [.start] and [.stop] on your behalf. Note when you call [ ][.stop] explicitly, the animation will not automatically restart when you trigger visibility or
 * when this View gets attached to window.
 *
 *
 * The View does not use Lifecycle api and thus cannot tell whether your hosting Activity or
 * Fragment is started or stopped. It can only tell when it's being destroyed
 * ([.onDetachedFromWindow] will be called) so this is where it stops animations
 * automatically. Thus, It is recommended to call [.stop] when the hosting component gets
 * onStop() call and call [.start] when the hosting component gets onStart() call.
 */
@Keep
class ParticlesView : View, Animatable, SceneConfiguration, SceneController, SceneScheduler {
    private val canvasSceneRenderer = CanvasSceneRenderer()
    private val scene = Scene()
    private val sceneConfigurator = SceneConfigurator()
    private val renderer: SceneRenderer = DefaultSceneRenderer(canvasSceneRenderer)
    private val engine = Engine(scene, this, renderer)

    /**
     * Whether explicitly stopped by user. This means it will not start automatically on visibility
     * change or when attached to window.
     */
    private var mExplicitlyStopped = false
    private var mAttachedToWindow = false
    private var mEmulateOnAttachToWindow = false

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int,
        @StyleRes defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        setLayerType(LAYER_TYPE_HARDWARE, canvasSceneRenderer.paint)
        if (attrs != null) {
            sceneConfigurator.configureSceneFromAttributes(scene, context.resources, attrs)
        }
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
            scene.lineThickness = (lineThickness)
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
            scene.lineLength = (lineLength)
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
            scene.density = (newNum)
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
            scene.particleColor = (color)
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
            scene.lineColor = (lineColor)
        }

    override fun requestRender() {
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        engine.setDimensions(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvasSceneRenderer.setCanvas(canvas)
        engine.draw()
        canvasSceneRenderer.setCanvas(null)
        engine.run()
    }

    override fun scheduleNextFrame(delay: Long) {
        if (delay == 0L) {
            requestRender()
        } else {
            postInvalidateDelayed(delay)
        }
    }

    override fun unscheduleNextFrame() {}
    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility != VISIBLE) {
            stopInternal()
        } else {
            startInternal()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mAttachedToWindow = true
        startInternal()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mAttachedToWindow = false
        stopInternal()
    }

    /**
     * Start animating. This will clear the explicit control flag if set by [.stop].
     * Note that if this View's visibility is not [.VISIBLE] or it's not attached to window,
     * this will not start animating until the state changes to meet the requirements above.
     */
    override fun start() {
        mExplicitlyStopped = false
        startInternal()
    }

    /**
     * Explicilty stop animating. This will stop animating and no animations will start
     * automatically until you call [.start].
     */
    override fun stop() {
        mExplicitlyStopped = true
        stopInternal()
    }

    override fun isRunning(): Boolean {
        return engine.isRunning
    }

    private fun startInternal() {
        if (!mExplicitlyStopped && isVisibleWithAllParents(this) && isAttachedToWindowCompat) {
            engine.start()
        }
    }

    private fun stopInternal() {
        engine.stop()
    }

    private val isAttachedToWindowCompat: Boolean
        get() = if (mEmulateOnAttachToWindow) {
            mAttachedToWindow
        } else isAttachedToWindow

    private fun isVisibleWithAllParents(view: View): Boolean {
        if (view.visibility != VISIBLE) {
            return false
        }
        val parent = view.parent
        return if (parent is View) {
            isVisibleWithAllParents(parent as View)
        } else true
    }
}