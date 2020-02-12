package com.revolution.robotics.features.onboarding.userTypeSelection.age

import android.os.Bundle
import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.databinding.DialogYearOfBirthSelectionBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance
import java.util.*

class YearOfBirthSelectionDialog : RoboticsDialog() {

    companion object {
        const val CHILDREN_MAX_AGE = 18
        const val DEFAULT_YEAR_SELECTION = 8
        fun newInstance() = YearOfBirthSelectionDialog()
    }

    private val reporter: Reporter by kodein.instance()
    private val appPrefs: AppPrefs by kodein.instance()
    private val earliestYear = Calendar.getInstance().get(Calendar.YEAR) - CHILDREN_MAX_AGE
    override val hasCloseButton = true
    override val dialogFaces: List<DialogFace<*>> = listOf(YearOfBirthSelectorDialogFace(this, earliestYear))
    override val dialogButtons: List<DialogButton> = listOf(
        DialogButton(R.string.onboarding_save, R.drawable.ic_play) {
            appPrefs.userTypeSelected = true
            reporter.reportEvent(Reporter.Event.SELECT_USER_TYPE)
            dismiss()
            navigator.back()
        }
    )

    fun onYearSelected(position:Int) {
        reporter.setUserProperty(Reporter.UserProperty.YEAR_OF_BIRTH, (earliestYear + position).toString())
        reporter.reportEvent(Reporter.Event.SELECT_YEAR_OF_BIRTH, Bundle().apply { putInt(Reporter.Parameter.YEAR.parameterName, earliestYear + position) })
    }

    class YearOfBirthSelectorDialogFace(dialog: RoboticsDialog, private val earliestYear: Int) :
        DialogFace<DialogYearOfBirthSelectionBinding>(R.layout.dialog_year_of_birth_selection, dialog) {

        override fun onActivated() {
            super.onActivated()
            binding?.viewModel = YearOfBirthSelectionViewModel(dialog as YearOfBirthSelectionDialog).apply {
                selectableYears.value = (earliestYear..earliestYear + CHILDREN_MAX_AGE).map { it.toString() }
                selectedPosition.value = DEFAULT_YEAR_SELECTION
            }
        }
    }
}