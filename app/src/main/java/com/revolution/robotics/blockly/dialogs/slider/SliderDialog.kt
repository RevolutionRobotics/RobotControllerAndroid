package com.revolution.robotics.blockly.dialogs.slider

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import com.revolution.robotics.R
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.BlocklyDialogSliderBinding
import org.kodein.di.erased.instance
import org.revolutionrobotics.blockly.android.view.result.SliderResult

class SliderDialog : JavascriptPromptDialog<BlocklyDialogSliderBinding>(R.layout.blockly_dialog_slider),
    SliderMvp.View {

    companion object {

        private var Bundle.title by BundleArgumentDelegate.String("title")
        private var Bundle.maxValue by BundleArgumentDelegate.Int("maxValue")
        private var Bundle.defaultValue by BundleArgumentDelegate.Int("defaultValue")

        fun newInstance(title: String, maxValue: Int, defaultValue: Int = 0) =
            SliderDialog().withArguments { bundle ->
                bundle.title = title
                bundle.maxValue = maxValue
                bundle.defaultValue = defaultValue
            }
    }

    override val hasCloseButton = true
    override val hasTitle = true

    private val presenter: SliderMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, null)
        arguments?.let { arguments ->
            title.set(arguments.title)
            binding.slider.apply {
                max = arguments.maxValue
                progress = arguments.defaultValue
                progressDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
                thumb.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
            }
            binding.viewModel = SliderDialogViewModel(arguments.maxValue, presenter).apply {
                labelText.set("${arguments.defaultValue}")
            }
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onDoneClicked() {
        (blocklyResultHolder.result as? SliderResult)?.confirm(binding.slider.progress)
        dismissAllowingStateLoss()
    }
}
