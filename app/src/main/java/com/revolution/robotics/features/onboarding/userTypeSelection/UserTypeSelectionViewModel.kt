package com.revolution.robotics.features.onboarding.userTypeSelection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.local.UserType

class UserTypeSelectionViewModel(private val presenter: UserTypeSelectionMvp.Presenter) : ViewModel() {

    val selectableYears = MutableLiveData<List<String>>()
    val displayYearSelector = MutableLiveData<Boolean>()

    fun onNextClicked() = presenter.onNextClicked()

    fun onUserTypeSelected(userType: UserType) = presenter.onUserTypeSelected(userType)

}