package com.revolution.robotics.features.coding

import com.revolution.robotics.BaseDialog
import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserProgram
import org.revolution.blockly.view.jsInterface.BlocklyJavascriptListener

interface CodingMvp : Mvp {

    interface View : Mvp.View {
        fun showDialog(baseDialog: BaseDialog)
        fun loadProgramIntoTheBlockly(xml: String)
        fun clearBlocklyWorkspace()
    }

    interface Presenter : Mvp.Presenter<View, CodingViewModel>, BlocklyJavascriptListener {
        fun showProgramsDialog()
        fun showSaveProgramDialog(userProgram: UserProgram?)
        fun setSavedProgramData(userProgram: UserProgram)
        fun removeProgram(userProgram: UserProgram)
        fun loadProgram(userProgram: UserProgram)
    }
}
