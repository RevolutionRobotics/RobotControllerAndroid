package com.revolution.robotics.core.utils.chippedBox

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import com.revolution.robotics.core.extensions.color
import com.revolution.robotics.core.extensions.dimension
import kotlin.math.max
import kotlin.math.sqrt

@Suppress("UnnecessaryApply")
class ChippedBoxDrawable(context: Context, private val config: ChippedBoxConfig) : Drawable() {

    companion object {
        const val TOP_LEFT_INDEX = 0
        const val TOP_RIGHT_INDEX = 1
        const val BOTTOM_RIGHT_INDEX = 2
        const val BOTTOM_LEFT_INDEX = 3
        val CORRECTION_MULTIPLIER = 1f / sqrt(2f)
    }

    private val contentPaint = Paint().apply { style = Paint.Style.FILL }
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

    override fun draw(canvas: Canvas) {
        canvas.drawChippedBox(config, true)
        canvas.drawChippedBox(config)
    }

    private fun Canvas.drawChippedBox(config: ChippedBoxConfig, isBorder: Boolean = false) {
        val pathData = ChippedBoxPathData(
            config = config,
            isBorder = isBorder,
            width = width.toFloat(),
            height = height.toFloat()
        )

        pathData.init()
        contentPaint.color = if (isBorder) borderColor else backgroundColor
        drawPath(pathData.buildPath(), contentPaint)
    }

    override fun setAlpha(alpha: Int) {
        contentPaint.alpha = alpha
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    @Suppress("DataClassContainsFunctions")
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
