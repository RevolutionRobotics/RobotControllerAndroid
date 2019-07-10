package com.revolution.robotics.core.domain.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Suppress("DataClassContainsFunctions")
@Parcelize
data class LocalizedString(var en: String?) : Parcelable {

    // TODO load the text based on the app language
    fun getLocalizedString(): String? = en
}