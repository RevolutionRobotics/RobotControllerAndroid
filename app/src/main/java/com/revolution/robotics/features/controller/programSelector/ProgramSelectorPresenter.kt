package com.revolution.robotics.features.controller.programSelector

import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.controller.programSelector.adapter.ProgramViewModel
import java.util.Random

class ProgramSelectorPresenter(private val navigator: Navigator) : ProgramSelectorMvp.Presenter {

    override var view: ProgramSelectorMvp.View? = null
    override var model: ProgramSelectorViewModel? = null

    private var programs: List<UserProgram> = ArrayList()

    override fun register(view: ProgramSelectorMvp.View, model: ProgramSelectorViewModel?) {
        super.register(view, model)
        loadPrograms()
    }

    @Suppress("MagicNumber")
    private fun loadPrograms() {
        val random = Random(System.currentTimeMillis())
        programs = (0..100).mapIndexed { index, i ->
            UserProgram(
                name = "Program $index",
                lastModified = System.currentTimeMillis() - (random.nextFloat() * 8640000000L).toLong()
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

    private fun createViewModels(programs: List<UserProgram>) =
        programs.map { ProgramViewModel(it) }
}
