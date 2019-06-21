package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.Controller

class ControllerInteractor : FirebaseListInteractor<Controller>() {

    override val genericTypeIndicator: GenericTypeIndicator<Map<String, Controller>> =
        object : GenericTypeIndicator<Map<String, Controller>>() {}

    lateinit var configurationId: String

    override fun getDatabaseReference(database: FirebaseDatabase): Query =
        database.getReference("controller")

    override fun filter(item: Controller): Boolean = item.configurationId == configurationId
}
