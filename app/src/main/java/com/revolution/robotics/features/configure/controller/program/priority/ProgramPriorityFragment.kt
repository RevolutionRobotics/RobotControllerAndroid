package com.revolution.robotics.features.configure.controller.program.priority

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentProgramPriorityBinding
import org.kodein.di.erased.instance

class ProgramPriorityFragment :
    BaseFragment<FragmentProgramPriorityBinding, ProgramPriorityViewModel>(R.layout.fragment_program_priority),
    ProgramPriorityMvp.View {

    override val viewModelClass: Class<ProgramPriorityViewModel> = ProgramPriorityViewModel::class.java
    private val presenter: ProgramPriorityMvp.Presenter by kodein.instance()
    private val resourceResolver: ResourceResolver by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbarViewModel = ProgramPriorityToolbarViewModel(resourceResolver)
        presenter.register(this, viewModel)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }
}
