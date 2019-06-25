package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.Controller

class ControllerInteractor : FirebaseListInteractor<Controller>() {

    override val genericTypeIndicator: GenericTypeIndicator<HashMap<String, @JvmSuppressWildcards  Controller>> =
        object : GenericTypeIndicator<HashMap<String, Controller>>() {}

    lateinit var configurationId: String

    override fun getDatabaseReference(database: FirebaseDatabase): Query =
        database.getReference("controller")

    override fun order(list: List<Controller>): List<Controller> = list.sortedBy { it.lastModified }
    override fun filter(item: Controller): Boolean = item.configurationId == configurationId
}
