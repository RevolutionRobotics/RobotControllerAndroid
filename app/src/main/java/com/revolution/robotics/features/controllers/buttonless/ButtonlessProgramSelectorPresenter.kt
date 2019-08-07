package com.revolution.robotics.features.controllers.buttonless

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.controllers.ProgramOrderingHandler
import com.revolution.robotics.features.controllers.buttonless.adapter.ButtonlessProgramViewModel
import kotlin.collections.set

@Suppress("TooManyFunctions")
class ButtonlessProgramSelectorPresenter(
    private val getUserProgramsInteractor: GetUserProgramsInteractor,
    private val compatibleProgramFilterer: CompatibleProgramFilterer,
    private val navigator: Navigator,
    private val userConfigurationStorage: UserConfigurationStorage
) : ButtonlessProgramSelectorMvp.Presenter {

    companion object {
        private const val SHOW_COMPATIBLE_PROGRAMS_ONLY_BY_DEFAULT = true
    }

    override var view: ButtonlessProgramSelectorMvp.View? = null
    override var model: ButtonlessProgramSelectorViewModel? = null

    private var allPrograms: List<ButtonlessProgramViewModel>? = null
    private var programs: MutableList<ButtonlessProgramViewModel> = mutableListOf()
    private var onlyShowCompatiblePrograms: Boolean? = null

    override fun register(view: ButtonlessProgramSelectorMvp.View, model: ButtonlessProgramSelectorViewModel?) {
        super.register(view, model)
        if (onlyShowCompatiblePrograms == null) {
            setShowOnlyCompatiblePrograms(SHOW_COMPATIBLE_PROGRAMS_ONLY_BY_DEFAULT)
        }
        loadPrograms()
    }

    override fun clearSelections() {
        onlyShowCompatiblePrograms = null
        model?.programOrderingHandler?.currentOrder =
            ProgramOrderingHandler.OrderBy.DATE to ProgramOrderingHandler.Order.DESCENDING
        allPrograms = null
        programs.clear()
    }

    private fun loadPrograms() {
        if (allPrograms != null) {
            return
        }

        getUserProgramsInteractor.execute { result ->
            val boundPrograms = userConfigurationStorage.getBoundButtonPrograms()
            allPrograms = result.filter { program ->
                boundPrograms.find { it.programName == program.name } == null
            }.map { userProgram ->
                ButtonlessProgramViewModel(userProgram, this).apply {
                    selected.set(userConfigurationStorage.controllerHolder?.backgroundBindings?.find
                    { it.programId == userProgram.name } != null && compatibleProgramFilterer.isProgramCompatible(
                        userProgram,
                        userConfigurationStorage.userConfiguration
                    ))
                    enabled.set(compatibleProgramFilterer.isProgramCompatible(userProgram, userConfigurationStorage.userConfiguration))
                }
            }.apply {
                programs.clear()
                programs.addAll(this)
            }
            orderAndFilterPrograms()
            setOrderingIcons()
        }
    }

    private fun setShowOnlyCompatiblePrograms(onlyCompatible: Boolean) {
        onlyShowCompatiblePrograms = onlyCompatible
        if (onlyCompatible) {
            model?.showCompatibleButtonText?.set(R.string.buttonless_program_show_all_programs)
            model?.showCompatibleButtonIcon?.set(R.drawable.ic_compatible_selected)
        } else {
            model?.showCompatibleButtonText?.set(R.string.buttonless_program_show_compatible_programs)
            model?.showCompatibleButtonIcon?.set(R.drawable.ic_compatible)
        }
    }

    override fun updateOrderingAndFiltering() {
        orderAndFilterPrograms()
        setOrderingIcons()
    }

    private fun setOrderingIcons() {
        model?.apply {
            if (programOrderingHandler.currentOrder.first == ProgramOrderingHandler.OrderBy.DATE) {
                nameOrderIconColor.set(R.color.white)
                dateOrderIconColor.set(R.color.robotics_red)

                if (programOrderingHandler.currentOrder.second == ProgramOrderingHandler.Order.ASCENDING) {
                    dateOrderIcon.set(R.drawable.ic_sort_date_up)
                } else {
                    dateOrderIcon.set(R.drawable.sort_date_down)
                }
                alphabeticalOderIcon.set(R.drawable.sort_name_up)
            } else {
                nameOrderIconColor.set(R.color.robotics_red)
                dateOrderIconColor.set(R.color.white)

                if (programOrderingHandler.currentOrder.second == ProgramOrderingHandler.Order.ASCENDING) {
                    alphabeticalOderIcon.set(R.drawable.sort_name_up)
                } else {
                    alphabeticalOderIcon.set(R.drawable.sort_name_down_red)
                }

                dateOrderIcon.set(R.drawable.sort_date_down)
            }
        }
    }

    private fun orderAndFilterPrograms() {
        model?.let { model ->
            val filteredPrograms =
                if (onlyShowCompatiblePrograms == true) {
                    programs.filter { compatibleProgramFilterer.isProgramCompatible(it.program, userConfigurationStorage.userConfiguration) }
                } else {
                    allPrograms ?: emptyList()
                }
            programs = filteredPrograms.sortedWith(Comparator { o1, o2 ->
                model.programOrderingHandler.getComparator().compare(o1.program, o2.program)
            }).toMutableList()
            model.items.value = programs
            model.isEmpty.set(programs.isEmpty())
            model.emptyText.set(
                if (onlyShowCompatiblePrograms == true) {
                    R.string.buttonless_program_selector_compatible_empty
                } else {
                    R.string.buttonless_program_selector_empty
                }
            )
        }
    }

    override fun onShowCompatibleProgramsButtonClicked() {
        onlyShowCompatiblePrograms?.let { onlyShowCompatiblePrograms ->
            setShowOnlyCompatiblePrograms(!onlyShowCompatiblePrograms)
            updateOrderingAndFiltering()
        }
    }

    override fun save() {
        val priorities = HashMap<String, Int>()
        programs.forEach { viewModel ->
            if (viewModel.selected.get()) {
                priorities[viewModel.program.name] = userConfigurationStorage.getPriority(viewModel.program.name)
            }
        }

        userConfigurationStorage.clearBackgroundPrograms()
        programs.forEach { viewModel ->
            if (viewModel.selected.get()) {
                val priority = priorities[viewModel.program.name]
                userConfigurationStorage.addBackgroundProgram(
                    viewModel.program,
                    if (priority == -1) 0 else priority ?: 0
                )
            }
        }
    }

    override fun onSelectAllClicked(checked: Boolean) {
        programs.forEach {
            it.selected.set(compatibleProgramFilterer.isProgramCompatible(it.program, userConfigurationStorage.userConfiguration) && checked)
        }
    }

    override fun onProgramSelected(viewModel: ButtonlessProgramViewModel) {
        if (compatibleProgramFilterer.isProgramCompatible(viewModel.program, userConfigurationStorage.userConfiguration)) {
            viewModel.selected.set(!viewModel.selected.get())
        }
    }

    override fun onInfoButtonClicked(userProgram: UserProgram) {
        view?.showUserProgramDialog(userProgram, compatibleProgramFilterer.isProgramCompatible(userProgram, userConfigurationStorage.userConfiguration))
    }

    override fun onProgramEdited(userProgram: UserProgram) {
        val viewModel = programs.find { it.program == userProgram }
        if (viewModel?.selected?.get() == true && !compatibleProgramFilterer.isProgramCompatible(userProgram, userConfigurationStorage.userConfiguration)) {
            viewModel.selected.set(false)
        }
    }
}
