package com.revolution.robotics.features.controller.programSelector

import com.revolution.robotics.core.domain.local.Program
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.controller.programSelector.adapter.ProgramViewModel
import java.util.Date
import java.util.Random

class ProgramSelectorPresenter(private val navigator: Navigator) : ProgramSelectorMvp.Presenter {

    override var view: ProgramSelectorMvp.View? = null
    override var model: ProgramSelectorViewModel? = null

    private var programs: List<Program> = ArrayList()

    override fun register(view: ProgramSelectorMvp.View, model: ProgramSelectorViewModel?) {
        super.register(view, model)
        loadPrograms()
    }

    @Suppress("MagicNumber")
    private fun loadPrograms() {
        val random = Random(System.currentTimeMillis())
        programs = (0..100).mapIndexed { index, i ->
            Program(
                "Program $index",
                Date(System.currentTimeMillis() - (random.nextFloat() * 8640000000L).toLong())
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
                    compareBy<Program> { it.name }
                } else {
                    compareBy<Program> { it.modificationDate }
                }
            programs =
                if (model.currentOrder.second == ProgramSelectorViewModel.Order.ASCENDING) {
                    programs.sortedWith(comparator)
                } else {
                    programs.sortedWith(comparator).reversed()
                }
        }
    }

    override fun back() {
        navigator.back()
    }

    private fun createViewModels(programs: List<Program>) =
        programs.map { ProgramViewModel(it) }
}
