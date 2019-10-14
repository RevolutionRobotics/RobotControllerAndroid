package com.revolution.robotics.blockly.dialogs.colorPicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.revolution.robotics.R
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.blockly.dialogs.colorPicker.adapter.ColorPickerAdapter
import com.revolution.robotics.blockly.utils.StringList
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.BlocklyDialogColorPickerBinding
import org.kodein.di.erased.instance
import org.revolutionrobotics.blockly.android.view.result.ColorResult

class ColorPickerDialog :
    JavascriptPromptDialog<BlocklyDialogColorPickerBinding>(R.layout.blockly_dialog_color_picker), ColorPickerMvp.View {

    companion object {
        private const val SPAN_COUNT = 7

        private var Bundle.title by BundleArgumentDelegate.String("title")
        private var Bundle.colors by BundleArgumentDelegate.Parcelable<StringList>("colors")
        private var Bundle.selectedColor by BundleArgumentDelegate.StringNullable("selectedColor")

        fun newInstance(title: String, colors: List<String>, selectedColor: String? = null) =
            ColorPickerDialog().withArguments { bundle ->
                bundle.title = title
                bundle.colors = StringList().apply { addAll(colors) }
                bundle.selectedColor = selectedColor
            }
    }

    override val hasCloseButton = true
    override val hasTitle = false

    private val presenter: ColorPickerMvp.Presenter by kodein.instance()
    private val adapter = ColorPickerAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            binding.recycler.apply {
                layoutManager = GridLayoutManager(context, SPAN_COUNT)
                adapter = this@ColorPickerDialog.adapter
            }
            arguments?.let { title.set(it.title) }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, null)
        arguments?.let { presenter.createColorOptions(it.colors, it.selectedColor) }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onColorOptionsAvailable(colors: List<ColorOption>) {
        adapter.setItems(colors)
    }

    override fun onColorSelected(color: ColorOption) {
        (blocklyResultHolder.result as? ColorResult)?.confirm(color.color)
        dismissAllowingStateLoss()
    }
}
