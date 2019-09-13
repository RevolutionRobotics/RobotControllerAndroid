package com.revolution.robotics.features.onboarding.userTypeSelection

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserType

interface UserTypeSelectionMvp : Mvp {

    interface View : Mvp.View {
        fun showYearOfBirthPopup()
    }

    interface Presenter : Mvp.Presenter<View, UserTypeSelectionViewModel> {
        fun onUserTypeSelected(userType: UserType)
    }
}