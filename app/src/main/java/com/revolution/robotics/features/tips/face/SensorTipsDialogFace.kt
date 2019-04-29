package com.revolution.robotics.features.tips.face

import com.revolution.robotics.R
import com.revolution.robotics.features.tips.face.base.TipsDialogFace

class SensorTipsDialogFace : TipsDialogFace() {
    override val bulletCharacter: Char = '-'
    override val tipsList: List<Int> = listOf(
        R.string.tips_dialog_sensor_tip_1,
        R.string.tips_dialog_sensor_tip_2,
        R.string.tips_dialog_sensor_tip_3,
        R.string.tips_dialog_sensor_tip_4
    )
}
