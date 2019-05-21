package com.revolution.robotics.features.controllers.buttonless

import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetUserProgramsInteractor
import com.revolution.robotics.features.configure.controller.CompatibleProgramFilterer
import com.revolution.robotics.features.controllers.ProgramOrderingHandler
import com.revolution.robotics.features.controllers.buttonless.adapter.ButtonlessProgramViewModel

class ButtonlessProgramSelectorPresenter(
    private val getUserProgramsInteractor: GetUserProgramsInteractor,
    private val compatibleProgramFilterer: CompatibleProgramFilterer
) : ButtonlessProgramSelectorMvp.Presenter {

    override var view: ButtonlessProgramSelectorMvp.View? = null
    override var model: ButtonlessProgramSelectorViewModel? = null

    private var allPrograms: List<UserProgram>? = null
    private var programs: List<UserProgram> = ArrayList()
    private var onlyShowCompatiblePrograms = false

    override fun register(view: ButtonlessProgramSelectorMvp.View, model: ButtonlessProgramSelectorViewModel?) {
        super.register(view, model)
        setShowOnlyCompatiblePrograms(false)
        loadPrograms()
    }

    private fun loadPrograms() {
        getUserProgramsInteractor.execute(
            onResponse = { result ->
                allPrograms = result
                programs = result
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
                // Red color for date
                if (programOrderingHandler.currentOrder.second == ProgramOrderingHandler.Order.ASCENDING) {
                    dateOrderIcon.set(R.drawable.sort_date_down)
                } else {
                    dateOrderIcon.set(R.drawable.sort_date_down)
                }

                // White color for name order
                alphabeticalOderIcon.set(R.drawable.sort_name_down_red)
            } else {
                // Red color for name
                if (programOrderingHandler.currentOrder.second == ProgramOrderingHandler.Order.ASCENDING) {
                    alphabeticalOderIcon.set(R.drawable.sort_name_up)
                } else {
                    alphabeticalOderIcon.set(R.drawable.sort_name_down_red)
                }

                // White color for name order
                dateOrderIcon.set(R.drawable.sort_date_down)
            }
        }
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
            model.items.value = programs.map {
                ButtonlessProgramViewModel(it, this)
            }
        }
    }

    override fun onNextButtonClicked() {

    }

    override fun onSelectAllClicked(checked: Boolean) {

    }

    override fun onShowCompatibleProgramsButtonClicked() {

    }

    override fun onProgramSelected(userProgram: UserProgram) {
        
    }

    override fun onInfoButtonClicked(userProgram: UserProgram) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}