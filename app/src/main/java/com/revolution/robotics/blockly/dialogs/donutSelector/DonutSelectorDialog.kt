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

class DonutSelectorDialog :
    JavascriptPromptDialog<BlocklyDialogDonutSelectorBinding>(R.layout.blockly_dialog_donut_selector),
    DonutSelectorView.SelectionListener, DonutSelectorMvp.View {

    enum class DonutSelectionType {
        SINGLE, MULTI
    }

    companion object {
        private const val OPTION_COUNT = 12
        private var Bundle.selectionTypeOrdinal by BundleArgumentDelegate.Int("selection-type")
        private var Bundle.selectedByDefault by BundleArgumentDelegate.Int("selectedByDefault")

        fun newInstance() = newInstance(DonutSelectionType.MULTI, -1)

        fun newInstance(selectedByDefault: Int) = newInstance(DonutSelectionType.SINGLE, selectedByDefault)

        fun newInstance(selectionType: DonutSelectionType, selectedByDefault: Int) =
            DonutSelectorDialog().withArguments { bundle ->
                bundle.selectionTypeOrdinal = selectionType.ordinal
                bundle.selectedByDefault = selectedByDefault
            }
    }

    override val hasCloseButton = true
    override val hasTitle = false

    private val presenter: DonutSelectorMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, null)
        arguments?.let { arguments ->
            val selectionType = DonutSelectionType.values()[arguments.selectionTypeOrdinal]
            if (selectionType == DonutSelectionType.SINGLE) {
                binding.donut.selectionListener = this
                val defaultSelection = List(OPTION_COUNT) { index -> index == arguments.selectedByDefault - 1 }
                binding.donut.setSelection(defaultSelection)
            }

            binding.viewModel = DonutSelectorViewModel(selectionType == DonutSelectionType.MULTI, presenter)
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onSelectionChanged(selection: List<Boolean>) {
        val selectedIndex = selection.indexOfFirst { it }
        confirmResult("$selectedIndex")
    }

    override fun onSelectAllClicked() {
        val isAllSelected = binding.donut.getSelection().firstOrNull { !it } == null
        binding.donut.setSelection(List(OPTION_COUNT) { !isAllSelected })
        binding.check.isChecked = !isAllSelected
    }

    override fun onDoneButtonClicked() {
        confirmResult(binding.donut.getSelection().joinToString(","))
    }
}
