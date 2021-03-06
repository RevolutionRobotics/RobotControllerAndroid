package com.revolution.robotics.blockly.dialogs.variableOptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolution.robotics.R
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.blockly.dialogs.variableOptions.adapter.VariableAdapters
import com.revolution.robotics.blockly.dialogs.variableOptions.adapter.VariableViewModel
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.BlocklyDialogVariableOptionsBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogButtonHelper
import org.kodein.di.erased.instance
import org.revolutionrobotics.blockly.android.BlocklyVariable
import org.revolutionrobotics.blockly.android.view.result.VariableResult

class VariableOptionsDialog :
    JavascriptPromptDialog<BlocklyDialogVariableOptionsBinding>(R.layout.blockly_dialog_variable_options),
    VariableOptionsMvp.View {

    companion object {
        private var Bundle.title by BundleArgumentDelegate.String("title")
        private var Bundle.selectedVariable by BundleArgumentDelegate.Parcelable<BlocklyVariable>("selected-variable")
        private var Bundle.variables by BundleArgumentDelegate.Parcelable<BlocklyVariableList>("variables")

        fun newInstance(title: String, defaultValue: BlocklyVariable?, variables: List<BlocklyVariable>) =
            VariableOptionsDialog().withArguments { bundle ->
                bundle.title = title
                defaultValue?.let { bundle.selectedVariable = it }
                bundle.variables = BlocklyVariableList().apply { addAll(variables) }
            }
    }

    override val hasCloseButton = true
    override val hasTitle = true

    private val dialogButtonHelper = DialogButtonHelper()
    private val adapter = VariableAdapters()
    private val presenter: VariableOptionsMvp.Presenter by kodein.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            dialogButtonHelper.createButtons(binding.buttonContainer, setOf(
                DialogButton(R.string.dialog_variable_options_delete, R.drawable.ic_delete) {
                    val selectedVariable = arguments?.selectedVariable
                    if (selectedVariable != null) {
                        (blocklyResultHolder.result as? VariableResult)?.confirmDeleteVariable(selectedVariable)
                    }
                    dismissAllowingStateLoss()
                }
            ))
            title.set(arguments?.title)
            binding.variables.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@VariableOptionsDialog.adapter
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, null)
        val selectedKey = arguments?.selectedVariable?.key
        arguments?.variables?.map { VariableViewModel(it, it.key == selectedKey, presenter) }?.let {
            adapter.setItems(it)
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onVariableSelected(variable: BlocklyVariable) {
        (blocklyResultHolder.result as? VariableResult)?.confirmChangeVariable(variable)
        dismissAllowingStateLoss()
    }
}
