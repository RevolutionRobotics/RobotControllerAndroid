package com.revolution.robotics.core.interactor

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.Robot

class RobotInteractor : FirebaseListInteractor<Robot>() {

    override val genericTypeIndicator: GenericTypeIndicator<ArrayList<Robot>> =
        object : GenericTypeIndicator<ArrayList<Robot>>() {}

    override fun getDatabaseReference(database: FirebaseDatabase): Query = database.getReference("robot")
}
