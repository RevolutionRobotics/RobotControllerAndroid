package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Program(
    var description: LocalizedString? = null,
    var id: String? = null,
    var robotId: String? = null,
    var lastModified: Long = 0,
    var name: LocalizedString? = null,
    var python: String? = null,
    var xml: String? = null,
    var variables: List<String> = emptyList()
) : Parcelable
