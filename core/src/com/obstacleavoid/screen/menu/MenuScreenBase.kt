package com.obstacleavoid.screen.menu

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.obstacleavoid.ObstacleAvoidGame
import com.obstacleavoid.config.HUD_HEIGHT
import com.obstacleavoid.config.HUD_WIDTH
import com.obstacleavoid.util.clearScreen

abstract class MenuScreenBase(protected val game: ObstacleAvoidGame) : ScreenAdapter() {
    protected val assetManager: AssetManager

    private lateinit var viewport: Viewport
    // automatically sets projection matrix
    private lateinit var stage: Stage

    init {
        assetManager = game.assetManager
    }

    override fun show() {
        viewport = FitViewport(HUD_WIDTH, HUD_HEIGHT)
        stage = Stage(viewport, game.batch)
        Gdx.input.inputProcessor = stage

        stage.addActor(createUi())
    }

    protected abstract fun createUi(): Actor

    override fun render(delta: Float) {
        clearScreen()

        stage.act()

        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        stage.dispose()
    }
}
