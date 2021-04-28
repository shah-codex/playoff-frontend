package com.shahdarshil.playoff.network

import com.shahdarshil.playoff.network.datamodel.Player
import com.shahdarshil.playoff.network.datamodel.Team
import com.shahdarshil.playoff.network.datamodel.Tournament
import com.shahdarshil.playoff.network.datamodel.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

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

    @POST("/user/forgot-password")
    suspend fun forgotPassword(@Body user: User): Response<User>

    @GET("/tournament/{location}")
    suspend fun getTournaments(@Path("location") location: String): Response<List<Tournament>?>

    @GET("/tournament/id/{tournament}")
    suspend fun getTournament(@Path("tournament") tournament: String): Response<Tournament>

    @POST("/tournament/join")
    suspend fun joinTournament(@Body team: Team): Response<Team>

    @PUT("/tournament/create")
    suspend fun createTournament(@Body tournament: Tournament): Response<Tournament>

    @GET("/team/")
    suspend fun getTeams(): Response<List<Team>?>

    @GET("/team/details/{teamName}")
    suspend fun getTeam(@Path("teamName") teamName: String): Response<Team>

    @GET("/team/{teamName}/players")
    suspend fun getTeamPlayers(@Path("teamName") teamName: String): Response<List<User>>

    @POST("/team/join")
    suspend fun joinTeam(@Body player: Player): Response<Player>

    @POST("/team/create")
    suspend fun createTeam(@Body team: Team): Response<Team>

    @GET("/team/{player}")
    suspend fun getPlayerTeam(@Path("player") player: String): Response<Player>
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