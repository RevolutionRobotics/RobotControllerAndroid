package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.Robot

class RobotInteractor : FirebaseSingleObjectInteractor<Robot>() {

    override val genericTypeIndicator: GenericTypeIndicator<HashMap<String, Robot>> =
        object : GenericTypeIndicator<HashMap<String, @JvmSuppressWildcards Robot>>() {}

    lateinit var robotId: String

    override fun getDatabaseReference(database: FirebaseDatabase): Query =
        database.getReference("robot").orderByChild("id").equalTo(robotId).limitToFirst(1)
}