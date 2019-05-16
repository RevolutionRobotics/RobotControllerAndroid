package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.Controller

class ControllerInteractor : FirebaseSingleObjectInteractor<Controller>() {

    override val genericTypeIndicator: GenericTypeIndicator<ArrayList<Controller>> =
        object : GenericTypeIndicator<ArrayList<Controller>>() {}

    lateinit var controllerId: String

    override fun getDatabaseReference(database: FirebaseDatabase): Query =
        database.getReference("controller").orderByChild("id").equalTo(controllerId).limitToFirst(1)
}
