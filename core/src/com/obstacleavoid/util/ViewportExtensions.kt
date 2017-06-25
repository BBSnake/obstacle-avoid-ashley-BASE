package com.obstacleavoid.util

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Logger
import com.badlogic.gdx.utils.viewport.Viewport

private val log = Logger("ViewportExtensions", Logger.DEBUG)
private val DEFAULT_CELL_SIZE = 1

fun Viewport.drawGrid(renderer: ShapeRenderer, cellSize: Int = DEFAULT_CELL_SIZE) {
    val usedCellSize: Int
    if(cellSize < DEFAULT_CELL_SIZE)
        usedCellSize = DEFAULT_CELL_SIZE
    else
        usedCellSize = cellSize
    val oldColor = Color(renderer.color)
    val worldWidth = this.worldWidth.toInt()
    val worldHeight = this.worldHeight.toInt()

    val doubleWorldWidth = worldWidth * 2
    val doubleWorldHeight = worldHeight * 2

    renderer.projectionMatrix = this.camera.combined
    renderer.begin(ShapeRenderer.ShapeType.Line)
    renderer.color = Color.WHITE

    // draw vertical lines
    var x = -doubleWorldWidth
    while (x < doubleWorldWidth) {
        renderer.line(x.toFloat(), (-doubleWorldHeight).toFloat(), x.toFloat(), doubleWorldHeight.toFloat())
        x += usedCellSize
    }
    // draw horizontal lines
    var y = -doubleWorldHeight
    while (y < doubleWorldHeight) {
        renderer.line((-doubleWorldWidth).toFloat(), y.toFloat(), doubleWorldWidth.toFloat(), y.toFloat())
        y += usedCellSize
    }
    // draw axis lines
    renderer.color = Color.RED
    renderer.line(0f, (-doubleWorldHeight).toFloat(), 0f, doubleWorldHeight.toFloat())
    renderer.line((-doubleWorldWidth).toFloat(), 0f, doubleWorldWidth.toFloat(), 0f)
    // draw world bounds
    renderer.color = Color.GREEN
    renderer.line(0f, worldHeight.toFloat(), worldWidth.toFloat(), worldHeight.toFloat())
    renderer.line(worldWidth.toFloat(), 0f, worldWidth.toFloat(), worldHeight.toFloat())

    renderer.end()

    renderer.color = oldColor
}

fun Viewport.debugPixelPerUnit() {
    val screenWidth = this.screenWidth.toFloat()
    val screenHeight = this.screenHeight.toFloat()

    val worldWidth = this.worldWidth
    val worldHeight = this.worldHeight

    // PPU => pixels per world unit
    val xPPU = screenWidth / worldWidth
    val yPPU = screenHeight / worldHeight

    log.debug("x PPU= $xPPU y PPU= $yPPU")
}
//
//object ViewportUtils {
//
//    private val log = Logger(ViewportUtils::class.java.name, Logger.DEBUG)
//
//    private val DEFAULT_CELL_SIZE = 1
//
//    @JvmOverloads fun drawGrid(viewport: Viewport?, renderer: ShapeRenderer?, cellSize: Int = DEFAULT_CELL_SIZE) {
//        var cellSize = cellSize
//        // validate arguments
//        if (viewport == null)
//            throw IllegalArgumentException("Viewport param is required.")
//        if (renderer == null)
//            throw IllegalArgumentException("Renderer param is required.")
//        if (cellSize < DEFAULT_CELL_SIZE)
//            cellSize = DEFAULT_CELL_SIZE
//
//        // copy old color from renderer
//        val oldColor = Color(renderer.color)
//
//        val worldWidth = viewport.worldWidth.toInt()
//        val worldHeight = viewport.worldHeight.toInt()
//
//        val doubleWorldWidth = worldWidth * 2
//        val doubleWorldHeight = worldHeight * 2
//
//        renderer.projectionMatrix = viewport.camera.combined
//        renderer.begin(ShapeRenderer.ShapeType.Line)
//        renderer.color = Color.WHITE
//
//        // draw vertical lines
//        var x = -doubleWorldWidth
//        while (x < doubleWorldWidth) {
//            renderer.line(x.toFloat(), (-doubleWorldHeight).toFloat(), x.toFloat(), doubleWorldHeight.toFloat())
//            x += cellSize
//        }
//        // draw horizontal lines
//        var y = -doubleWorldHeight
//        while (y < doubleWorldHeight) {
//            renderer.line((-doubleWorldWidth).toFloat(), y.toFloat(), doubleWorldWidth.toFloat(), y.toFloat())
//            y += cellSize
//        }
//        // draw axis lines
//        renderer.color = Color.RED
//        renderer.line(0f, (-doubleWorldHeight).toFloat(), 0f, doubleWorldHeight.toFloat())
//        renderer.line((-doubleWorldWidth).toFloat(), 0f, doubleWorldWidth.toFloat(), 0f)
//        // draw world bounds
//        renderer.color = Color.GREEN
//        renderer.line(0f, worldHeight.toFloat(), worldWidth.toFloat(), worldHeight.toFloat())
//        renderer.line(worldWidth.toFloat(), 0f, worldWidth.toFloat(), worldHeight.toFloat())
//
//        renderer.end()
//
//        renderer.color = oldColor
//    }
//
//    fun debugPixelPerUnit(viewport: Viewport?) {
//        if (viewport == null)
//            throw IllegalArgumentException("Viewport param is required.")
//
//        val screenWidth = viewport.screenWidth.toFloat()
//        val screenHeight = viewport.screenHeight.toFloat()
//
//        val worldWidth = viewport.worldWidth
//        val worldHeight = viewport.worldHeight
//
//        // PPU => pixels per world unit
//        val xPPU = screenWidth / worldWidth
//        val yPPU = screenHeight / worldHeight
//
//        log.debug("x PPU= $xPPU y PPU= $yPPU")
//    }
//
//}
