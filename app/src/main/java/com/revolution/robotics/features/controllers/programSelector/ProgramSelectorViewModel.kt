package com.revolution.robotics.features.controllers.programSelector

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.features.controllers.ProgramOrderingHandler

class ProgramSelectorViewModel(private val presenter: ProgramSelectorMvp.Presenter) : ViewModel() {

    val isEmpty = ObservableBoolean(false)
    val filterDrawable = ObservableInt(R.drawable.ic_compatible)
    val filterText = ObservableInt(R.string.program_selector_show_compatible_programs)
    val programOrderingHandler = ProgramOrderingHandler()

    fun onBackPressed() {
        presenter.back()
    }

    fun onOrderByNameClicked() {
        programOrderingHandler.onOrderByNameClicked()
        presenter.updateOrderingAndFiltering()
    }

    fun onOrderByDateClicked() {
        programOrderingHandler.onOrderByDateClicked()
        presenter.updateOrderingAndFiltering()
    }

    fun isOrderedByName() = programOrderingHandler.isOrderedByName()
    fun isNameAscending() = programOrderingHandler.isNameAscending()
    fun isOrderedByDate() = programOrderingHandler.isOrderedByDate()
    fun isDateAscending() = programOrderingHandler.isDateAscending()

    fun onShowCompatibleProgramsButtonClicked() {
        presenter.showCompatibleProgramsClicked()
    }
}
