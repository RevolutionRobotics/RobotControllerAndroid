package com.revolution.robotics.core.api

import retrofit2.Call
import retrofit2.http.GET


interface RoboticsService {

    @GET(".json")
    fun getDatabaseContents(): Call<String>
}