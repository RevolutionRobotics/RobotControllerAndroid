package com.revolution.robotics.features.onboarding.userTypeSelection.age

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class YearOfBirthSelectionViewModel(private val yearOfBirthSelectionDialog: YearOfBirthSelectionDialog): ViewModel() {

    val selectableYears = MutableLiveData<List<String>>()

    val selectedPosition = MutableLiveData<Int>()

    fun onYearSelected(position: Int) = yearOfBirthSelectionDialog.onYearSelected(position)
}