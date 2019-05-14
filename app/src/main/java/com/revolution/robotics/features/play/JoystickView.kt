package com.revolution.robotics.features.play

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.color
import com.revolution.robotics.core.extensions.dimension

class JoystickView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private val joystickButtonSize = context.dimension(R.dimen.dimen_48dp)
    private val innerPadding = context.dimension(R.dimen.dimen_2dp)
    private val redPaint = Paint().apply {
        color = context.color(R.color.robotics_red)
    }

    private var center: Pair<Float, Float>? = null
    private var joystickPosition = Vector()

    init {
        setBackgroundResource(R.drawable.bg_joystick)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_CANCEL ||
            event.action == MotionEvent.ACTION_POINTER_UP ||
            event.action == MotionEvent.ACTION_UP
        ) {
            joystickPosition.reset()
        } else {
            center?.let { center ->
                joystickPosition.calculateAngle(center.first, center.second, event.x, event.y)
                joystickPosition.calculateDistance(center.first, center.second, event.x, event.y)
            }
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        if (background.intrinsicWidth == 0 || background.intrinsicHeight == 0) {
            return
        }
        if (center == null) {
            center = background.intrinsicWidth / 2.0f to background.intrinsicHeight / 2.0f
        }

        center?.let { center ->
            val (x, y) =
                if (joystickPosition.mirrored) {
                    center.first - joystickPosition.getX() to center.second - joystickPosition.getY()
                } else {
                    center.first + joystickPosition.getX() to center.second + joystickPosition.getY()
                }
            canvas.drawCircle(x, y, joystickButtonSize.toFloat() / 2, redPaint)
        }
    }

    private inner class Vector {
        var mirrored = false
        var angle = 0.0
        var distance = 0.0

        fun calculateAngle(x1: Float, y1: Float, x2: Float, y2: Float) {
            angle = Math.atan(((y2 - y1) / (x2 - x1)).toDouble())
            mirrored = x1 > x2
        }

        fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float) {
            center?.let { center ->
                distance = Math.min(
                    Math.sqrt(((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)).toDouble()),
                    (center.first - joystickButtonSize / 2 - innerPadding).toDouble()
                )
            }
        }

        fun reset() {
            angle = 0.0
            distance = 0.0
            mirrored = false
        }

        fun getX() = (distance * Math.cos(angle)).toFloat()
        fun getY() = (distance * Math.sin(angle)).toFloat()
    }
}
