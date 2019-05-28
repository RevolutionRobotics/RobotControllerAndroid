package com.revolution.robotics.blockly.utils

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class StringList : ArrayList<String>(), Parcelable
