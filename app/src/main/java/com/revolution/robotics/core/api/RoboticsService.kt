package com.revolution.robotics.core.api

import retrofit2.Call
import retrofit2.http.GET


interface RoboticsService {

    @GET("https://api.myjson.com/bins/11v9zw")
    fun getRobots(): Call<String>

    @GET("https://api.myjson.com/bins/u5i2o")
    fun getChallenges(): Call<String>

    @GET(".json")
    fun getDatabaseContents(): Call<String>
}