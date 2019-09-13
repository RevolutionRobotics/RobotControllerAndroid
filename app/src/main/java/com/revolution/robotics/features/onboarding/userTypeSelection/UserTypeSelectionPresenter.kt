package com.revolution.robotics.features.onboarding.userTypeSelection

import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.domain.local.UserType
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.features.onboarding.userTypeSelection.age.YearOfBirthSelectionDialog

class UserTypeSelectionPresenter(
    private val appPrefs: AppPrefs,
    private val navigator: Navigator,
    private val reporter: Reporter
) : UserTypeSelectionMvp.Presenter {

    override var view: UserTypeSelectionMvp.View? = null
    override var model: UserTypeSelectionViewModel? = null

    override fun onUserTypeSelected(userType: UserType) {
        reporter.setUserProperty(Reporter.UserProperty.USER_TYPE, userType.reportName)
        if (userType == UserType.STUDENT) {
            view?.showYearOfBirthPopup()
        } else {
            appPrefs.userTypeSelected = true
            navigator.back()
        }
    }
}