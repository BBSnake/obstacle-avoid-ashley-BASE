package com.obstacleavoid.screen.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.Pool
import com.badlogic.gdx.utils.Pools
import com.obstacleavoid.ObstacleAvoidGame
import com.obstacleavoid.assets.HIT_SOUND
import com.obstacleavoid.common.GameManager
import com.obstacleavoid.config.*
import com.obstacleavoid.entity.Background
import com.obstacleavoid.entity.Obstacle
import com.obstacleavoid.entity.Player

class GameController(game: ObstacleAvoidGame) {

    // == fields ==
    lateinit var player: Player
        private set
    val obstacles = Array<Obstacle>()
    lateinit var background: Background
        private set
    private var obstacleTimer: Float = 0.toFloat()
    private var scoreTimer: Float = 0.toFloat()
    var lives = LIVES_START
        private set
    private var score: Int = 0
    var displayScore: Int = 0
        private set
    private lateinit var obstaclePool: Pool<Obstacle>
    private lateinit var hit: Sound
    private val assetManager: AssetManager

    private val startPlayerX = WORLD_CENTER_X - PLAYER_SIZE / 2f
    private val startPlayerY = 1 - PLAYER_SIZE / 2f

    init {
        assetManager = game.assetManager
        init()
    }

    // == init ==
    private fun init() {
        // create player
        player = Player()

        // position player
        player.setPosition(startPlayerX, startPlayerY)

        // create obstacle pool
        obstaclePool = Pools.get(Obstacle::class.java, 40)

        // create background
        background = Background()
        background.setPosition(0f, 0f)
        background.setSize(WORLD_WIDTH, WORLD_HEIGHT)

        hit = assetManager.get(HIT_SOUND)
    }

    // == public methods ==
    fun update(delta: Float) {
        if (isGameOver) {
            return
        }
        updatePlayer()
        updateObstacles(delta)
        updateScore(delta)
        updateDisplayScore(delta)

        if (isPlayerCollidingWithObstacle) {
            log.debug("Collision detected")
            lives--

            if (isGameOver) {
                log.debug("Game over")
                GameManager.instance.updateHighScore(score)
            } else
                restart()
        }
    }

    val isGameOver: Boolean
        get() = lives <= 0

    // == private methods ==
    private fun restart() {
        obstaclePool.freeAll(obstacles)
        obstacles.clear()
        player.setPosition(startPlayerX, startPlayerY)
    }

    private fun updateScore(delta: Float) {
        scoreTimer += delta
        if (scoreTimer >= SCORE_MAX_TIME) {
            score += MathUtils.random(1, 5)
            scoreTimer = 0f
        }
    }

    private fun updateDisplayScore(delta: Float) {
        if (displayScore < score)
            displayScore = Math.min(score, displayScore + (60 * delta).toInt())

    }

    private val isPlayerCollidingWithObstacle: Boolean
        get() {
            for (obstacle in obstacles) {
                if (obstacle.isNotHit && obstacle.isPlayerColliding(player)) {
                    hit.play(0.7f)
                    return true
                }
            }
            return false
        }

    private fun updatePlayer() {
        var xSpeed = 0f

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            xSpeed = MAX_PLAYER_X_SPEED
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            xSpeed = -MAX_PLAYER_X_SPEED

        player.x = player.x + xSpeed
        blockPlayerFromLeavingTheWorld()
    }

    private fun blockPlayerFromLeavingTheWorld() {
        val playerX = MathUtils.clamp(player.x, 0f, WORLD_WIDTH - player.width)

        player.setPosition(playerX, player.y)
    }

    private fun updateObstacles(delta: Float) {
        for (obstacle in obstacles) {
            obstacle.update()
        }

        createNewObstacle(delta)
        removePassedObstacles()
    }

    private fun createNewObstacle(delta: Float) {
        obstacleTimer += delta

        if (obstacleTimer >= OBSTACLE_SPAWN_TIME) {
            val min = 0f
            val max = WORLD_WIDTH - OBSTACLE_SIZE
            val obstacleX = MathUtils.random(min, max)
            val obstacleY = WORLD_HEIGHT

            val obstacle = obstaclePool.obtain()
            val difficultyLevel = GameManager.instance.difficultyLevel
            obstacle.ySpeed = difficultyLevel.obstacleSpeed
            obstacle.setPosition(obstacleX, obstacleY)

            obstacles.add(obstacle)
            obstacleTimer = 0f
        }
    }

    private fun removePassedObstacles() {
        if (obstacles.size > 0) {
            val first = obstacles.first()

            val minObstacleY = -OBSTACLE_SIZE

            if (first.y < minObstacleY) {
                obstacles.removeValue(first, true)
                obstaclePool.free(first)
            }
        }
    }

    companion object {

        // == constants ==
        private val log = Logger(GameController::class.java.name, Logger.DEBUG)
    }
}
