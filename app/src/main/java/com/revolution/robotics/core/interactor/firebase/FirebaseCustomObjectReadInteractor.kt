package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.DataSnapshot
import com.revolution.robotics.core.domain.remote.FirebaseException

abstract class FirebaseCustomObjectReadInteractor<T> : FirebaseInteractor<T>() {

    override fun onDataChange(snapShot: DataSnapshot) {
        val response = convert(snapShot)
        if (response != null) {
            onResponse?.invoke(response)
        } else {
            onError?.invoke(FirebaseException("Data is null"))
        }
    }

    abstract fun convert(snapShot: DataSnapshot): T
}