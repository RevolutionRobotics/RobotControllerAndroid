package com.revolution.robotics.features.onboarding.userTypeSelection

import androidx.lifecycle.ViewModel
import com.revolution.robotics.core.domain.local.UserType

class UserTypeSelectionViewModel(private val presenter: UserTypeSelectionMvp.Presenter) : ViewModel() {

    fun onUserTypeSelected(userType: UserType) = presenter.onUserTypeSelected(userType)
}