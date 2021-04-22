package com.shahdarshil.playoff.network

import com.google.gson.GsonBuilder
import com.shahdarshil.playoff.network.datamodel.Tournament
import com.shahdarshil.playoff.network.datamodel.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by shah on 14 April, 2021.
 */
private const val BASE_URL = "http://192.168.43.173:8080"

interface PlayoffApiService {
    @POST("/user/login")
    suspend fun validateUser(@Body user: User): Response<User>

    @POST("/user/register")
    suspend fun registerUser(@Body user: User): Response<User>

    @POST("/user/authenticate")
    suspend fun requestOtp(@Body user: User): Response<Any>

    @GET("/tournament/{location}")
    suspend fun getTournaments(@Path("location") location: String): Response<List<Tournament>?>
}

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object PlayoffApi {
    val retrofitService: PlayoffApiService by lazy {
        retrofit.create(PlayoffApiService::class.java)
    }
}