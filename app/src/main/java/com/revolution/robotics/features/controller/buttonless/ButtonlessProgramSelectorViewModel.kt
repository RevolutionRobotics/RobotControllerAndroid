package com.revolution.robotics.features.controller.buttonless

import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.features.controller.ProgramOrderingHandler

class ButtonlessProgramSelectorViewModel(private val presenter: ButtonlessProgramSelectorMvp.Presenter) : ViewModel() {

    val dateOrderIcon = ObservableInt(R.drawable.sort_date_down)
    val alphabeticalOderIcon = ObservableInt(R.drawable.sort_name_up)
    val showCompatibleButtonText = ObservableInt(R.string.buttonless_program_show_compatible_programs)
    val showCompatibleButtonIcon = ObservableInt(R.drawable.ic_compatible)

    private val programOrderingHandler = ProgramOrderingHandler()

    fun onNextButtonClicked() {
        presenter.onNextButtonClicked()
    }

    fun onDateOrderClicked() {
        programOrderingHandler.onOrderByDateClicked()
        presenter.updateOrdering()
    }

    fun onAlphabeticalOrderIconClicked() {
        programOrderingHandler.onOrderByNameClicked()
        presenter.updateOrdering()
    }

    fun onShowCompatibleProgramsButtonClicked() {
        presenter.onShowCompatibleProgramsButtonClicked()
    }

    fun onSelectAllChecked(checked: Boolean) {
        presenter.onSelectAllClicked(checked)
    }
}