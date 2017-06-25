package com.obstacleavoid.util.debug

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Logger

class DebugCameraController {

    private val position = Vector2()
    private val startPosition = Vector2()
    private var zoom = 1.0f

    private val config: DebugCameraConfig

    init {
        config = DebugCameraConfig()
        log.info("cameraConfig= " + config)
    }

    fun setStartPosition(x: Float, y: Float) {
        startPosition.set(x, y)
        position.set(x, y)
    }

    fun applyTo(camera: OrthographicCamera) {
        camera.position.set(position, 0f)
        camera.zoom = zoom
        camera.update()
    }

    fun handleDebugInput(delta: Float) {
        // if we're not on Desktop, don't handle input
        if (Gdx.app.type != Application.ApplicationType.Desktop)
            return

        val moveSpeed = config.moveSpeed * delta
        val zoomSpeed = config.zoomSpeed * delta

        // move controls
        if (config.isLeftPressed)
            moveLeft(moveSpeed)
        else if (config.isRightPressed)
            moveRight(moveSpeed)
        else if (config.isUpPressed)
            moveUp(moveSpeed)
        else if (config.isDownPressed)
            moveDown(moveSpeed)

        // zoom controls
        if (config.isZoomInPressed)
            zoomIn(zoomSpeed)
        else if (config.isZoomOutPressed)
            zoomOut(zoomSpeed)

        // reset controls
        if (config.isResetJustPressed)
            reset()

        // log controls
        if (config.isLogJustPressed)
            logDebug()
    }

    private fun setPosition(x: Float, y: Float) {
        position.set(x, y)
    }

    private fun setZoom(value: Float) {
        zoom = MathUtils.clamp(value, config.maxZoomIn, config.maxZoomOut)
    }

    private fun moveCamera(xSpeed: Float, ySpeed: Float) {
        setPosition(position.x + xSpeed, position.y + ySpeed)
    }

    private fun moveLeft(speed: Float) {
        moveCamera(-speed, 0f)
    }

    private fun moveRight(speed: Float) {
        moveCamera(speed, 0f)
    }

    private fun moveUp(speed: Float) {
        moveCamera(0f, speed)
    }

    private fun moveDown(speed: Float) {
        moveCamera(0f, -speed)
    }

    private fun zoomIn(zoomSpeed: Float) {
        setZoom(zoom + zoomSpeed)
    }

    private fun zoomOut(zoomSpeed: Float) {
        setZoom(zoom - zoomSpeed)
    }

    private fun reset() {
        position.set(startPosition)
        setZoom(1.0f)
    }

    private fun logDebug() {
        log.debug("position= $position zoom= $zoom")
    }

    companion object {
        private val log = Logger(DebugCameraController::class.java.name, Logger.DEBUG)
    }

}
