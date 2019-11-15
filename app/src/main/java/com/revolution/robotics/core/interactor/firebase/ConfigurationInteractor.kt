package com.revolution.robotics.core.interactor.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.revolution.robotics.core.domain.remote.Configuration

class ConfigurationInteractor : FirebaseCustomObjectReadInteractor<Configuration>() {

    lateinit var configId: String

    override fun convert(snapShot: DataSnapshot) = Configuration().apply {
        val gson = Gson()
        val json = gson.toJson(snapShot.value)
        val list: HashMap<String, Configuration> =
            gson.fromJson(json, object : TypeToken<HashMap<String, Configuration>>() {}.type) ?: hashMapOf()
        return list.toList().first { it.second.id == configId }.second
    }

    override fun getDatabaseReference(database: FirebaseDatabase): Query =
        database.getReference("configuration")
}
