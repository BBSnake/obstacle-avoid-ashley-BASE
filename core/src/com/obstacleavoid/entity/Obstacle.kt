package com.obstacleavoid.entity

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.utils.Pool
import com.obstacleavoid.config.MEDIUM_OBSTACLE_SPEED
import com.obstacleavoid.config.OBSTACLE_BOUNDS_RADIUS
import com.obstacleavoid.config.OBSTACLE_SIZE

class Obstacle : GameObjectBase(OBSTACLE_BOUNDS_RADIUS), Pool.Poolable {

    var ySpeed = MEDIUM_OBSTACLE_SPEED
        set(value) {
            field = value
        }
    private var hit: Boolean = false

    init {
        setSize(OBSTACLE_SIZE, OBSTACLE_SIZE)
    }

    fun update() {
        y -= ySpeed
    }

    fun isPlayerColliding(player: Player): Boolean {
        val playerBounds = player.bounds
        val overlaps = Intersector.overlaps(playerBounds, bounds)

        hit = overlaps

        return overlaps
    }

    val isNotHit: Boolean
        get() = !hit

    override fun reset() {
        hit = false
    }
}
