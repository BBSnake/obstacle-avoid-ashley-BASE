package com.obstacleavoid.screen.menu

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Logger
import com.obstacleavoid.ObstacleAvoidGame
import com.obstacleavoid.assets.BACKGROUND
import com.obstacleavoid.assets.GAME_PLAY
import com.obstacleavoid.assets.PANEL
import com.obstacleavoid.assets.UI_SKIN
import com.obstacleavoid.screen.game.GameScreen

class MenuScreen(game: ObstacleAvoidGame) : MenuScreenBase(game) {

    override fun createUi(): Actor {
        val table = Table()

        val gamePlayAtlas = assetManager.get(GAME_PLAY)
        val uiskin = assetManager.get(UI_SKIN)

        val backgroundRegion = gamePlayAtlas.findRegion(BACKGROUND)

        table.background = TextureRegionDrawable(backgroundRegion)

        //play button
        val playButton = TextButton("Play", uiskin)
        playButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                play()
            }
        })

        // highscore button
        val highscoreButton = TextButton("Highscore", uiskin)
        highscoreButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                showHighScore()
            }
        })

        // options button
        val optionsButton = TextButton("Options", uiskin)
        optionsButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                showOptions()
            }
        })
        // quit button
        val quitButton = TextButton("Quit", uiskin)
        quitButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                quit()
            }
        })

        // setup table
        val buttonTable = Table(uiskin)
        buttonTable.defaults().pad(20f)
        buttonTable.setBackground(PANEL)

        buttonTable.add(playButton).row()
        buttonTable.add(highscoreButton).row()
        buttonTable.add(optionsButton).row()
        buttonTable.add(quitButton)

        buttonTable.center()

        table.add(buttonTable)
        table.center()
        table.setFillParent(true)
        table.pack()

        return table
    }

    private fun play() {
        log.debug("play()")
        game.screen = GameScreen(game)
    }

    private fun showHighScore() {
        log.debug("showHighScore()")
        game.screen = HighScoreScreen(game)
    }

    private fun showOptions() {
        log.debug("showOptions()")
        game.screen = OptionsScreen(game)
    }

    private fun quit() {
        log.debug("quit()")
        Gdx.app.exit()
    }

    companion object {

        private val log = Logger(MenuScreen::class.java.name, Logger.DEBUG)
    }
}
