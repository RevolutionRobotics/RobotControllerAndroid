package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.GenericTypeIndicator
import com.revolution.robotics.core.domain.remote.FirebaseException

abstract class FirebaseListInteractor<T> : FirebaseInteractor<ArrayList<T>>() {

    abstract val genericTypeIndicator: GenericTypeIndicator<ArrayList<T>>

    override fun onDataChange(snapShot: DataSnapshot) {
        val response = snapShot.getValue(genericTypeIndicator)
        if (response != null) {
            onResponse?.invoke(response)
        } else {
            onError?.invoke(FirebaseException("Data is null"))
        }
    }
}
