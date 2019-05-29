package com.revolution.robotics.features.coding

import com.revolution.robotics.features.coding.programs.ProgramsDialog

class CodingPresenter : CodingMvp.Presenter {

    override var view: CodingMvp.View? = null
    override var model: CodingViewModel? = null

    override fun showProgramsDialog() {
        view?.showDialog(ProgramsDialog.newInstance())
    }
}
