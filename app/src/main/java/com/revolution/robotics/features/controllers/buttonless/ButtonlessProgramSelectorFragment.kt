package com.revolution.robotics.features.controllers.buttonless

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentButtonlessProgramSelectorBinding
import com.revolution.robotics.features.controllers.buttonless.adapter.ButtonlessProgramAdapter
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import org.kodein.di.erased.instance

class ButtonlessProgramSelectorFragment :
    BaseFragment<FragmentButtonlessProgramSelectorBinding, ButtonlessProgramSelectorViewModel>
        (R.layout.fragment_buttonless_program_selector), ButtonlessProgramSelectorMvp.View {

    override val viewModelClass: Class<ButtonlessProgramSelectorViewModel> =
        ButtonlessProgramSelectorViewModel::class.java

    private val presenter: ButtonlessProgramSelectorMvp.Presenter by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()
    private val adapter = ButtonlessProgramAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this, viewModel)
        binding?.toolbarViewModel = ButtonlessProgramSelectorToolbarViewModel(resourceResolver)
        binding?.recyclerPriority?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ButtonlessProgramSelectorFragment.adapter
        }
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun showUserProgramDialog(userProgram: UserProgram, compatible: Boolean) {
        if (compatible) {
            ProgramDialog.Info.newInstance(userProgram).show(fragmentManager)
        } else {
            ProgramDialog.CompatibilityIssue.newInstance(userProgram).show(fragmentManager)
        }
    }
}