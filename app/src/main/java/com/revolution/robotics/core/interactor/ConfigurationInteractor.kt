package com.revolution.robotics.core.interactor

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.revolution.robotics.core.domain.remote.Configuration

class ConfigurationInteractor : FirebaseSingleObjectInteractor<Configuration>() {
    override val genericTypeIndicator: GenericTypeIndicator<ArrayList<Configuration>> =
        object : GenericTypeIndicator<ArrayList<Configuration>>() {}

    var configId = 0

    override fun getDatabaseReference(database: FirebaseDatabase): Query =
        database.getReference("configuration").orderByChild("id").equalTo(configId.toDouble()).limitToFirst(1)
}
