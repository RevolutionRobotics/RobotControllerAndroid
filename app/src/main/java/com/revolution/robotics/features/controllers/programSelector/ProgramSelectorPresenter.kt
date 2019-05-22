package com.revolution.robotics.features.controllers.programSelector

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.controllers.ProgramOrderingHandler
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import com.revolution.robotics.features.controllers.programSelector.adapter.ProgramViewModel

class ProgramSelectorPresenter(
    private val getUserProgramsInteractor: GetUserProgramsInteractor,
    private val compatibleProgramFilterer: CompatibleProgramFilterer,
    private val storage: UserConfigurationStorage,
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
                allPrograms = result.toMutableList().apply {
                    storage.getBoundButtonPrograms().forEach { boundProgram ->
                        removeAll { it.id == boundProgram?.programId }
                    }
                }
                programs = ArrayList<UserProgram>().apply { allPrograms?.let { addAll(it) } }
                model?.programOrderingHandler?.currentOrder =
                    ProgramOrderingHandler.OrderBy.NAME to ProgramOrderingHandler.Order.ASCENDING
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
            val filteredPrograms =
                if (onlyShowCompatiblePrograms) {
                    compatibleProgramFilterer.getCompatibleProgramsOnly(allPrograms ?: emptyList())
                } else {
                    allPrograms ?: emptyList()
                }
            programs = filteredPrograms.sortedWith(model.programOrderingHandler.getComparator())
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
        view?.showDialog(ProgramDialog.Add.newInstance(userProgram))
    }

    private fun createViewModels(programs: List<UserProgram>) =
        programs.map { ProgramViewModel(it, this) }
}
