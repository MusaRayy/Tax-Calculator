package com.musarayy.taxcalculator.views.particlesView.renderer

import com.musarayy.taxcalculator.views.particlesView.KeepAsApi
import com.musarayy.taxcalculator.views.particlesView.contract.LowLevelRenderer
import com.musarayy.taxcalculator.views.particlesView.contract.SceneRenderer
import com.musarayy.taxcalculator.views.particlesView.model.Scene
import com.musarayy.taxcalculator.views.particlesView.util.DistanceResolver
import com.musarayy.taxcalculator.views.particlesView.util.LineColorResolver
import com.musarayy.taxcalculator.views.particlesView.util.ParticleColorResolver

@KeepAsApi
class DefaultSceneRenderer(private val renderer: LowLevelRenderer) : SceneRenderer {
    override fun drawScene(scene: Scene) {
        if (scene.density > 0) {
            val particleColor = ParticleColorResolver.resolveParticleColorWithSceneAlpha(
                scene.particleColor,
                scene.alpha
            )
            val radiuses = scene.radiuses
            val particlesCount: Int = scene.density
            for (i in 0 until particlesCount) {
                val x1 = scene.getParticleX(i)
                val y1 = scene.getParticleY(i)

                // Draw connection lines for eligible particles
                for (j in i + 1 until particlesCount) {
                    val x2 = scene.getParticleX(j)
                    val y2 = scene.getParticleY(j)
                    val distance = DistanceResolver.distance(x1, y1, x2, y2)
                    if (distance < scene.lineLength) {
                        val lineColor = LineColorResolver.resolveLineColorWithAlpha(
                            scene.alpha,
                            scene.lineColor,
                            scene.lineLength,
                            distance
                        )
                        renderer.drawLine(
                            x1,
                            y1,
                            x2,
                            y2,
                            scene.lineThickness,
                            lineColor
                        )
                    }
                }
                val radius = radiuses[i]
                renderer.fillCircle(x1, y1, radius, particleColor)
            }
        }
    }
}