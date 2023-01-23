package com.musarayy.taxcalculator.views.particlesView.renderer

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import androidx.annotation.ColorInt
import com.musarayy.taxcalculator.views.particlesView.KeepAsApi
import com.musarayy.taxcalculator.views.particlesView.contract.LowLevelRenderer

@KeepAsApi
class CanvasSceneRenderer : LowLevelRenderer {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var canvas: Canvas? = null
    fun setCanvas(canvas: Canvas?) {
        this.canvas = canvas
    }

    fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun drawLine(
        startX: Float,
        startY: Float,
        stopX: Float,
        stopY: Float,
        strokeWidth: Float,
        @ColorInt color: Int
    ) {
        checkNotNull(canvas) { "Called in wrong state" }
        paint.strokeWidth = strokeWidth
        paint.color = color
        canvas!!.drawLine(startX, startY, stopX, stopY, paint)
    }

    override fun fillCircle(
        cx: Float,
        cy: Float,
        radius: Float,
        @ColorInt color: Int
    ) {
        checkNotNull(canvas) { "Called in wrong state" }
        paint.color = color
        canvas!!.drawCircle(cx, cy, radius, paint)
    }
}