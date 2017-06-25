package com.obstacleavoid.common

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences
import com.obstacleavoid.ObstacleAvoidGame
import com.obstacleavoid.config.DifficultyLevel

class GameManager private constructor() {

    private val prefs: Preferences
    private var highscore: Int = 0
    var difficultyLevel = DifficultyLevel.MEDIUM
        private set

    init {
        prefs = Gdx.app.getPreferences(ObstacleAvoidGame::class.java.simpleName)
        highscore = prefs.getInteger(HIGH_SCORE_KEY, 0)
        val difficultyName = prefs.getString(DIFFICULTY_KEY, DifficultyLevel.MEDIUM.name)
        difficultyLevel = DifficultyLevel.valueOf(difficultyName)
    }

    val highScoreString: String
        get() = highscore.toString()

    fun updateHighScore(score: Int) {
        if (score < highscore) {
            return
        }

        highscore = score
        prefs.putInteger(HIGH_SCORE_KEY, highscore)
        prefs.flush()
    }

    fun updateDifficulty(newDifficultyLevel: DifficultyLevel) {
        if (difficultyLevel == newDifficultyLevel)
            return

        difficultyLevel = newDifficultyLevel
        prefs.putString(DIFFICULTY_KEY, difficultyLevel.name)
        prefs.flush()
    }

    companion object {

        val instance = GameManager()

        private val HIGH_SCORE_KEY = "highscore"
        private val DIFFICULTY_KEY = "difficulty"
    }
}
