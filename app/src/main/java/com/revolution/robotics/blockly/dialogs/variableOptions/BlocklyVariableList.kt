package com.revolution.robotics.blockly.dialogs.variableOptions

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.revolutionrobotics.robotcontroller.blocklysdk.BlocklyVariable

@Parcelize
class BlocklyVariableList : ArrayList<BlocklyVariable>(), Parcelable
