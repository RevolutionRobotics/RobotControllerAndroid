package com.revolution.robotics.blockly.dialogs.variableOptions

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.revolutionrobotics.blockly.android.BlocklyVariable

@Parcelize
class BlocklyVariableList : ArrayList<BlocklyVariable>(), Parcelable
