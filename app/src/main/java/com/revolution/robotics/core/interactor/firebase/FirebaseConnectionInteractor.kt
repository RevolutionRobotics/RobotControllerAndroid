package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.FirebaseException

class FirebaseConnectionInteractor: FirebaseInteractor<Boolean>() {

    override fun onDataChange(snapShot: DataSnapshot) {
        val response = snapShot.getValue(Boolean::class.java)
        if (response != null) {
            onResponse?.invoke(response)
        } else {
            onError?.invoke(FirebaseException("Data is null"))
        }
    }

    override fun getDatabaseReference(database: FirebaseDatabase): Query = database.getReference(".info/connected")
}