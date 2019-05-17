package com.revolution.robotics.core.domain

import android.os.Parcelable
import com.revolution.robotics.core.domain.remote.ProgramBinding
import kotlinx.android.parcel.Parcelize

@Parcelize
open class ButtonMapping(
    var b1: ProgramBinding? = null,
    var b2: ProgramBinding? = null,
    var b3: ProgramBinding? = null,
    var b4: ProgramBinding? = null,
    var b5: ProgramBinding? = null,
    var b6: ProgramBinding? = null
) : Parcelable
