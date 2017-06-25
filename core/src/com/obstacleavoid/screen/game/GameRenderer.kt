package com.obstacleavoid.screen.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.obstacleavoid.assets.*
import com.obstacleavoid.config.*
import com.obstacleavoid.util.clearScreen
import com.obstacleavoid.util.debug.DebugCameraController
import com.obstacleavoid.util.debugPixelPerUnit
import com.obstacleavoid.util.drawGrid
import com.obstacleavoid.util.use

class GameRenderer(private val batch: SpriteBatch, private val assetManager: AssetManager, private val controller: GameController) : Disposable {

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var renderer: ShapeRenderer

    private lateinit var hudCamera: OrthographicCamera
    private lateinit var hudViewport: Viewport

    private lateinit var font: BitmapFont
    private val layout = GlyphLayout()

    private lateinit var debugCameraController: DebugCameraController

    private lateinit var playerRegion: TextureRegion
    private lateinit var obstacleRegion: TextureRegion
    private lateinit var backgroundRegion: TextureRegion

    init {
        init()
    }

    private fun init() {
        camera = OrthographicCamera()
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()

        hudCamera = OrthographicCamera()
        hudViewport = FitViewport(HUD_WIDTH, HUD_HEIGHT, hudCamera)
        font = assetManager.get(FONT)

        debugCameraController = DebugCameraController()
        debugCameraController.setStartPosition(WORLD_CENTER_X, WORLD_CENTER_Y)

        val gamePlayAtlas = assetManager.get(GAME_PLAY)

        playerRegion = gamePlayAtlas.findRegion(PLAYER)
        obstacleRegion = gamePlayAtlas.findRegion(OBSTACLE)
        backgroundRegion = gamePlayAtlas.findRegion(BACKGROUND)
    }

    fun render(delta: Float) {
        batch.totalRenderCalls = 0

        debugCameraController.handleDebugInput(delta)
        debugCameraController.applyTo(camera)

        if (Gdx.input.isTouched && !controller.isGameOver) {
            val screenTouch = Vector2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
            val worldTouch = viewport.unproject(Vector2(screenTouch))
            println("screenTouch= " + screenTouch)
            println("worldTouch= " + worldTouch)

            val xSpeed: Float
            val player = controller.player
            if (worldTouch.x > WORLD_CENTER_X)
                xSpeed = MAX_PLAYER_X_SPEED
            else
                xSpeed = -MAX_PLAYER_X_SPEED
            val xPosition = MathUtils.clamp(player.x + xSpeed, 0f, WORLD_WIDTH - player.width)
            player.setPosition(xPosition, player.y)
        }
        // clear screen
        clearScreen()
        // render graphics
        renderGameplay()

        // render ui/hud
        renderUi()
        // render debug graphics
        //        renderDebug();
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        hudViewport.update(width, height, true)
        viewport.debugPixelPerUnit()
    }

    private fun renderGameplay() {
        viewport.apply()

        batch.projectionMatrix = camera.combined

        batch.use {
            val background = controller.background
            batch.draw(backgroundRegion,
                    background.x, background.y,
                    background.width, background.height)

            // draw player
            val player = controller.player
            batch.draw(playerRegion,
                    player.x, player.y,
                    player.width, player.height)

            // draw obstacles
            for (obstacle in controller.obstacles) {
                batch.draw(obstacleRegion,
                        obstacle.x, obstacle.y,
                        obstacle.width, obstacle.height)
            }
        }
    }

    private fun renderUi() {
        hudViewport.apply()
        batch.projectionMatrix = hudCamera.combined

        batch.use {
            val livesText = "Lives: " + controller.lives
            layout.setText(font, livesText)

            font.draw(batch, livesText,
                    20f,
                    HUD_HEIGHT - layout.height)

            val scoreText = "Score: " + controller.displayScore
            layout.setText(font, scoreText)

            font.draw(batch, scoreText,
                    HUD_WIDTH - layout.width - 20f,
                    HUD_HEIGHT - layout.height)
        }
    }

    private fun renderDebug() {
        viewport.apply()
        renderer.projectionMatrix = camera.combined

        renderer.use(ShapeRenderer.ShapeType.Line) {
            drawDebug()
        }
        
        viewport.drawGrid(renderer)
    }

    private fun drawDebug() {
        val player = controller.player
        player.drawDebug(renderer)

        val obstacles = controller.obstacles

        for (obstacle in obstacles)
            obstacle.drawDebug(renderer)
    }

    override fun dispose() {
        renderer.dispose()
    }
}
