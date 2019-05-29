package com.revolution.robotics.views.dialogs

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.forEachIndexed
import com.revolution.robotics.R
import com.revolution.robotics.core.extensions.dimension
import com.revolution.robotics.core.extensions.gone
import com.revolution.robotics.databinding.DialogRoboticsCoreButtonBinding

class DialogButtonHelper {

    fun createButtons(container: ViewGroup, buttons: Set<DialogButton>) {
        val inflater = LayoutInflater.from(container.context)
        container.removeAllViews()

        buttons.forEachIndexed { index, button ->
            val buttonBinding = DialogRoboticsCoreButtonBinding.inflate(inflater, container, false)
            buttonBinding.viewModel = DialogButtonViewModel(button, index == 0, index == buttons.size - 1)
            container.addView(buttonBinding.root)
            button.viewModel = buttonBinding.viewModel
        }
        container.gone = buttons.isEmpty()
        updateButtonWeights(container)
    }

    fun updateButtonWeights(container: ViewGroup) {
        container.forEachIndexed { index, child ->
            child.layoutParams = (child.layoutParams as LinearLayout.LayoutParams).apply {
                if (index != container.childCount - 1) {
                    marginEnd = container.context.dimension(R.dimen.dimen_2dp)
                }
                weight = 1.0f
            }
        }
    }
}
