package com.revolution.robotics.features.coding

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.kodein.utils.ResourceResolver

class CodingViewModel(resourceResolver: ResourceResolver, private val presenter: CodingMvp.Presenter) : ViewModel() {

    val programName = ObservableField<String>(resourceResolver.string(R.string.program_title_default))
    var userProgram: UserProgram? = null

    fun showProgramsDialog() {
        presenter.showProgramsDialog()
    }

    fun saveProgram() {
        presenter.showSaveProgramDialog(userProgram)
    }
}
