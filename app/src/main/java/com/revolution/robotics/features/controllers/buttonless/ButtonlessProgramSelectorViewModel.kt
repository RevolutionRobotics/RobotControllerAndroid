package com.revolution.robotics.features.controllers.buttonless

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.features.controllers.ProgramOrderingHandler
import com.revolution.robotics.features.controllers.buttonless.adapter.ButtonlessProgramViewModel

class ButtonlessProgramSelectorViewModel(private val presenter: ButtonlessProgramSelectorMvp.Presenter) : ViewModel() {

    val dateOrderIcon = ObservableInt(R.drawable.sort_date_down)
    val alphabeticalOderIcon = ObservableInt(R.drawable.sort_name_up)
    val showCompatibleButtonText = ObservableInt(R.string.buttonless_program_show_compatible_programs)
    val showCompatibleButtonIcon = ObservableInt(R.drawable.ic_compatible)
    val isEmpty = ObservableBoolean(false)
    val emptyText = ObservableInt(R.string.buttonless_program_selector_compatible_empty)

    val programOrderingHandler = ProgramOrderingHandler()
    val items = MutableLiveData<List<ButtonlessProgramViewModel>>()

    val dateOrderIconColor = ObservableInt(R.color.white)
    val nameOrderIconColor = ObservableInt(R.color.white)

    fun onDateOrderClicked() {
        programOrderingHandler.onOrderByDateClicked()
        presenter.updateOrderingAndFiltering()
    }

    fun onAlphabeticalOrderIconClicked() {
        programOrderingHandler.onOrderByNameClicked()
        presenter.updateOrderingAndFiltering()
    }

    fun onShowCompatibleProgramsButtonClicked() {
        presenter.onShowCompatibleProgramsButtonClicked()
    }

    fun onSelectAllChecked(checked: Boolean) {
        presenter.onSelectAllClicked(checked)
    }
}
