package com.revolution.robotics.blockly.dialogs.valueOptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolution.robotics.R
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.blockly.dialogs.valueOptions.adapter.ValueAdapter
import com.revolution.robotics.blockly.dialogs.valueOptions.adapter.ValueViewModel
import com.revolution.robotics.blockly.dialogs.variableOptions.adapter.VariableAdapters
import com.revolution.robotics.blockly.dialogs.variableOptions.adapter.VariableViewModel
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.BlocklyDialogValueOptionsBinding
import com.revolution.robotics.databinding.BlocklyDialogVariableOptionsBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogButtonHelper
import org.kodein.di.erased.instance
import org.revolutionrobotics.blockly.android.BlocklyVariable
import org.revolutionrobotics.blockly.android.view.result.OptionResult
import org.revolutionrobotics.blockly.android.view.result.TextResult
import org.revolutionrobotics.blockly.android.view.result.VariableResult

class ValueOptionsDialog :
    JavascriptPromptDialog<BlocklyDialogValueOptionsBinding>(R.layout.blockly_dialog_value_options) {

    companion object {
        private var Bundle.title by BundleArgumentDelegate.String("title")
        private var Bundle.selectedValue by BundleArgumentDelegate.String("selected-value")
        private var Bundle.values by BundleArgumentDelegate.StringArray("values")
        private var Bundle.errorMessageResId by BundleArgumentDelegate.Int("error-message")

        fun newInstance(title: String, defaultValue: String?, values: List<String>, @StringRes errorMessageResId: Int) =
            ValueOptionsDialog().withArguments { bundle ->
                bundle.title = title
                defaultValue?.let { bundle.selectedValue = it }
                bundle.values = values.toTypedArray()
                bundle.errorMessageResId = errorMessageResId
            }
    }

    override val hasCloseButton = true
    override val hasTitle = true

    private val adapter = ValueAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            title.set(arguments?.title)
            binding.values.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@ValueOptionsDialog.adapter
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val selectedKey = arguments?.selectedValue
        arguments?.values?.map { ValueViewModel(it, it == selectedKey, this) }?.let { items ->
            if (items.isNotEmpty()) {
                adapter.setItems(items)
            } else {
                arguments?.errorMessageResId?.let { binding.error.setText(it) }
            }
        }
    }

    fun onValueSelected(value: String) {
        if (blocklyResultHolder.result is TextResult) {
            (blocklyResultHolder.result as? TextResult)?.confirm(value)
        } else if (blocklyResultHolder.result is OptionResult) {
            (blocklyResultHolder.result as? OptionResult)?.confirm(value)
        }
        dismissAllowingStateLoss()
    }
}
