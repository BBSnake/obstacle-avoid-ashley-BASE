package com.obstacleavoid.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

@JvmOverloads fun clearScreen(color: Color = Color.BLACK) {
    Gdx.gl.glClearColor(color.r, color.g, color.b, color.a)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
}

inline fun <B : Batch> B.use(func: (B) -> Unit) {
    begin()
    func(this)
    end()
}

inline fun <R : ShapeRenderer> R.use(shapeType: ShapeRenderer.ShapeType, func: (R) -> Unit) {
    begin(shapeType)
    func(this)
    end()
}
