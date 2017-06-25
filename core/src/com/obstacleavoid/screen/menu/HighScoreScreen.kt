package com.obstacleavoid.screen.menu

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Label
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
import com.obstacleavoid.common.GameManager

class HighScoreScreen(game: ObstacleAvoidGame) : MenuScreenBase(game) {

    override fun createUi(): Actor {
        val table = Table()

        val gamePlayAtlas = assetManager.get(GAME_PLAY)
        val uiskin = assetManager.get(UI_SKIN)

        val backgroundRegion = gamePlayAtlas.findRegion(BACKGROUND)

        // background
        table.background = TextureRegionDrawable(backgroundRegion)

        // highscore text
        val highscoreText = Label("Highscore", uiskin)

        // highscore label
        val highScoreString = GameManager.instance.highScoreString
        val highscoreLabel = Label(highScoreString, uiskin)

        // back button
        val backButton = TextButton("Back", uiskin)

        backButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                back()
            }
        })

        // setup tables
        val contentTable = Table(uiskin)
        contentTable.defaults().pad(20f)
        contentTable.setBackground(PANEL)

        contentTable.add(highscoreText).row()
        contentTable.add(highscoreLabel).row()
        contentTable.add(backButton)

        contentTable.center()

        table.add(contentTable)
        table.center()
        table.setFillParent(true)
        table.pack()

        return table
    }

    private fun back() {
        log.debug("back")
        game.screen = MenuScreen(game)
    }

    companion object {

        private val log = Logger(HighScoreScreen::class.java.name, Logger.DEBUG)
    }
}
