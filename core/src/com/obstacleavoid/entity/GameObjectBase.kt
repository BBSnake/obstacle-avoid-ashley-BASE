package com.obstacleavoid.entity

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle

abstract class GameObjectBase(boundsRadius: Float) {

    var x: Float = 0.toFloat()
        set(value) {
            field = value
            updateBounds()
        }
    var y: Float = 0.toFloat()
        set(value) {
            field = value
            updateBounds()
        }
    var width = 1f
        private set
    var height = 1f
        private set

    val bounds: Circle

    init {
        bounds = Circle(x, y, boundsRadius)
    }

    fun drawDebug(renderer: ShapeRenderer) {
        renderer.circle(bounds.x, bounds.y, bounds.radius, 30)
    }

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
        updateBounds()
    }

    fun updateBounds() {
        val halfWidth = width / 2f
        val halfHeight = height / 2f
        bounds.setPosition(x + halfWidth, y + halfHeight)
    }

    fun setSize(width: Float, height: Float) {
        this.width = width
        this.height = height
    }
}
