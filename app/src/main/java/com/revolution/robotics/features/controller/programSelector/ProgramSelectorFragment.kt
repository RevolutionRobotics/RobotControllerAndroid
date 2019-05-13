package com.revolution.robotics.features.controller.programSelector

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.kodein.utils.ResourceResolver
import com.revolution.robotics.databinding.FragmentProgramSelectorBinding
import org.kodein.di.erased.instance

class ProgramSelectorFragment :
    BaseFragment<FragmentProgramSelectorBinding, ProgramSelectorViewModel>(R.layout.fragment_program_selector) {

    override val viewModelClass = ProgramSelectorViewModel::class.java

    private val resourceResolver: ResourceResolver by kodein.instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.toolbarViewModel = ProgramSelectorToolbarViewModel(resourceResolver)
    }

}
