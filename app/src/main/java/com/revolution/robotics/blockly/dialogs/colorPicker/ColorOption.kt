package com.revolution.robotics.blockly.dialogs.colorPicker

import androidx.lifecycle.ViewModel

class ColorOption(
    val color: String,
    val isSelected: Boolean,
    private val presenter: ColorPickerMvp.Presenter
) :
    ViewModel() {

    fun onColorClicked() {
        presenter.onColorSelected(this)
    }
}
