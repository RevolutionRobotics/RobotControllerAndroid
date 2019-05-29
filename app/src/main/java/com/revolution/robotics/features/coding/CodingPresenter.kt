package com.revolution.robotics.features.coding

import com.revolution.robotics.features.coding.programs.ProgramsDialog
import com.revolution.robotics.features.coding.saveProgram.SaveProgramDialog

class CodingPresenter : CodingMvp.Presenter {

    override var view: CodingMvp.View? = null
    override var model: CodingViewModel? = null

    override fun showProgramsDialog() {
        view?.showDialog(ProgramsDialog.newInstance())
    }

    override fun saveProgram() {
        // TODO pass current name & description if it exists
        view?.showDialog(SaveProgramDialog.newInstance())
    }
}
