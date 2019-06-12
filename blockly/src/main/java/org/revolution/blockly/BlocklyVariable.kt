package org.revolution.blockly

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BlocklyVariable(val name: String, val key: String) : Parcelable
