package org.revolution.blockly

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BlocklyOption(val key: String, val value: String) : Parcelable
