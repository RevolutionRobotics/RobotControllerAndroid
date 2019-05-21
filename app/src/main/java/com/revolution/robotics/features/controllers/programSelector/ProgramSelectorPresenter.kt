package com.revolution.robotics.features.controllers.programSelector

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.controllers.programInfo.ProgramInfoDialog
import com.revolution.robotics.features.controllers.programSelector.adapter.ProgramViewModel

class ProgramSelectorPresenter(
    private val getUserProgramsInteractor: GetUserProgramsInteractor,
    private val compatibleProgramFilterer: CompatibleProgramFilterer,
    private val navigator: Navigator
) : ProgramSelectorMvp.Presenter {

    override var view: ProgramSelectorMvp.View? = null
    override var model: ProgramSelectorViewModel? = null

    private var allPrograms: List<UserProgram>? = null
    private var programs: List<UserProgram> = ArrayList()
    private var onlyShowCompatiblePrograms = false

    override fun register(view: ProgramSelectorMvp.View, model: ProgramSelectorViewModel?) {
        super.register(view, model)
        setShowOnlyCompatiblePrograms(false)
        loadPrograms()
    }

    private fun loadPrograms() {
        getUserProgramsInteractor.execute(
            onResponse = { result ->
                allPrograms = result
                programs = result
                model?.currentOrder = ProgramSelectorViewModel.OrderBy.NAME to ProgramSelectorViewModel.Order.ASCENDING
                orderAndFilterPrograms()
                view?.onProgramsChanged(createViewModels(programs))
            },
            onError = {
                // TODO add error handling
            }
        )
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

    override fun updateOrderingAndFiltering() {
        orderAndFilterPrograms()
        view?.onProgramsChanged(createViewModels(programs))
    }

    private fun orderAndFilterPrograms() {
        model?.let { model ->
            val comparator =
                if (model.currentOrder.first == ProgramSelectorViewModel.OrderBy.NAME) {
                    compareBy<UserProgram> { it.name }
                } else {
                    compareBy<UserProgram> { it.lastModified }
                }
            val filteredPrograms =
                if (onlyShowCompatiblePrograms) {
                    compatibleProgramFilterer.getCompatibleProgramsOnly(allPrograms ?: emptyList())
                } else {
                    allPrograms ?: emptyList()
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
        updateOrderingAndFiltering()
    }

    override fun onProgramSelected(userProgram: UserProgram) {
        view?.showDialog(ProgramInfoDialog.Add.newInstance(userProgram))
    }

    private fun createViewModels(programs: List<UserProgram>) =
        programs.map { ProgramViewModel(it, this) }
}
