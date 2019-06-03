package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.revolution.robotics.core.domain.remote.Configuration

class ConfigurationInteractor : FirebaseCustomObjectReadInteractor<Configuration>() {

    var configId = 0

    override fun convert(snapShot: DataSnapshot) = Configuration().apply {
        val gson = Gson()
        val json = gson.toJson(snapShot.value)
        val list: List<Configuration> =
            gson.fromJson(json, object : TypeToken<List<Configuration>>() {}.type) ?: emptyList()
        return list.first { it.id == configId }
    }

    override fun getDatabaseReference(database: FirebaseDatabase): Query =
        database.getReference("configuration")
}
