package com.revolution.robotics.features.coding

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.kodein.utils.ResourceResolver

class CodingViewModel(
    private val resourceResolver: ResourceResolver,
    private val presenter: CodingMvp.Presenter
) :
    ViewModel() {

    val programName =
        ObservableField<String>(resourceResolver.string(R.string.program_title_default))
    var userProgram: UserProgram? = null
    val isBlocklyLoaded = ObservableBoolean(false)
    val isInEditMode = ObservableBoolean(true)

    fun showPythonCode() = presenter.showPythonCode()

    fun showProgramsDialog() = presenter.showProgramsDialog()

    fun showNewProgramDialog() = presenter.showNewProgramDialog()

    fun saveProgram() = presenter.showSaveProgramDialog(userProgram, -1)

    fun onBackPressed() = presenter.onBackPressed()

    fun resetProgramName() {
        this.programName.set(resourceResolver.string(R.string.program_title_default))
    }

    fun play() = presenter.play()


}
