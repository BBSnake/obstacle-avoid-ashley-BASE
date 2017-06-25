package com.obstacleavoid.entity

import com.obstacleavoid.config.PLAYER_BOUNDS_RADIUS
import com.obstacleavoid.config.PLAYER_SIZE

class Player : GameObjectBase(PLAYER_BOUNDS_RADIUS) {
    init {
        setSize(PLAYER_SIZE, PLAYER_SIZE)
    }
}
