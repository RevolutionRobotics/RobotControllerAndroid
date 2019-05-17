package com.revolution.robotics.features.controller.programSelector

import androidx.lifecycle.ViewModel

class ProgramSelectorViewModel(private val presenter: ProgramSelectorMvp.Presenter) : ViewModel() {

    enum class OrderBy { NAME, DATE }
    enum class Order { ASCENDING, DESCENDING }

    var currentOrder = OrderBy.NAME to Order.ASCENDING

    fun onBackPressed() {
        presenter.back()
    }

    fun onOrderByNameClicked() {
        currentOrder =
            if (currentOrder.first == OrderBy.NAME && currentOrder.second == Order.ASCENDING) {
                OrderBy.NAME to Order.DESCENDING
            } else {
                OrderBy.NAME to Order.ASCENDING
            }
        presenter.updateOrdering()
    }

    fun onOrderByDateClicked() {
        currentOrder =
            if (currentOrder.first == OrderBy.DATE && currentOrder.second == Order.DESCENDING) {
                OrderBy.DATE to Order.ASCENDING
            } else {
                OrderBy.DATE to Order.DESCENDING
            }
        presenter.updateOrdering()
    }

    fun isOrderedByName() = currentOrder.first == OrderBy.NAME
    fun isNameAscending() = currentOrder.first == OrderBy.NAME && currentOrder.second == Order.ASCENDING
    fun isOrderedByDate() = currentOrder.first == OrderBy.DATE
    fun isDateAscending() = currentOrder.first == OrderBy.DATE && currentOrder.second == Order.ASCENDING
}
