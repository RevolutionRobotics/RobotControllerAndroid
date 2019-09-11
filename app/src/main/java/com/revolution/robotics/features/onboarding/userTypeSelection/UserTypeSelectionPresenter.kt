package com.revolution.robotics.features.onboarding.userTypeSelection

import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.domain.local.UserType
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.Navigator

class UserTypeSelectionPresenter(
    private val appPrefs: AppPrefs,
    private val navigator: Navigator,
    private val reporter: Reporter
) : UserTypeSelectionMvp.Presenter {

    companion object {
        private const val EARLIEST_YEAR_SELECTABLE = 2001
    }

    override var view: UserTypeSelectionMvp.View? = null
    override var model: UserTypeSelectionViewModel? = null

    override fun register(view: UserTypeSelectionMvp.View, model: UserTypeSelectionViewModel?) {
        super.register(view, model)
        model?.selectableYears?.value = (EARLIEST_YEAR_SELECTABLE..EARLIEST_YEAR_SELECTABLE + 18).map { it.toString() }
        onUserTypeSelected(UserType.STUDENT)
    }

    override fun onUserTypeSelected(userType: UserType) {
        reporter.setUserProperty(Reporter.UserProperty.USER_TYPE, userType.reportName)
        if (userType == UserType.STUDENT) {

        } else {
            appPrefs.userTypeSelected = true
            navigator.back()
        }
    }

    override fun onYearSelected(position: Int) {
        reporter.setUserProperty(Reporter.UserProperty.YEAR_OF_BIRTH, (EARLIEST_YEAR_SELECTABLE + position).toString())
    }

    override fun onNextClicked() {
        appPrefs.userTypeSelected = true
        reporter.reportEvent(Reporter.Event.SELECTED_USER_TYPE)
        navigator.back()
    }

}