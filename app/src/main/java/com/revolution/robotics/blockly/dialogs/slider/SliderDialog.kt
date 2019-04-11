package com.revolution.robotics.blockly.dialogs.slider

import android.os.Bundle
import android.view.View
import com.revolution.robotics.R
import com.revolution.robotics.BaseDialogFragment
import com.revolution.robotics.databinding.BlocklyDialogSliderBinding
import org.revolution.blockly.view.DialogFactory

class SliderDialog : BaseDialogFragment<BlocklyDialogSliderBinding>(R.layout.blockly_dialog_slider) {

    lateinit var options: DialogFactory.SliderOptions
    lateinit var viewModel: SliderDialogViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = SliderDialogViewModel(options)
        binding.viewModel = viewModel
    }

    override fun getResult() = "${binding.slider.progress + viewModel.minValue}"
}
