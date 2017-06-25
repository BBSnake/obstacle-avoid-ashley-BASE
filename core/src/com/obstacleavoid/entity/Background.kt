package com.obstacleavoid.entity

class Background {

    var x: Float = 0.toFloat()
        private set
    var y: Float = 0.toFloat()
        private set
    var width: Float = 0.toFloat()
        private set
    var height: Float = 0.toFloat()
        private set

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun setSize(width: Float, height: Float) {
        this.width = width
        this.height = height
    }
}
