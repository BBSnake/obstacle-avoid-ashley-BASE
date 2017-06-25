package com.obstacleavoid.config

enum class DifficultyLevel(val obstacleSpeed: Float) {
    EASY(EASY_OBSTACLE_SPEED),
    MEDIUM(MEDIUM_OBSTACLE_SPEED),
    HARD(HARD_OBSTACLE_SPEED);

    val isEasy: Boolean
        get() = this == EASY

    val isMedium: Boolean
        get() = this == MEDIUM

    val isHard: Boolean
        get() = this == HARD
}
