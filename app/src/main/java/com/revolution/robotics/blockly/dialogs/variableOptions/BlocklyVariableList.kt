package com.revolution.robotics.blockly.dialogs.variableOptions

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.revolution.blockly.BlocklyVariable

@Parcelize
class BlocklyVariableList : ArrayList<BlocklyVariable>(), Parcelable
