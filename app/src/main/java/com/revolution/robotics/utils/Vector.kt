package com.revolution.robotics.utils

import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

class Vector {
    var mirrored = false
    var angle = 0.0
    var distance = 0.0

    fun calculateAngle(x1: Float, y1: Float, x2: Float, y2: Float) {
        angle = atan(((y2 - y1) / (x2 - x1)).toDouble())
        mirrored = x1 > x2
    }

    fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float, maxDistance: Float = -1.0f) {
        val realDistance = sqrt(((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)).toDouble())
        distance =
            if (maxDistance == -1.0f) {
                realDistance
            } else {
                min(realDistance, maxDistance.toDouble())
            }
    }

    fun reset() {
        angle = 0.0
        distance = 0.0
        mirrored = false
    }

    fun getX() = ((if (mirrored) -distance else distance) * cos(angle)).toFloat()
    fun getY() = ((if (mirrored) -distance else distance) * sin(angle)).toFloat()
}
