package com.revolution.robotics.views.chippedBox

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.DashPathEffect
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import com.revolution.robotics.core.extensions.color
import com.revolution.robotics.core.extensions.dimension
import kotlin.math.max
import kotlin.math.sqrt

class ChippedBoxDrawable(context: Context, private val config: ChippedBoxConfig) : Drawable() {

    companion object {
        const val TOP_LEFT_INDEX = 0
        const val TOP_RIGHT_INDEX = 1
        const val BOTTOM_RIGHT_INDEX = 2
        const val BOTTOM_LEFT_INDEX = 3
        val CORRECTION_MULTIPLIER = 1f / sqrt(2f)
    }

    private var helperBitmap: Bitmap? = null
    private var helperCanvas: Canvas? = null
    private val contentPaint = Paint().also { it.style = Paint.Style.FILL }
    private val chipBorderSize =
        if (config.chipBorderSizeResource == 0) {
            0
        } else {
            context.dimension(config.chipBorderSizeResource)
        }
    private val chipArray =
        config.chipArray.map { chipSize ->
            if (chipSize == 0) {
                0
            } else {
                context.dimension(chipSize)
            }
        }

    @ColorInt
    private val borderColor: Int = context.color(
        if (config.chipBorderColor == 0) {
            android.R.color.transparent
        } else {
            config.chipBorderColor
        }
    )
    @ColorInt
    private val backgroundColor: Int = context.color(
        if (config.chipBackgroundColor == 0) {
            android.R.color.transparent
        } else {
            config.chipBackgroundColor
        }
    )

    private val dashParams: FloatArray? =
        config.dashedBorderResources?.map { context.dimension(it).toFloat() }?.toFloatArray()

