package com.obstacleavoid.screen.loading

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.obstacleavoid.ObstacleAvoidGame
import com.obstacleavoid.assets.FONT
import com.obstacleavoid.assets.GAME_PLAY
import com.obstacleavoid.assets.HIT_SOUND
import com.obstacleavoid.assets.UI_SKIN
import com.obstacleavoid.config.HUD_HEIGHT
import com.obstacleavoid.config.HUD_WIDTH
import com.obstacleavoid.screen.menu.MenuScreen
import com.obstacleavoid.util.clearScreen
import com.obstacleavoid.util.use

class LoadingScreen(private val game: ObstacleAvoidGame) : ScreenAdapter() {

    // == fields ==
    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var renderer: ShapeRenderer

    private var progress: Float = 0.toFloat()
    private var waitTime = 0.1f
    private var changeScreen: Boolean = false
    private val assetManager: AssetManager

    init {
        assetManager = game.assetManager
    }

    // == public methods ==

    override fun show() {
        camera = OrthographicCamera()
        viewport = FitViewport(HUD_WIDTH, HUD_HEIGHT, camera)
        renderer = ShapeRenderer()
        renderer.color = Color.RED

        assetManager.load(FONT)
        assetManager.load(GAME_PLAY)
        assetManager.load(UI_SKIN)
        assetManager.load(HIT_SOUND)
    }

    override fun render(delta: Float) {
        update(delta)

        clearScreen()
        viewport.apply()
        renderer.projectionMatrix = camera.combined

        renderer.use(ShapeRenderer.ShapeType.Filled) {
            draw()
        }
//        renderer.begin(ShapeRenderer.ShapeType.Filled)
//
//        draw()
//        renderer.end()

        if (changeScreen)
            game.screen = MenuScreen(game)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        renderer.dispose()
    }

    // == private methods ==
    private fun update(delta: Float) {
        //        waitMilis(400);

        progress = assetManager.progress

        // update returns true when all assets are loaded
        if (assetManager.update()) {
            waitTime -= delta

            if (waitTime <= 0)
                changeScreen = true
        }
    }

    private fun draw() {
        val progressBarX = (HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2f
        val progressBarY = (HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2

        val rectProgress = progress * PROGRESS_BAR_WIDTH

        if (rectProgress >= 60 && rectProgress < 120)
            renderer.color = Color.ORANGE
        else if (rectProgress >= 120 && rectProgress < 180)
            renderer.color = Color.YELLOW
        else if (rectProgress >= 180)
            renderer.color = Color.GREEN

        renderer.rect(progressBarX, progressBarY,
                rectProgress, PROGRESS_BAR_HEIGHT)
    }

    companion object {

        // == constants ==
        private val log = Logger(LoadingScreen::class.java.name, Logger.DEBUG)

        private val PROGRESS_BAR_WIDTH = HUD_WIDTH / 2f
        private val PROGRESS_BAR_HEIGHT = 20f

        private fun waitMilis(milis: Long) {
            try {
                Thread.sleep(milis)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
    }
}
