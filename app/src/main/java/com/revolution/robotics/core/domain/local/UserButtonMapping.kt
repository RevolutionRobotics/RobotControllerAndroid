package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
class UserButtonMapping(
    var userControllerId: String? = null,
    var b1: UserProgramBinding? = null,
    var b2: UserProgramBinding? = null,
    var b3: UserProgramBinding? = null,
    var b4: UserProgramBinding? = null,
    var b5: UserProgramBinding? = null,
    var b6: UserProgramBinding? = null
) : Parcelable
