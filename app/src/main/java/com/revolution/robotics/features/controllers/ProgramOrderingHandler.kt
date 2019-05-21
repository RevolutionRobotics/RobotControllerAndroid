package com.revolution.robotics.features.controllers

import com.revolution.robotics.core.domain.local.UserProgram

class ProgramOrderingHandler {

    enum class OrderBy { NAME, DATE }
    enum class Order { ASCENDING, DESCENDING }

    var currentOrder = OrderBy.NAME to Order.ASCENDING

    fun onOrderByNameClicked() {
        currentOrder =
            if (currentOrder.first == OrderBy.NAME && currentOrder.second == Order.ASCENDING) {
                OrderBy.NAME to Order.DESCENDING
            } else {
                OrderBy.NAME to Order.ASCENDING
            }
    }

    fun onOrderByDateClicked() {
        currentOrder =
            if (currentOrder.first == OrderBy.DATE && currentOrder.second == Order.DESCENDING) {
                OrderBy.DATE to Order.ASCENDING
            } else {
                OrderBy.DATE to Order.DESCENDING
            }
    }

    fun getComparator() = if (currentOrder.first == OrderBy.NAME) {
        if (currentOrder.second == Order.ASCENDING) {
            compareBy<UserProgram> { it.name }
        } else {
            compareByDescending { it.name }
        }
    } else {
        if (currentOrder.second == Order.ASCENDING) {
            compareBy<UserProgram> { it.lastModified }
        } else {
            compareByDescending { it.name }
        }
    }

    fun isOrderedByName() = currentOrder.first == OrderBy.NAME
    fun isNameAscending() = currentOrder.first == OrderBy.NAME && currentOrder.second == Order.ASCENDING
    fun isOrderedByDate() = currentOrder.first == OrderBy.DATE
    fun isDateAscending() = currentOrder.first == OrderBy.DATE && currentOrder.second == Order.ASCENDING
}