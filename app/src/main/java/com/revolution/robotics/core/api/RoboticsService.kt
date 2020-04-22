package com.revolution.robotics.core.api

import retrofit2.Call
import retrofit2.http.GET


interface RoboticsService {

    companion object {
        private const val API_VERSION = 1
    }

    @GET("v$API_VERSION/robots")
    fun getRobots(): Call<String>

    @GET("v$API_VERSION/challenges")
    fun getChallenges(): Call<String>

    @GET("v$API_VERSION//firmware")
    fun getFirmware(): Call<String>

    @GET("v$API_VERSION//versionData")
    fun getVersionData(): Call<String>
}