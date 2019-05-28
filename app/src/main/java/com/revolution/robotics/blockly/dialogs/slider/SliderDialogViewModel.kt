package com.revolution.robotics.blockly.dialogs.slider

import android.widget.SeekBar
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.views.chippedBox.ChippedBoxConfig

class SliderDialogViewModel(val maxValue: Int, private val presenter: SliderMvp.Presenter) : ViewModel(),
    SeekBar.OnSeekBarChangeListener {

    companion object {
        private val BACKGROUND = ChippedBoxConfig.Builder()
            .chipSize(R.dimen.dimen_8dp)
            .backgroundColorResource(R.color.grey_28)
            .borderColorResource(R.color.white)
            .create()
    }

    val background = BACKGROUND
    val labelText = ObservableField<String>("0")

    fun onDoneClicked() {
        presenter.onDoneClicked()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        labelText.set("$progress")
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
}
