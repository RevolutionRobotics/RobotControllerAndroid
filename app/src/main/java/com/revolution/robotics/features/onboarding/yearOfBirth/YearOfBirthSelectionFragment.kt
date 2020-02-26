package com.revolution.robotics.features.onboarding.yearOfBirth

import android.os.Bundle
import android.view.View
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentYearOfBirthSelectionBinding
import org.kodein.di.erased.instance
import java.util.*

class YearOfBirthSelectionFragment :
    BaseFragment<FragmentYearOfBirthSelectionBinding, YearOfBirthSelectionViewModel>(R.layout.fragment_year_of_birth_selection) {

    override val viewModelClass: Class<YearOfBirthSelectionViewModel> =
        YearOfBirthSelectionViewModel::class.java
    override val screen = Reporter.Screen.USER_TYPE_SELECTION

    private val appPrefs: AppPrefs by kodein.instance()
    private val navigator: Navigator by kodein.instance()

    private val latestYear = Calendar.getInstance().get(Calendar.YEAR) - 1
    private val earliestYear = latestYear - MAX_AGE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel?.apply {
            selectableYears.value =
                (earliestYear..latestYear).map { it.toString() }
            selectedPosition.value = selectableYears.value!!.count() - DEFAULT_AGE
            fragment = this@YearOfBirthSelectionFragment
        }
    }

    fun onNextClicked(year: Int?) {
        reporter.setUserProperty(Reporter.UserProperty.YEAR_OF_BIRTH, year.toString())
        appPrefs.yearOfBirthSelected = true
        navigator.back()
    }

    companion object {
        const val MAX_AGE = 99
        const val DEFAULT_AGE = 10
    }
}