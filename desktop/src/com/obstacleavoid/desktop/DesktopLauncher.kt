package com.obstacleavoid.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.obstacleavoid.ObstacleAvoidGame
import com.obstacleavoid.config.GameConfig

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()

        config.apply {
            width = 360
            height = 600
            forceExit = false
        }

        LwjglApplication(ObstacleAvoidGame(), config)
    }
}
