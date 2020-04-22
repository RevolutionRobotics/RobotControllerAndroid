package com.revolution.robotics.core.api

import com.revolution.robotics.core.domain.remote.VersionData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers


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

    @Headers("Cache-Control: public, only-if-cached, max-stale=604800, stale-if-error=604800")
    @GET("v$API_VERSION//versionData")
    fun getVersionData(): Call<VersionData>
}