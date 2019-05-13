package com.revolution.robotics.features.controller.programSelector

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.Program
import com.revolution.robotics.databinding.FragmentProgramSelectorBinding
import com.revolution.robotics.features.controller.programSelector.adapter.ProgramSelectorAdapter
import com.revolution.robotics.features.controller.programSelector.adapter.ProgramViewModel
import org.kodein.di.erased.instance

class ProgramSelectorFragment :
    BaseFragment<FragmentProgramSelectorBinding, ProgramSelectorViewModel>(R.layout.fragment_program_selector),
    ProgramSelectorMvp.View {

    override val viewModelClass = ProgramSelectorViewModel::class.java

    private val presenter: ProgramSelectorMvp.Presenter by kodein.instance()
    private val adapter = ProgramSelectorAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.register(this, viewModel)
        binding?.recycler?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ProgramSelectorFragment.adapter
        }
    }

    override fun onProgramsChanged(programs: List<ProgramViewModel>) {
        adapter.setItems(programs)
    }
}
