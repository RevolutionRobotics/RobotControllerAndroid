package com.revolution.robotics.blockly.dialogs.donutSelector

import android.os.Bundle
import android.view.View
import com.revolution.robotics.R
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.BlocklyDialogDonutSelectorBinding
import com.revolution.robotics.views.DonutSelectorView
import org.kodein.di.erased.instance
import org.revolutionrobotics.robotcontroller.blocklysdk.view.result.DonutResult

class DonutSelectorDialog :
    JavascriptPromptDialog<BlocklyDialogDonutSelectorBinding>(R.layout.blockly_dialog_donut_selector),
    DonutSelectorView.SelectionListener, DonutSelectorMvp.View {

    enum class DonutSelectionType {
        SINGLE, MULTI
    }

    companion object {
        private var Bundle.selectionTypeOrdinal by BundleArgumentDelegate.Int("selection-type")
        private var Bundle.defaultSelection by BundleArgumentDelegate.String("default-selection")

        fun newInstance(selectionType: DonutSelectionType, defaultSelection: String) =
            DonutSelectorDialog().withArguments { bundle ->
                bundle.selectionTypeOrdinal = selectionType.ordinal
                bundle.defaultSelection = defaultSelection
            }
    }

    override val hasCloseButton = true
    override val hasTitle = false

    private val presenter: DonutSelectorMvp.Presenter by kodein.instance()
    var selectionType = DonutSelectionType.SINGLE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, null)
        arguments?.let { arguments ->
            selectionType = DonutSelectionType.values()[arguments.selectionTypeOrdinal]
            val defaultSelection = arguments.defaultSelection.split(",")
            binding.donut.setSelection(
                List(DonutSelectorView.OPTION_COUNT) { index ->
                    defaultSelection.contains((index + 1).toString())
                })
            binding.viewModel = DonutSelectorViewModel(selectionType == DonutSelectionType.MULTI, presenter)
            binding.donut.selectionListener = this
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onSelectionChanged(index: Int, value: Boolean) {
        if (selectionType == DonutSelectionType.SINGLE) {
            (blocklyResultHolder.result as? DonutResult)?.confirm(index + 1)
            dismissAllowingStateLoss()
        } else {
            binding.check.isChecked = binding.donut.getSelection().all { it }
        }
    }

    override fun onSelectAllClicked() {
        val isAllSelected = binding.donut.getSelection().firstOrNull { !it } == null
        binding.donut.setSelection(List(DonutSelectorView.OPTION_COUNT) { !isAllSelected })
        binding.check.isChecked = !isAllSelected
    }

    override fun onDoneButtonClicked() {
        val result = binding.donut.getSelection()
            .mapIndexed { index, value -> index + 1 to value }
            .filter { it.second }
            .map { it.first }
            .toIntArray()

        (blocklyResultHolder.result as? DonutResult)?.confirm(result)
        dismissAllowingStateLoss()
    }
}
