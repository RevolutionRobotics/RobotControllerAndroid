package com.revolution.robotics.core.interactor

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.BuildStep

class BuildStepInteractor : FirebaseListInteractor<BuildStep>() {

    var robotId = 0

    override val genericTypeIndicator: GenericTypeIndicator<ArrayList<BuildStep>> =
        object : GenericTypeIndicator<ArrayList<BuildStep>>() {}

    override fun getDatabaseReference(database: FirebaseDatabase): Query =
        database.getReference("buildStep")
            .orderByChild("robotId")
            .equalTo(robotId.toDouble())
}
