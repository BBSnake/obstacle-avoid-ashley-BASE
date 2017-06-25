package com.obstacleavoid.desktop

import com.badlogic.gdx.tools.texturepacker.TexturePacker

object AssetPacker {

    private val DRAW_DEBUG_OUTLINE = false

    private val RAW_ASSETS_PATH = "desktop/assets-raw"
    private val ASSETS_PATH = "android/assets"

    @JvmStatic fun main(args: Array<String>) {
        val settings = TexturePacker.Settings()
        settings.debug = DRAW_DEBUG_OUTLINE

        TexturePacker.process(settings,
                RAW_ASSETS_PATH + "/gameplay",
                ASSETS_PATH + "/gameplay",
                "gameplay")

        TexturePacker.process(settings,
                RAW_ASSETS_PATH + "/skin",
                ASSETS_PATH + "/ui",
                "uiskin")
    }
}
