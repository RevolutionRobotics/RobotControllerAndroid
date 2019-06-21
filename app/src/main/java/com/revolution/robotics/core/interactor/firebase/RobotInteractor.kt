package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.Robot

class RobotInteractor : FirebaseListInteractor<Robot>() {

    override val genericTypeIndicator: GenericTypeIndicator<Map<String, Robot>> =
        object : GenericTypeIndicator<Map<String, Robot>>() {}

    override fun getDatabaseReference(database: FirebaseDatabase): Query = database.getReference("robot")

    override fun filter(item: Robot) = true
}
