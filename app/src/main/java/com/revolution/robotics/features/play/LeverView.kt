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
import kotlin.math.max
import kotlin.math.min

@Suppress("ClickableViewAccessibility")
class LeverView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    companion object {
        private const val FLOAT_CENTER = 0.5f
    }

    private val leverButtonSize = context.dimension(R.dimen.play_joystick_handle_size)
    private val leverPaddingY = leverButtonSize / 2
    private var leverPaddingX =
        (context.dimension(R.dimen.play_joystick_handle_size) - context.dimension(R.dimen.lever_width)) / 2
    private val gradientPaint = Paint()
    private val darkRedPaint = Paint().apply { color = context.color(R.color.robotics_red) }
    private val backgroundDrawable = context.getDrawable(R.drawable.bg_lever)

    private var center: Pair<Float, Float>? = null
    private var leverPosition = 0.0f
    private var isTouched = false
    private var axisChangedListener: ((value: Int) -> Unit)? = null

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_CANCEL ||
            event.action == MotionEvent.ACTION_POINTER_UP ||
            event.action == MotionEvent.ACTION_UP
        ) {
            isTouched = false
            leverPosition = 0.0f
        } else {
            isTouched = true
            leverPosition = ((event.y - leverPaddingY) / (measuredHeight - leverButtonSize) - FLOAT_CENTER)
                .limit(FLOAT_CENTER)
        }

        axisChangedListener?.invoke(leverPosition.normalizeForController())
        invalidate()
        return true
    }

    private fun init() {
        center = measuredWidth / 2.0f to measuredHeight / 2.0f
        backgroundDrawable?.setBounds(
            leverPaddingX,
            leverPaddingY,
            measuredWidth - leverPaddingX,
            measuredHeight - leverPaddingY
        )
        gradientPaint.shader = LinearGradient(
            0.0f,
            (measuredHeight / 2 - leverPaddingY).toFloat(),
            0.0f,
            (measuredHeight / 2 + leverPaddingY).toFloat(),
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
            canvas.drawCircle(
                center.first,
                (leverPosition + FLOAT_CENTER) * (measuredHeight - leverButtonSize) + leverPaddingY,
                leverButtonSize.toFloat() / 2,
                if (isTouched) darkRedPaint else gradientPaint
            )
        }
    }

    fun onAxisChanged(listener: ((value: Int) -> Unit)?) {
        axisChangedListener = listener
    }

    private fun Float.normalizeForController() =
        ((this + FLOAT_CENTER) * PlayPresenter.DIRECTION_VALUE_MAX).toInt()

    private fun Float.limit(limit: Float) =
        min(max(this, -limit), limit)
}
