package com.revolution.robotics.features.controllers.programSelector

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.AssignProgramToButtonInteractor
import com.revolution.robotics.core.interactor.GetFullConfigurationInteractor
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.configure.controller.ControllerButton
import com.revolution.robotics.features.controllers.ProgramOrderingHandler
import com.revolution.robotics.features.controllers.programInfo.ProgramDialog
import com.revolution.robotics.features.controllers.programSelector.adapter.ProgramViewModel

class ProgramSelectorPresenter(
    private val getUserProgramsInteractor: GetUserProgramsInteractor,
    private val getFullConfigurationInteractor: GetFullConfigurationInteractor,
    private val compatibleProgramFilterer: CompatibleProgramFilterer,
    private val assignProgramToButtonInteractor: AssignProgramToButtonInteractor,
    private val navigator: Navigator
) : ProgramSelectorMvp.Presenter {

    companion object {
        private const val SHOW_COMPATIBLE_PROGRAMS_ONLY_BY_DEFAULT = false
    }

    override var view: ProgramSelectorMvp.View? = null
    override var model: ProgramSelectorViewModel? = null

    private var allPrograms: List<UserProgram>? = null
    private var programs: List<UserProgram> = ArrayList()
    private var onlyShowCompatiblePrograms: Boolean? = null

    private var controllerButton: ControllerButton? = null
    private var robotId: Int = -1

    override fun register(view: ProgramSelectorMvp.View, model: ProgramSelectorViewModel?) {
        super.register(view, model)
        if (onlyShowCompatiblePrograms == null) {
            setShowOnlyCompatiblePrograms(SHOW_COMPATIBLE_PROGRAMS_ONLY_BY_DEFAULT)
        }
    }

    override fun clearSelectionStates() {
        onlyShowCompatiblePrograms = null
        model?.programOrderingHandler?.currentOrder =
            ProgramOrderingHandler.OrderBy.DATE to ProgramOrderingHandler.Order.DESCENDING
    }

    override fun loadPrograms(controllerButton: ControllerButton, robotId: Int) {
        this.controllerButton = controllerButton
        this.robotId = robotId
        getFullConfigurationInteractor.robotId = robotId
        getFullConfigurationInteractor.execute { fullControllerData ->
            getUserProgramsInteractor.execute { userPrograms ->
                allPrograms = userPrograms.toMutableList().apply {
                    fullControllerData.controller?.userController?.getBoundButtonPrograms()?.forEach { boundProgram ->
                        removeAll { it.name == boundProgram.programName }
                    }
                    fullControllerData.controller?.backgroundBindings?.forEach { backgroundBinding ->
                        removeAll { it.name == backgroundBinding.programId }
                    }
                }
                programs = ArrayList<UserProgram>().apply { allPrograms?.let { addAll(it) } }
                updateOrderingAndFiltering()
            }
        }
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
        getFullConfigurationInteractor.robotId = robotId
        getFullConfigurationInteractor.execute {
            model?.let { model ->
                val filteredPrograms =
                    if (onlyShowCompatiblePrograms == true) {
                        compatibleProgramFilterer.getCompatibleProgramsOnly(
                            allPrograms ?: emptyList(),
                            it.userConfiguration
                        )
                    } else {
                        allPrograms ?: emptyList()
                    }
                programs = filteredPrograms.sortedWith(model.programOrderingHandler.getComparator())
                onProgramsChanged()
            }
        }
    }

    override fun back() {
        navigator.back()
    }

    override fun showCompatibleProgramsClicked() {
        onlyShowCompatiblePrograms?.let { onlyShowCompatiblePrograms ->
            setShowOnlyCompatiblePrograms(!onlyShowCompatiblePrograms)
            updateOrderingAndFiltering()
        }
    }

    override fun onProgramSelected(userProgram: UserProgram) {
        addProgram(userProgram)
    }

    override fun onProgramInfoClicked(userProgram: UserProgram) {
        getFullConfigurationInteractor.robotId = robotId
        getFullConfigurationInteractor.execute {
            if (compatibleProgramFilterer.isProgramCompatible(userProgram, it.userConfiguration)) {
                view?.showDialog(ProgramDialog.Add.newInstance(userProgram))
            } else {
                view?.showDialog(ProgramDialog.CompatibilityIssue.newInstance(userProgram))
            }
        }
    }

    override fun onEditProgramClicked(userProgram: UserProgram) {
        navigator.navigate(ProgramSelectorFragmentDirections.toCoding(userProgram, true))
    }

    override fun addProgram(userProgram: UserProgram) {
        assignProgramToButtonInteractor.robotId = robotId
        assignProgramToButtonInteractor.userProgram = userProgram
        assignProgramToButtonInteractor.button = controllerButton
        assignProgramToButtonInteractor.execute {
            navigator.back()
        }
    }

    private fun onProgramsChanged() {
        model?.isEmpty?.set(programs.isEmpty())
        model?.emptyText?.set(
            if (onlyShowCompatiblePrograms == true) {
                R.string.program_selector_empty_compatible
            } else {
                R.string.program_selector_empty
            }
        )
        view?.onProgramsChanged(programs.map { ProgramViewModel(it, this) })
    }
}
