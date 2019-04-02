package com.revolution.robotics.core.interactor

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.revolution.robotics.core.domain.Robot

class RobotInteractor : FirebaseInteractor<ArrayList<Robot>>() {

    override val genericTypeIndicator: GenericTypeIndicator<ArrayList<Robot>> =
        object : GenericTypeIndicator<ArrayList<Robot>>() {}

    override fun getDatabaseReference(database: FirebaseDatabase): DatabaseReference = database.getReference("robot")
}
