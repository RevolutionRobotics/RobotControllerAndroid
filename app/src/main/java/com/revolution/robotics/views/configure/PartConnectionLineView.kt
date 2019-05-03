package com.revolution.robotics.views.configure

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.color
import com.revolution.robotics.core.extensions.dimension

class PartConnectionLineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    private var isVertical: Boolean = false
    private var isDashed: Boolean = false
    private val paint: Paint = Paint()
    private val bounds = Rect()
    private val linePoints = FloatArray(LINE_ARRAY_SIZE)
    private val dashParams = FloatArray(DASH_ARRAY_SIZE)
    @ColorRes
    private var dashedLineColorRes: Int = R.color.grey_6d
    @ColorRes
    private var lineColorRes: Int = R.color.grey_6d

    private var dashedLineColor: Int? = null
    private var lineColor: Int? = null
    private val lineWidthDimension = context.dimension(R.dimen.configure_connections_line_width).toFloat()
    private val lineDashWidthDimension = context.dimension(R.dimen.configure_connections_line_dash_width).toFloat()
    private val lineGapWidthDimension = context.dimension(R.dimen.configure_connections_line_gap_width).toFloat()

    companion object {
        const val LINE_ARRAY_SIZE = 4
        const val LINE_START_X_INDEX = 0
        const val LINE_START_Y_INDEX = 1
        const val LINE_END_X_INDEX = 2
        const val LINE_END_Y_INDEX = 3

        const val DASH_ARRAY_SIZE = 2
        const val DASH_ARRAY_DASH_INDEX = 0
        const val DASH_ARRAY_GAP_INDEX = 1
    }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        initAttrs(attrs)
        paint.strokeWidth = lineWidthDimension
        paint.style = Paint.Style.STROKE
        dashedLineColor = context.color(dashedLineColorRes)
        lineColor = context.color(lineColorRes)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.getClipBounds(bounds)
        updatePoints()
        canvas.drawCustomLine()
    }

    private fun updatePoints() {
        if (isVertical) {
            linePoints[LINE_START_X_INDEX] = bounds.width() / 2f
            linePoints[LINE_START_Y_INDEX] = 0f
            linePoints[LINE_END_X_INDEX] = bounds.width() / 2f
            linePoints[LINE_END_Y_INDEX] = bounds.height().toFloat()
        } else {
            linePoints[LINE_START_X_INDEX] = 0f
            linePoints[LINE_START_Y_INDEX] = bounds.height() / 2f
            linePoints[LINE_END_X_INDEX] = bounds.width().toFloat()
            linePoints[LINE_END_Y_INDEX] = bounds.height() / 2f
        }
    }

    private fun Canvas.drawCustomLine() {
        if (isDashed) {
            dashedLineColor?.let { paint.color = it }
            dashParams[DASH_ARRAY_DASH_INDEX] = lineDashWidthDimension
            dashParams[DASH_ARRAY_GAP_INDEX] = lineGapWidthDimension
            paint.pathEffect = DashPathEffect(dashParams, 0f)
        } else {
            lineColor?.let { paint.color = it }
            paint.pathEffect = null
        }
        drawLines(linePoints, paint)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs == null) return
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PartConnectionLineView,
            0, 0
        )
        try {
            isVertical = a.getBoolean(R.styleable.PartConnectionLineView_isLineVertical, isVertical)
        } finally {
            a.recycle()
        }
    }

    fun setIsDashed(dashed: Boolean) {
        isDashed = dashed
        invalidate()
    }

    fun setLineColor(@ColorRes color: Int) {
        lineColorRes = color
        lineColor = context.color(lineColorRes)
        invalidate()
    }
}
