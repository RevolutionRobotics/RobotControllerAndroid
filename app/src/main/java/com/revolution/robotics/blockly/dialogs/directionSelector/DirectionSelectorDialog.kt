package com.revolution.robotics.blockly.dialogs.directionSelector

import android.os.Bundle
import android.view.View
import com.revolution.robotics.R
import com.revolution.robotics.blockly.JavascriptPromptDialog
import com.revolution.robotics.core.extensions.withArguments
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.databinding.BlocklyDialogDirectionSelectorBinding
import org.kodein.di.erased.instance

class DirectionSelectorDialog :
    JavascriptPromptDialog<BlocklyDialogDirectionSelectorBinding>(R.layout.blockly_dialog_direction_selector),
    DirectionSelectorMvp.View {

    companion object {
        private var Bundle.selectedDirectionOrdinal by BundleArgumentDelegate.Int("direction")

        fun newInstance(selectedDirection: Direction? = null) =
            DirectionSelectorDialog().withArguments { bundle ->
                selectedDirection?.ordinal?.let { bundle.selectedDirectionOrdinal = it }
            }
    }

    override val hasCloseButton = true
    override val hasTitle = true

    private val presenter: DirectionSelectorMvp.Presenter by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        titleResource.set(R.string.blockly_dialog_choose_a_direction)
        presenter.register(this, null)
        binding.viewModel = DirectionSelectorViewModel(presenter).apply {
            arguments?.selectedDirectionOrdinal?.let { selectedDirection = Direction.values()[it] }
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onDirectionSelected(direction: Direction) {
        confirmResult(direction.value)
    }
}
