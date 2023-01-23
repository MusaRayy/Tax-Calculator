package com.musarayy.taxcalculator.views.particlesView.contract

import com.musarayy.taxcalculator.views.particlesView.KeepAsApi
import com.musarayy.taxcalculator.views.particlesView.model.Scene

@KeepAsApi
interface SceneRenderer {
    fun drawScene(scene: Scene)
}