package com.revolution.robotics.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class TopCropImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        scaleType = ScaleType.MATRIX
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        recomputeMatrix()
    }

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
        recomputeMatrix()
        return super.setFrame(l, t, r, b)
    }

    private fun recomputeMatrix() {
        drawable?.let { drawable ->
            val matrixScale = (width - paddingLeft - paddingRight).toFloat() / drawable.intrinsicWidth
            imageMatrix = imageMatrix.apply {
                setScale(matrixScale, matrixScale)
            }
        }
    }
}
