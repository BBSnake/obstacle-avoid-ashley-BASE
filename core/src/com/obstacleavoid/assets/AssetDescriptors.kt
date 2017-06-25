package com.obstacleavoid.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Skin

    val FONT = AssetDescriptor<BitmapFont>(UI_FONT_PATH, BitmapFont::class.java)

    val GAME_PLAY = AssetDescriptor<TextureAtlas>(GAME_PLAY_PATH, TextureAtlas::class.java)

    val UI_SKIN = AssetDescriptor<Skin>(UI_SKIN_PATH, Skin::class.java)

    val HIT_SOUND = AssetDescriptor<Sound>(HIT_PATH, Sound::class.java)
