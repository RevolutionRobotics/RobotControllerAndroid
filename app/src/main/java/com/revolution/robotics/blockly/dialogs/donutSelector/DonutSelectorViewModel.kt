package com.revolution.robotics.blockly.dialogs.donutSelector

import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class DonutSelectorViewModel(
    val areControlsVisible: Boolean,
    private val presenter: DonutSelectorMvp.Presenter
) : ViewModel() {

    companion object {
        private val BACKGROUND = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_8dp)
            .backgroundColorResource(R.color.grey_28)
            .borderColorResource(R.color.white)
            .create()
    }

    val doneButtonBackground = BACKGROUND

    fun onDoneButtonClicked() {
        presenter.onDoneButtonClicked()
    }

    fun onSelectAllClicked() {
        presenter.onSelectAllClicked()
    }
}
