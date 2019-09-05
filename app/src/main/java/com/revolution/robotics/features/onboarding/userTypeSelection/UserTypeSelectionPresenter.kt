package com.revolution.robotics.features.onboarding.userTypeSelection

import com.revolution.robotics.core.domain.local.UserType
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.Navigator

class UserTypeSelectionPresenter(
    private val appPrefs: AppPrefs,
    private val navigator: Navigator
) : UserTypeSelectionMvp.Presenter {

    override var view: UserTypeSelectionMvp.View? = null
    override var model: UserTypeSelectionViewModel? = null

    override fun register(view: UserTypeSelectionMvp.View, model: UserTypeSelectionViewModel?) {
        super.register(view, model)
        model?.selectableYears?.value = (2001..2012).map { it.toString() }
    }

    override fun onUserTypeSelected(userType: UserType) {
        model?.displayYearSelector?.value = userType == UserType.STUDENT
    }

    override fun onNextClicked() {
        appPrefs.userTypeSelected = true
        navigator.back()
    }

}