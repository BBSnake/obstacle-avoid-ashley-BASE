package com.obstacleavoid.util

fun String.capitalize(): String {
    return "${this.substring(0, 1).toUpperCase()}${this.substring(1).toLowerCase()}"
}
