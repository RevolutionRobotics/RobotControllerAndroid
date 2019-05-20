package com.revolution.robotics.features.controllers.programSelector

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.controllers.programSelector.adapter.ProgramViewModel
import java.util.Random

class ProgramSelectorPresenter(
    private val navigator: Navigator,
    private val compatibleProgramFilterer: CompatibleProgramFilterer
) : ProgramSelectorMvp.Presenter {

    override var view: ProgramSelectorMvp.View? = null
    override var model: ProgramSelectorViewModel? = null

    private var defaultPrograms: List<UserProgram>? = null
    private var programs: List<UserProgram> = ArrayList()
    private var onlyShowCompatiblePrograms = false

    override fun register(view: ProgramSelectorMvp.View, model: ProgramSelectorViewModel?) {
        super.register(view, model)
        setShowOnlyCompatiblePrograms(false)
        loadPrograms()
    }

    private fun setShowOnlyCompatiblePrograms(onlyCompatible: Boolean) {
        onlyShowCompatiblePrograms = onlyCompatible
        if (onlyCompatible) {
            model?.filterText?.set(R.string.program_selector_show_all_programs)
            model?.filterDrawable?.set(R.drawable.ic_compatible_selected)
        } else {
            model?.filterText?.set(R.string.program_selector_show_compatible_programs)
            model?.filterDrawable?.set(R.drawable.ic_compatible)
        }
    }

    @Suppress("MagicNumber")
    private fun loadPrograms() {
        val random = Random(System.currentTimeMillis())
        defaultPrograms = (0..100).mapIndexed { index, i ->
            UserProgram(
                name = "Program $index",
                lastModified = System.currentTimeMillis() - (random.nextFloat() * 8640000000L).toLong(),
                variables = listOf("sensor1", "sensor2", "motor1", "motor2")
            )
        }
        model?.currentOrder = ProgramSelectorViewModel.OrderBy.NAME to ProgramSelectorViewModel.Order.ASCENDING
        orderPrograms()
        view?.onProgramsChanged(createViewModels(programs))
    }

    override fun updateOrdering() {
        orderPrograms()
        view?.onProgramsChanged(createViewModels(programs))
    }

    private fun orderPrograms() {
        model?.let { model ->
            val comparator =
                if (model.currentOrder.first == ProgramSelectorViewModel.OrderBy.NAME) {
                    compareBy<UserProgram> { it.name }
                } else {
                    compareBy<UserProgram> { it.lastModified }
                }
            val filteredPrograms =
                if (onlyShowCompatiblePrograms) {
                    compatibleProgramFilterer.getCompatibleProgramsOnly(defaultPrograms ?: emptyList())
                } else {
                    defaultPrograms?.toList() ?: emptyList()
                }
            programs =
                if (model.currentOrder.second == ProgramSelectorViewModel.Order.ASCENDING) {
                    filteredPrograms.sortedWith(comparator)
                } else {
                    filteredPrograms.sortedWith(comparator).reversed()
                }
        }
    }

    override fun back() {
        navigator.back()
    }

    override fun showCompatibleProgramsClicked() {
        setShowOnlyCompatiblePrograms(!onlyShowCompatiblePrograms)
        updateOrdering()
    }

    private fun createViewModels(programs: List<UserProgram>) =
        programs.map { ProgramViewModel(it) }
}
