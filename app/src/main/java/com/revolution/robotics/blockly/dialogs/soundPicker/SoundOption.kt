package com.revolution.robotics.blockly.dialogs.soundPicker

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class SoundOption(
    val fileName: String,
    emojiUnicode: Int,
    isSelected: Boolean,
    private val presenter: SoundPickerMvp.Presenter
) : ViewModel() {

    companion object {
        private val BACKGROUND = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_12dp)
            .backgroundColorResource(R.color.grey_1d)
            .create()
        private val BACKGROUND_SELECTED = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_12dp)
            .backgroundColorResource(R.color.grey_1d)
            .borderColorResource(R.color.white)
            .create()
    }

    val background = BACKGROUND
    val backgroundSelected = BACKGROUND_SELECTED
    val isSelected = ObservableBoolean(isSelected)
    val iconEmoji = String(Character.toChars(emojiUnicode))

    fun onSoundClicked() {
        presenter.onSoundSelected(this)
    }
}
