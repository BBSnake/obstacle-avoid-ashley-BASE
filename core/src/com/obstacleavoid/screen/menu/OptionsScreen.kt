package com.obstacleavoid.screen.menu

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Logger
import com.obstacleavoid.ObstacleAvoidGame
import com.obstacleavoid.assets.BACKGROUND
import com.obstacleavoid.assets.GAME_PLAY
import com.obstacleavoid.assets.PANEL
import com.obstacleavoid.assets.UI_SKIN
import com.obstacleavoid.common.GameManager
import com.obstacleavoid.config.DifficultyLevel

class OptionsScreen(game: ObstacleAvoidGame) : MenuScreenBase(game) {

    private lateinit var checkBoxGroup: ButtonGroup<CheckBox>
    private lateinit var easy: CheckBox
    private lateinit var medium: CheckBox
    private lateinit var hard: CheckBox

    override fun createUi(): Actor {
        val table = Table()
        table.defaults().pad(15f)

        val gamePlayAtlas = assetManager.get(GAME_PLAY)
        val uiskin = assetManager.get(UI_SKIN)

        val backgroundRegion = gamePlayAtlas.findRegion(BACKGROUND)
        table.background = TextureRegionDrawable(backgroundRegion)

        // label
        val label = Label("Difficulty", uiskin)

        val easyText = DifficultyLevel.EASY.name.capitalize()
        easy = checkBox(easyText, uiskin)
        val mediumText = DifficultyLevel.MEDIUM.name.capitalize()
        medium = checkBox(mediumText, uiskin)
        val hardText = DifficultyLevel.HARD.name.capitalize()
        hard = checkBox(hardText, uiskin)

        checkBoxGroup = ButtonGroup<CheckBox>(easy, medium, hard)

        val difficultyLevel = GameManager.instance.difficultyLevel
        val difficultyLevelText = difficultyLevel.name.capitalize()
        checkBoxGroup.setChecked(difficultyLevelText)

        val backButton = TextButton("Back", uiskin)
        backButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                back()
            }
        })

        val listener = object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                difficultyChanged()
            }
        }

        easy.addListener(listener)
        medium.addListener(listener)
        hard.addListener(listener)

        // setup table
        val contentTable = Table(uiskin)
        contentTable.defaults().pad(10f)
        contentTable.setBackground(PANEL)

        contentTable.add(label).row()
        contentTable.add<CheckBox>(easy).row()
        contentTable.add<CheckBox>(medium).row()
        contentTable.add<CheckBox>(hard).row()
        contentTable.add(backButton)

        table.add(contentTable)
        table.center()
        table.setFillParent(true)
        table.pack()

        return table
    }

    private fun back() {
        log.debug("back()")
        game.screen = MenuScreen(game)
    }

    private fun difficultyChanged() {
        log.debug("difficultyChanged()")
        val checked = checkBoxGroup.checked
        if (checked === easy) {
            log.debug("easy")
            GameManager.instance.updateDifficulty(DifficultyLevel.EASY)
        } else if (checked === medium) {
            log.debug("medium")
            GameManager.instance.updateDifficulty(DifficultyLevel.MEDIUM)
        } else if (checked === hard) {
            log.debug("hard")
            GameManager.instance.updateDifficulty(DifficultyLevel.HARD)
        }
    }

    companion object {

        private val log = Logger(OptionsScreen::class.java.name, Logger.DEBUG)

        private fun checkBox(text: String, skin: Skin): CheckBox {
            val checkBox = CheckBox(text, skin)
            checkBox.left().pad(8f)
            checkBox.labelCell.pad(8f)
            return checkBox
        }
    }
}
