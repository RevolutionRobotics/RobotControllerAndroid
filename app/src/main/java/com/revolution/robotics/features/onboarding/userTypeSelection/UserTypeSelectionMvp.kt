package com.revolution.robotics.features.onboarding.userTypeSelection

import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserType

interface UserTypeSelectionMvp : Mvp {

    interface View : Mvp.View

    interface Presenter : Mvp.Presenter<View, UserTypeSelectionViewModel> {
        fun onNextClicked()
        fun onUserTypeSelected(userType: UserType)
        fun onYearSelected(position: Int)
    }
}