package com.revolution.robotics.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.revolution.robotics.R
import com.revolution.robotics.databinding.ViewRoboticsButtonBinding
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig
import com.revolution.robotics.views.chippedBox.ChippedBoxDrawable

class RoboticsButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    var image: Drawable? = null
        set(value) {
            field = value
            binding.image.setImageDrawable(image)
        }

    var text: String? = null
        set(value) {
            field = value
            binding.text.text = text
        }

    private val binding = ViewRoboticsButtonBinding.inflate(LayoutInflater.from(context), this, true)

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
