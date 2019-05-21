package com.revolution.robotics.features.play

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.color
import com.revolution.robotics.core.extensions.dimension

@Suppress("ClickableViewAccessibility")
class JoystickView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    companion object {
        private const val JOYSTICK_AXIS_CENTER = 128
    }

    private val joystickButtonSize = context.dimension(R.dimen.play_joystick_handle_size)
    private val joystickPadding = joystickButtonSize / 2
    private val innerPadding = context.dimension(R.dimen.dimen_2dp)
    private val gradientPaint = Paint()
    private val darkRedPaint = Paint().apply { color = context.color(R.color.robotics_red) }
    private val backgroundDrawable = context.getDrawable(R.drawable.bg_joystick)

    private var center: Pair<Float, Float>? = null
    private var joystickPosition = Vector()
    private var joystickPositionMax = -1
    private var isTouched = false

    var listener: JoystickEventListener? = null

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_CANCEL ||
            event.action == MotionEvent.ACTION_POINTER_UP ||
            event.action == MotionEvent.ACTION_UP
        ) {
            isTouched = false
            joystickPosition.reset()
        } else {
            center?.let { center ->
                isTouched = true
                joystickPosition.calculateAngle(center.first, center.second, event.x, event.y)
                joystickPosition.calculateDistance(center.first, center.second, event.x, event.y)
            }
        }

        listener?.onJoystickPositionChanged(
            joystickPosition.getX().normalizeForController(),
            joystickPosition.getY().normalizeForController()
        )
        invalidate()
        return true
    }

    private fun init() {
        center = measuredWidth / 2.0f to measuredHeight / 2.0f
        backgroundDrawable?.setBounds(
            joystickPadding,
            joystickPadding,
            measuredWidth - joystickPadding,
            measuredHeight - joystickPadding
        )
        joystickPositionMax = measuredWidth / 2 - joystickButtonSize / 2 - innerPadding
        gradientPaint.shader = LinearGradient(
            0.0f,
            measuredHeight / 2.0f - joystickPadding,
            0.0f,
            measuredHeight / 2.0f + joystickPadding,
            context.color(R.color.robotics_orange),
            context.color(R.color.robotics_red),
            Shader.TileMode.REPEAT
        )
    }

    override fun onDraw(canvas: Canvas) {
        if (measuredWidth == 0 || measuredHeight == 0) {
            return
        }
        if (center == null) {
            init()
        }

        backgroundDrawable?.draw(canvas)
        center?.let { center ->
            val (x, y) = center.first + joystickPosition.getX() to center.second + joystickPosition.getY()
            canvas.drawCircle(x, y, joystickButtonSize.toFloat() / 2, if (isTouched) darkRedPaint else gradientPaint)
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
            center?.let {
                distance = Math.min(
                    Math.sqrt(((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)).toDouble()),
                    joystickPositionMax.toDouble()
                )
            }
        }

        fun reset() {
            angle = 0.0
            distance = 0.0
            mirrored = false
        }

        fun getX() = ((if (mirrored) -distance else distance) * Math.cos(angle)).toFloat()
        fun getY() = ((if (mirrored) -distance else distance) * Math.sin(angle)).toFloat()
    }

    private fun Float.normalizeForController() =
        JOYSTICK_AXIS_CENTER + (this / joystickPositionMax * JOYSTICK_AXIS_CENTER).toInt()

    interface JoystickEventListener {
        fun onJoystickPositionChanged(x: Int, y: Int)
    }
}