    override fun draw(canvas: Canvas) {
        if (config.dashedBorderResources == null) {
            canvas.drawChippedBox(config, true)
            canvas.drawChippedBox(config)
        } else {
            if (helperBitmap == null) helperBitmap =
                Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888)
            if (helperCanvas == null) helperBitmap?.let { helperCanvas = Canvas(it) }

            helperCanvas?.let { helperCanvas ->
                helperCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                helperCanvas.drawChippedBox(config, isBorder = true, isDashedBackground = true)
                helperCanvas.overlayDashedLine(config)
                helperBitmap?.let { canvas.drawBitmap(it, Matrix(), Paint()) }
            }
        }
    }

    private fun Canvas.drawChippedBox(
        config: ChippedBoxConfig,
        isBorder: Boolean = false,
        isDashedBackground: Boolean = false
    ) {
        val pathData = ChippedBoxPathData(
            config = config,
            isBorder = isBorder,
            width = width.toFloat(),
            height = height.toFloat()
        )

        pathData.init()
        contentPaint.color = if (isBorder && !isDashedBackground) borderColor else backgroundColor
        drawPath(pathData.buildPath(), contentPaint)
    }

    private fun Canvas.overlayDashedLine(
        config: ChippedBoxConfig
    ) {
        val pathData = ChippedBoxPathData(
            config = config,
            isBorder = true,
            width = width.toFloat(),
            height = height.toFloat()
        )
        pathData.init()
        contentPaint.color = borderColor
        contentPaint.style = Paint.Style.STROKE
        contentPaint.strokeWidth = chipBorderSize.toFloat() * 2f
        contentPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
        contentPaint.pathEffect = DashPathEffect(dashParams, 0f)

        drawPath(pathData.buildPath(), contentPaint)

        contentPaint.pathEffect = null
        contentPaint.xfermode = null
        contentPaint.style = Paint.Style.FILL
    }

    override fun setAlpha(alpha: Int) {
        contentPaint.alpha = alpha
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    inner class ChippedBoxPathData(
        val config: ChippedBoxConfig,
        val isBorder: Boolean,
        var width: Float,
        var height: Float,
        private var clipLeftBounds: Float = -1f,
        private var clipRightBounds: Float = -1f,
        private var borderSize: Float = 0f,
        private val edgeCorrections: ArrayList<Float> = arrayListOf(0f, 0f, 0f, 0f)
    ) {
        fun init() {
            if (isBorder) {
                chipArray.forEachIndexed { index, value ->
                    if (value > 0f) edgeCorrections[index] = chipBorderSize * CORRECTION_MULTIPLIER
                }
            }

            if (!isBorder) borderSize = chipBorderSize.toFloat()

            if (config.clipLeftSide && !isBorder) {
                clipLeftBounds =
                    max(chipArray[TOP_LEFT_INDEX], chipArray[BOTTOM_LEFT_INDEX]) + borderSize
            }

            if (config.clipRightSide && !isBorder) {
                clipRightBounds =
                    width - max(
                        chipArray[TOP_RIGHT_INDEX],
                        chipArray[BOTTOM_RIGHT_INDEX]
                    ) - borderSize
            }
        }

        fun buildPath() =
            Path().apply {
                applyStartingTopLeftCorner()
                applyTopRightCorner()
                applyBottomRightCorner()
                applyEndingBottomLeftCorner()
                close()
            }

        private fun Path.applyStartingTopLeftCorner() {
            if (config.clipLeftSide && !isBorder) {
                moveTo(clipLeftBounds, borderSize)
            } else {
                if (chipArray[TOP_LEFT_INDEX] != 0) {
                    moveTo(borderSize, chipArray[0] + borderSize + edgeCorrections[0])
                    lineTo(
                        chipArray[TOP_LEFT_INDEX] + borderSize + edgeCorrections[TOP_LEFT_INDEX],
                        borderSize
                    )
                } else {
                    moveTo(
                        chipArray[TOP_LEFT_INDEX] + borderSize + edgeCorrections[TOP_LEFT_INDEX],
                        borderSize
                    )
                }
            }
        }

        private fun Path.applyTopRightCorner() {
            if (config.clipRightSide && !isBorder) {
                lineTo(clipRightBounds, borderSize)
            } else {
                lineTo(
                    width - chipArray[TOP_RIGHT_INDEX] - borderSize - edgeCorrections[TOP_RIGHT_INDEX],
                    borderSize
                )
                if (chipArray[TOP_RIGHT_INDEX] != 0) {
                    lineTo(
                        width - borderSize,
                        chipArray[TOP_RIGHT_INDEX] + borderSize + edgeCorrections[TOP_RIGHT_INDEX]
                    )
                }
            }
        }

        private fun Path.applyBottomRightCorner() {
            if (config.clipRightSide && !isBorder) {
                lineTo(clipRightBounds, height - borderSize)
            } else {
                lineTo(
                    width - borderSize,
                    height - chipArray[BOTTOM_RIGHT_INDEX] - borderSize - edgeCorrections[BOTTOM_RIGHT_INDEX]
                )
                if (chipArray[BOTTOM_RIGHT_INDEX] != 0) {
                    lineTo(
                        width - chipArray[BOTTOM_RIGHT_INDEX] - borderSize - edgeCorrections[BOTTOM_RIGHT_INDEX],
                        height - borderSize
                    )
                }
            }
        }

        private fun Path.applyEndingBottomLeftCorner() {
            if (config.clipLeftSide && !isBorder) {
                lineTo(clipLeftBounds, height - borderSize)
            } else {
                lineTo(
                    chipArray[BOTTOM_LEFT_INDEX] + borderSize + edgeCorrections[BOTTOM_LEFT_INDEX],
                    height - borderSize
                )
                if (chipArray[BOTTOM_LEFT_INDEX] != 0) {
                    lineTo(
                        borderSize,
                        height - chipArray[BOTTOM_LEFT_INDEX] - borderSize - edgeCorrections[BOTTOM_LEFT_INDEX]
                    )
                }
            }
        }
    }
}
