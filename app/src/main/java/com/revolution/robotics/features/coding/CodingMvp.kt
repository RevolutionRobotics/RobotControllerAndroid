package com.revolution.robotics.features.coding

import com.revolution.robotics.BaseDialog
import com.revolution.robotics.core.Mvp
import com.revolution.robotics.core.domain.local.UserConfiguration
import com.revolution.robotics.core.domain.local.UserProgram
import org.revolutionrobotics.blockly.android.view.jsInterface.SaveBlocklyListener

interface CodingMvp : Mvp {

    interface View : Mvp.View {
        fun onConfigLoaded(userConfiguration: UserConfiguration?)
        fun showDialog(baseDialog: BaseDialog)
        fun loadProgramIntoTheBlockly(xml: String)
        fun clearBlocklyWorkspace()
        fun getDataFromBlocklyView(listener: SaveBlocklyListener)
        fun onProgramSaved()
        fun onToolbarBackPressed()
        fun onBackPressed(showDialog: Boolean)
        fun shareCode(code: String, xml: String)
    }

    interface Presenter : Mvp.Presenter<View, CodingViewModel>, SaveBlocklyListener {
        fun showProgramsDialog()
        fun showSaveProgramDialog(userProgram: UserProgram?, actionIdAfterSave: Int)
        fun setSavedProgramData(userProgram: UserProgram, actionId: Int)
        fun showNewProgramDialog()
        fun removeProgram(userProgram: UserProgram)
        fun loadProgram(userProgram: UserProgram)
        fun showRobotSelectionDialog()
        fun createNewProgram(robotId: Int)
        fun showPythonCode()
        fun play()
        fun onBackPressed()
        fun shareProgram()
    }
}
