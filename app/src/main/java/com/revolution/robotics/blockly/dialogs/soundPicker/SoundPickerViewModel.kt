package com.revolution.robotics.blockly.dialogs.soundPicker

import androidx.lifecycle.ViewModel

class SoundPickerViewModel(private val presenter: SoundPickerMvp.Presenter) : ViewModel() {

    fun onDoneClicked() {
        presenter.onDoneClicked()
    }
}
