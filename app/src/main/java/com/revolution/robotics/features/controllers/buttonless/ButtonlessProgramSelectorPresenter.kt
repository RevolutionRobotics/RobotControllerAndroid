package com.revolution.robotics.features.controllers.buttonless

import android.util.SparseIntArray
import androidx.core.util.set
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.controllers.ProgramOrderingHandler
import com.revolution.robotics.features.controllers.buttonless.adapter.ButtonlessProgramViewModel

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
    private var onlyShowCompatiblePrograms = SHOW_COMPATIBLE_PROGRAMS_ONLY_BY_DEFAULT

    override fun register(view: ButtonlessProgramSelectorMvp.View, model: ButtonlessProgramSelectorViewModel?) {
        super.register(view, model)
        setShowOnlyCompatiblePrograms(SHOW_COMPATIBLE_PROGRAMS_ONLY_BY_DEFAULT)
        loadPrograms()
    }

    private fun loadPrograms() {
        getUserProgramsInteractor.execute(
            onResponse = { result ->
                val boundPrograms = userConfigurationStorage.getBoundButtonPrograms()
                allPrograms = result.filter { program ->
                    boundPrograms.find { it.programId == program.id } == null
                }.map { userProgram ->
                    ButtonlessProgramViewModel(userProgram, this).apply {
                        selected.set(userConfigurationStorage.controllerHolder?.backgroundBindings?.find
                        { it.programId == userProgram.id } != null && compatibleProgramFilterer.isProgramCompatible(
                            userProgram
                        ))
                        enabled.set(compatibleProgramFilterer.isProgramCompatible(userProgram))
                    }
                }.apply {
                    programs.clear()
                    programs.addAll(this)
                }
                model?.programOrderingHandler?.currentOrder =
                    ProgramOrderingHandler.OrderBy.DATE to ProgramOrderingHandler.Order.ASCENDING
                orderAndFilterPrograms()
                setOrderingIcons()
            },
            onError = {
                // TODO add error handling
            }
        )
    }

    private fun setShowOnlyCompatiblePrograms(onlyCompatible: Boolean) {
        onlyShowCompatiblePrograms = onlyCompatible
        if (onlyCompatible) {
            model?.showCompatibleButtonText?.set(R.string.buttonless_program_show_compatible_programs)
            model?.showCompatibleButtonIcon?.set(R.drawable.ic_compatible)
        } else {
            model?.showCompatibleButtonText?.set(R.string.buttonless_program_show_all_programs)
            model?.showCompatibleButtonIcon?.set(R.drawable.ic_compatible_selected)
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
                    dateOrderIcon.set(R.drawable.sort_date_down)
                } else {
                    dateOrderIcon.set(R.drawable.ic_sort_date_up)
                }
                alphabeticalOderIcon.set(R.drawable.sort_name_down_red)
            } else {
                nameOrderIconColor.set(R.color.robotics_red)
                dateOrderIconColor.set(R.color.white)

                if (programOrderingHandler.currentOrder.second == ProgramOrderingHandler.Order.ASCENDING) {
                    alphabeticalOderIcon.set(R.drawable.sort_name_down_red)
                } else {
                    alphabeticalOderIcon.set(R.drawable.sort_name_up)
                }

                dateOrderIcon.set(R.drawable.ic_sort_date_up)
            }
        }
    }

    private fun orderAndFilterPrograms() {
        model?.let { model ->
            val filteredPrograms =
                if (onlyShowCompatiblePrograms) {
                    programs.filter { compatibleProgramFilterer.isProgramCompatible(it.program) }
                } else {
                    allPrograms ?: emptyList()
                }
            programs = filteredPrograms.sortedWith(Comparator { o1, o2 ->
                model.programOrderingHandler.getComparator().compare(o1.program, o2.program)
            }).toMutableList()
            model.items.value = programs
            model.isEmpty.set(programs.isEmpty())
        }
    }

    override fun onShowCompatibleProgramsButtonClicked() {
        setShowOnlyCompatiblePrograms(!onlyShowCompatiblePrograms)
        updateOrderingAndFiltering()
    }

    override fun onNextButtonClicked() {
        val priorities = SparseIntArray()
        programs.forEach { viewModel ->
            if (viewModel.selected.get()) {
                priorities[viewModel.program.id] = userConfigurationStorage.getPriority(viewModel.program.id)
            }
        }

        userConfigurationStorage.clearBackgroundPrograms()
        programs.forEach { viewModel ->
            if (viewModel.selected.get()) {
                val priority = priorities[viewModel.program.id]
                userConfigurationStorage.addBackgroundProgram(viewModel.program, if (priority == -1) 0 else priority)
            }
        }

        navigator.navigate(ButtonlessProgramSelectorFragmentDirections.toProgramPriorityFragment())
    }

    override fun onSelectAllClicked(checked: Boolean) {
        programs.forEach {
            it.selected.set(compatibleProgramFilterer.isProgramCompatible(it.program) && checked)
        }
    }

    override fun onProgramSelected(viewModel: ButtonlessProgramViewModel) {
        if (compatibleProgramFilterer.isProgramCompatible(viewModel.program)) {
            viewModel.selected.set(!viewModel.selected.get())
        }
    }

    override fun onInfoButtonClicked(userProgram: UserProgram) {
        view?.showUserProgramDialog(userProgram, compatibleProgramFilterer.isProgramCompatible(userProgram))
    }
}
