package com.revolution.robotics.core.domain.local

import android.os.Parcelable
import androidx.room.Entity
import com.revolution.robotics.core.domain.PortMapping
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
class UserMapping(var userConfigId: Int = 0) : PortMapping(), Parcelable
