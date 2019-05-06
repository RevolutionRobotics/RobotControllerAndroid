package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.BuildStep

class BuildStepInteractor : FirebaseListInteractor<BuildStep>() {

    var robotId = 0

    override val genericTypeIndicator: GenericTypeIndicator<ArrayList<BuildStep>> =
        object : GenericTypeIndicator<ArrayList<BuildStep>>() {}

    override fun getDatabaseReference(database: FirebaseDatabase): Query = database.getReference("buildStep")

    override fun filter(item: BuildStep) = item.robotId == robotId
}
