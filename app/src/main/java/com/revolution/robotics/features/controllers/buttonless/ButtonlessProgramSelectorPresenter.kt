package com.revolution.robotics.features.controllers.buttonless

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserBackgroundProgramBinding
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.configure.UserConfigurationStorage
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.controllers.ProgramOrderingHandler
import com.revolution.robotics.features.controllers.buttonless.adapter.ButtonlessProgramViewModel

class ButtonlessProgramSelectorPresenter(
    private val getUserProgramsInteractor: GetUserProgramsInteractor,
    private val compatibleProgramFilterer: CompatibleProgramFilterer,
    private val navigator: Navigator,
    private val userConfigurationStorage: UserConfigurationStorage
) : ButtonlessProgramSelectorMvp.Presenter {

    override var view: ButtonlessProgramSelectorMvp.View? = null
    override var model: ButtonlessProgramSelectorViewModel? = null

    private var allPrograms: List<ButtonlessProgramViewModel>? = null
    private var programs: MutableList<ButtonlessProgramViewModel> = mutableListOf()
    private var onlyShowCompatiblePrograms = false

    override fun register(view: ButtonlessProgramSelectorMvp.View, model: ButtonlessProgramSelectorViewModel?) {
        super.register(view, model)
        setShowOnlyCompatiblePrograms(false)
        loadPrograms()
    }

    private fun loadPrograms() {
        getUserProgramsInteractor.execute(
            onResponse = { result ->
                allPrograms = result.map {userProgram ->
                    ButtonlessProgramViewModel(userProgram, this).apply {
                        enabled.set(compatibleProgramFilterer.isProgramCompatible(userProgram))
                    }
                }.apply {
                    programs.clear()
                    programs.addAll(this)
                }
                model?.programOrderingHandler?.currentOrder =
                    ProgramOrderingHandler.OrderBy.NAME to ProgramOrderingHandler.Order.ASCENDING
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
                    dateOrderIcon.set(R.drawable.sort_date_down)
                } else {
                    dateOrderIcon.set(R.drawable.sort_date_down)
                }
                alphabeticalOderIcon.set(R.drawable.sort_name_down_red)
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
                if (onlyShowCompatiblePrograms) {
                    programs.filter { compatibleProgramFilterer.isProgramCompatible(it.program) }
                } else {
                    allPrograms ?: emptyList()
                }
            programs = filteredPrograms.sortedWith(Comparator { o1, o2 ->
                model.programOrderingHandler.getComparator().compare(o1.program, o2.program)
            }).toMutableList()
            model.items.value = programs
        }
    }

    override fun onShowCompatibleProgramsButtonClicked() {
        setShowOnlyCompatiblePrograms(!onlyShowCompatiblePrograms)
        updateOrderingAndFiltering()
    }

    override fun onNextButtonClicked() {
        userConfigurationStorage.controllerHolder?.backgroundBindings?.let { backgroundBindings ->
            backgroundBindings.clear()
            programs.forEach { viewModel ->
                if (viewModel.selected.get()) {
                    backgroundBindings.add(
                        UserBackgroundProgramBinding(
                            0,
                            userConfigurationStorage.controllerHolder?.userController?.id ?: 0,
                            viewModel.program.id,
                            0
                        )
                    )
                    userConfigurationStorage.controllerHolder?.programs?.put(viewModel.program.id, viewModel.program)
                }
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
        view?.showUserProgramDialog(userProgram)
    }
}