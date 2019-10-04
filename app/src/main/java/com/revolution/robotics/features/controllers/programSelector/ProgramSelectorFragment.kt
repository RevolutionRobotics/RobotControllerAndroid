package com.revolution.robotics.features.controllers.programSelector

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.utils.BundleArgumentDelegate
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentProgramSelectorBinding
import com.revolution.robotics.features.configure.controller.ControllerButton
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import com.revolution.robotics.features.controllers.programSelector.adapter.ProgramSelectorAdapter
import com.revolution.robotics.features.controllers.programSelector.adapter.ProgramViewModel
import org.kodein.di.erased.instance

class ProgramSelectorFragment :
    BaseFragment<FragmentProgramSelectorBinding, ProgramSelectorViewModel>(R.layout.fragment_program_selector),
    ProgramSelectorMvp.View, DialogEventBus.Listener {

    companion object {

        private var Bundle.configId by BundleArgumentDelegate.Int("configId")
        private var Bundle.selectedButton by BundleArgumentDelegate.String("selectedButton")
    }
    override val viewModelClass = ProgramSelectorViewModel::class.java

    private val presenter: ProgramSelectorMvp.Presenter by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()
    private val navigator: Navigator by kodein.instance()
    private val adapter = ProgramSelectorAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        dialogEventBus.register(this)
        binding?.recycler?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ProgramSelectorFragment.adapter
        }
        arguments?.configId?.let { configId -> arguments?.selectedButton?.let { button -> presenter.loadPrograms(ControllerButton.valueOf(button), configId) } }
    }

    override fun onDestroyView() {
        presenter.unregister()
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearSelectionStates()
    }

    override fun onProgramsChanged(programs: List<ProgramViewModel>) {
        adapter.setItems(programs)
        binding?.apply {
            viewModel = this@ProgramSelectorFragment.viewModel
            executePendingBindings()
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        if (event == DialogEvent.ADD_PROGRAM) {
            presenter.addProgram(event.program())
        } else if (event == DialogEvent.EDIT_PROGRAM) {
            navigator.navigate(ProgramSelectorFragmentDirections.toCoding(event.program(), true))
        }
    }

    private fun DialogEvent.program() =
        extras.getParcelable<UserProgram>(ProgramDialog.KEY_PROGRAM)
}
