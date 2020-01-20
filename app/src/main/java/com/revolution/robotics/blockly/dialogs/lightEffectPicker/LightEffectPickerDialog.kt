package com.revolution.robotics.blockly.dialogs.lightEffectPicker

import android.os.Bundle
import android.view.View
import com.revolution.robotics.R
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.BlocklyDialogLightEffectPickerBinding

class LightEffectPickerDialog :
    JavascriptPromptDialog<BlocklyDialogLightEffectPickerBinding>(R.layout.blockly_dialog_light_effect_picker) {

    companion object {

        private var Bundle.title by BundleArgumentDelegate.String("title")
        private var Bundle.selectedLightEffect by BundleArgumentDelegate.StringNullable("selectedLightEffect")

        fun newInstance(title: String, selectedLightEffect: String? = null) =
            LightEffectPickerDialog().withArguments { bundle ->
                bundle.title = title
                bundle.selectedLightEffect = selectedLightEffect
            }
    }

    override val hasCloseButton = true
    override val hasTitle = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { arguments ->
            val options = LightEffectIcons.ICONS.map { icon ->
                LightEffectOption(
                    icon.key,
                    icon.value,
                    icon.key == arguments.selectedLightEffect
                )
            }
            binding.viewModel =
                LightEffectPickerViewModel(options, blocklyResultHolder, this)
            title.set(arguments.title)
        }
    }
}
