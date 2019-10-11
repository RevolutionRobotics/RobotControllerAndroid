package com.revolution.robotics.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.revolution.robotics.R
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.views.chippedBox.ChippedBoxDrawable

class ChippedView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    init {
        background = ChippedBoxDrawable(
            context, ChippedBoxConfig.Builder()
                .chipSize(R.dimen.dimen_8dp)
                .backgroundColorResource(R.color.grey_28)
                .borderColorResource(R.color.white)
                .borderSize(R.dimen.dimen_1dp)
                .create()
        )
    }
}