package com.revolution.robotics.features.controllers.buttonless

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.eventBus.dialog.DialogEvent
import com.revolution.robotics.core.eventBus.dialog.DialogEventBus
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentButtonlessProgramSelectorBinding
import com.revolution.robotics.features.controllers.buttonless.adapter.ButtonlessProgramAdapter
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import org.kodein.di.erased.instance

class ButtonlessProgramSelectorFragment :
    BaseFragment<FragmentButtonlessProgramSelectorBinding, ButtonlessProgramSelectorViewModel>
        (R.layout.fragment_buttonless_program_selector), ButtonlessProgramSelectorMvp.View, DialogEventBus.Listener {

    override val viewModelClass: Class<ButtonlessProgramSelectorViewModel> =
        ButtonlessProgramSelectorViewModel::class.java

    private val presenter: ButtonlessProgramSelectorMvp.Presenter by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()
    private val dialogEventBus: DialogEventBus by kodein.instance()
    private val navigator: Navigator by kodein.instance()
    private val adapter = ButtonlessProgramAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        dialogEventBus.register(this)
        binding?.toolbarViewModel = ButtonlessProgramSelectorToolbarViewModel(resourceResolver)
        binding?.recyclerPriority?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ButtonlessProgramSelectorFragment.adapter
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        dialogEventBus.unregister(this)
        super.onDestroyView()
    }

    override fun onDialogEvent(event: DialogEvent) {
        if (event == DialogEvent.EDIT_PROGRAM) {
            val program = event.extras.getParcelable<UserProgram>(ProgramDialog.KEY_PROGRAM)
            navigator.navigate(ButtonlessProgramSelectorFragmentDirections.toCoding(program))
        }
    }

    override fun showUserProgramDialog(userProgram: UserProgram, compatible: Boolean) {
        if (compatible) {
            ProgramDialog.Info.newInstance(userProgram).show(fragmentManager)
        } else {
            ProgramDialog.CompatibilityIssue.newInstance(userProgram).show(fragmentManager)
        }
    }
}
