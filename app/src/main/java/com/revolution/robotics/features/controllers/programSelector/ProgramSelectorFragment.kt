package com.revolution.robotics.features.controllers.programSelector

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentProgramSelectorBinding
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.features.controllers.programInfo.ProgramInfoDialog
import com.revolution.robotics.features.controllers.programSelector.adapter.ProgramSelectorAdapter
import com.revolution.robotics.features.controllers.programSelector.adapter.ProgramViewModel
import org.kodein.di.erased.instance

class ProgramSelectorFragment :
    BaseFragment<FragmentProgramSelectorBinding, ProgramSelectorViewModel>(R.layout.fragment_program_selector),
    ProgramSelectorMvp.View, DialogEventBus.Listener {

    override val viewModelClass = ProgramSelectorViewModel::class.java

    private val presenter: ProgramSelectorMvp.Presenter by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()
    private val storage: UserConfigurationStorage by kodein.instance()
    private val navigator: Navigator by kodein.instance()
    private val adapter = ProgramSelectorAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        dialogEventBus.register(this)
        binding?.recycler?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ProgramSelectorFragment.adapter
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        dialogEventBus.unregister(this)
        super.onDestroyView()
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
            val program = event.extras.getParcelable<UserProgram>(ProgramInfoDialog.KEY_PROGRAM)
            storage.controllerHolder?.programToBeAdded = program
            navigator.back()
        }
    }
}
