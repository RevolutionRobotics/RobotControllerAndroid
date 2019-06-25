package com.revolution.robotics.features.coding

import com.revolution.robotics.BaseDialog
import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserProgram
import org.revolution.blockly.view.jsInterface.SaveBlocklyListener

interface CodingMvp : Mvp {

    interface View : Mvp.View {
        fun showDialog(baseDialog: BaseDialog)
        fun loadProgramIntoTheBlockly(xml: String)
        fun clearBlocklyWorkspace()
        fun getPythonCodeFromBlockly(listener: SaveBlocklyListener)
        fun onProgramSaved()
        fun onToolbarBackPressed()
    }

    interface Presenter : Mvp.Presenter<View, CodingViewModel>, SaveBlocklyListener {
        fun showProgramsDialog()
        fun showSaveProgramDialog(userProgram: UserProgram?)
        fun setSavedProgramData(userProgram: UserProgram)
        fun removeProgram(userProgram: UserProgram)
        fun loadProgram(userProgram: UserProgram)
        fun showPythonCode()
        fun onBackPressed()
    }
}
