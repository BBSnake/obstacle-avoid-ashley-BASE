package com.obstacleavoid.screen.game

import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Logger
import com.obstacleavoid.ObstacleAvoidGame
import com.obstacleavoid.screen.menu.MenuScreen

class GameScreen(private val game: ObstacleAvoidGame) : Screen {
    private val assetManager: AssetManager
    private lateinit var controller: GameController
    private lateinit var renderer: GameRenderer

    init {
        assetManager = game.assetManager
    }

    override fun show() {
        controller = GameController(game)
        renderer = GameRenderer(game.batch, assetManager, controller!!)
    }

    override fun render(delta: Float) {
        controller.update(delta)
        renderer.render(delta)
        if (controller.isGameOver)
            game.screen = MenuScreen(game)
    }

    override fun resize(width: Int, height: Int) {
        renderer.resize(width, height)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        renderer.dispose()
    }

    companion object {
        private val log = Logger(GameScreen::class.java.name, Logger.DEBUG)
    }


}
