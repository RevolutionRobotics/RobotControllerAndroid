package com.revolution.robotics.features.coding.saveProgram

import androidx.lifecycle.ViewModel
import com.revolution.robotics.R
import com.revolution.robotics.core.domain.local.UserProgram
import com.revolution.robotics.core.interactor.GetUserProgramInteractor
import com.revolution.robotics.core.kodein.utils.ResourceResolver

class SaveProgramPresenter(
    private val getUserProgramInteractor: GetUserProgramInteractor,
    private val resourceResolver: ResourceResolver
) : SaveProgramMvp.Presenter {

    override var view: SaveProgramMvp.View? = null
    override var model: ViewModel? = null

    private var defaultProgram: UserProgram? = null

    override fun setDefaultUserProgram(userProgram: UserProgram) {
        defaultProgram = userProgram.copy()
    }

    override fun onDoneButtonClicked(userProgram: UserProgram) {
        getUserProgramInteractor.name = userProgram.name
        getUserProgramInteractor.robotId = userProgram.robotId
        getUserProgramInteractor.execute { program ->
            if (program == null || defaultProgram?.name == program.name && program.remoteId == null) {
                view?.saveProgram(userProgram)
            } else {
                view?.showError(resourceResolver.string(R.string.error_program_already_in_use) ?: "")
            }
        }
    }
}
