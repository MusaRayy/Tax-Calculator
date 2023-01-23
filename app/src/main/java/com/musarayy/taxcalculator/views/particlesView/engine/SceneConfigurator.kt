package com.musarayy.taxcalculator.views.particlesView.engine

import android.content.res.Resources
import android.util.AttributeSet
import androidx.annotation.Keep
import com.musarayy.taxcalculator.R
import com.musarayy.taxcalculator.views.particlesView.Defaults
import com.musarayy.taxcalculator.views.particlesView.contract.SceneConfiguration

@Keep
class SceneConfigurator {
    fun configureSceneFromAttributes(
        scene: SceneConfiguration,
        r: Resources,
        attrs: AttributeSet
    ) {
        val a = r.obtainAttributes(attrs, R.styleable.ParticlesView)
        try {
            val count = a.indexCount
            var particleRadiusMax = Defaults.PARTICLE_RADIUS_MAX
            var particleRadiusMin = Defaults.PARTICLE_RADIUS_MIN
            for (i in 0 until count) {
                when (val attr = a.getIndex(i)) {
                    R.styleable.ParticlesView_density -> {
                        scene.density = a.getInteger(attr, Defaults.DENSITY)
                    }
                    R.styleable.ParticlesView_frameDelayMillis -> {
                        scene.frameDelay = a.getInteger(attr, Defaults.FRAME_DELAY)
                    }
                    R.styleable.ParticlesView_lineColor -> {
                        scene.lineColor = a.getColor(attr, Defaults.LINE_COLOR)
                    }
                    R.styleable.ParticlesView_lineLength -> {
                        scene.lineLength = a.getDimension(attr, Defaults.LINE_LENGTH)
                    }
                    R.styleable.ParticlesView_lineThickness -> {
                        scene.lineThickness = a.getDimension(attr, Defaults.LINE_THICKNESS)
                    }
                    R.styleable.ParticlesView_particleColor -> {
                        scene.particleColor = a.getColor(attr, Defaults.PARTICLE_COLOR)
                    }
                    R.styleable.ParticlesView_particleRadiusMax -> {
                        particleRadiusMax = a.getDimension(attr, Defaults.PARTICLE_RADIUS_MAX)
                    }
                    R.styleable.ParticlesView_particleRadiusMin -> {
                        particleRadiusMin = a.getDimension(attr, Defaults.PARTICLE_RADIUS_MIN)
                    }
                    R.styleable.ParticlesView_speedFactor -> {
                        scene.speedFactor = a.getFloat(attr, Defaults.SPEED_FACTOR)
                    }
                }
            }
            scene.setParticleRadiusRange(particleRadiusMin, particleRadiusMax)
        } finally {
            a.recycle()
        }
    }
}