package com.revolution.robotics.features.onboarding.yearOfBirth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class YearOfBirthSelectionViewModel: ViewModel() {

    var fragment: YearOfBirthSelectionFragment? = null

    val selectableYears = MutableLiveData<List<String>>()

    val selectedPosition = MutableLiveData<Int>()

    fun onYearSelected(position: Int) {
        selectedPosition.value = position
    }

    fun onNextClicked() {
        fragment?.onNextClicked(selectableYears.value?.get(selectedPosition.value ?: 0)?.toInt())
    }
}