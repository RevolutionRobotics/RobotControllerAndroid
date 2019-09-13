package com.revolution.robotics.features.onboarding.userTypeSelection.age

import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.databinding.DialogYearOfBirthSelectionBinding
import com.revolution.robotics.views.dialogs.DialogButton
import com.revolution.robotics.views.dialogs.DialogFace
import com.revolution.robotics.views.dialogs.RoboticsDialog
import org.kodein.di.erased.instance

class YearOfBirthSelectionDialog : RoboticsDialog() {

    companion object {
        private const val EARLIEST_YEAR_SELECTABLE = 2001
        fun newInstance() = YearOfBirthSelectionDialog()
    }

    private val reporter: Reporter by kodein.instance()
    private val appPrefs: AppPrefs by kodein.instance()

    override val hasCloseButton = true
    override val dialogFaces: List<DialogFace<*>> = listOf(YearOfBirthSelectorDialogFace(this))
    override val dialogButtons: List<DialogButton> = listOf(
        DialogButton(R.string.onboarding_save, R.drawable.ic_play) {
            appPrefs.userTypeSelected = true
            dismiss()
            navigator.back()
        }
    )

    fun onYearSelected(position:Int) {
        reporter.setUserProperty(Reporter.UserProperty.YEAR_OF_BIRTH, (EARLIEST_YEAR_SELECTABLE + position).toString())
    }

    class YearOfBirthSelectorDialogFace(dialog: RoboticsDialog) :
        DialogFace<DialogYearOfBirthSelectionBinding>(R.layout.dialog_year_of_birth_selection, dialog) {

        override fun onActivated() {
            super.onActivated()
            binding?.viewModel = YearOfBirthSelectionViewModel(dialog as YearOfBirthSelectionDialog).apply {
                selectableYears.value = (EARLIEST_YEAR_SELECTABLE..EARLIEST_YEAR_SELECTABLE + 18).map { it.toString() }
            }
        }
    }
}