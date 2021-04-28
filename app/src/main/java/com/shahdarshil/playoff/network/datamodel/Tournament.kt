package com.shahdarshil.playoff.network.datamodel

import com.google.gson.annotations.SerializedName

/**
 * Created by shah on 20 April, 2021.
 */
data class Tournament(
    @SerializedName("tournament_id")
    val _id: String? = null,
    val title: String,
    val game: String,
    @SerializedName("min_players")
    val minimumPlayers: Int,
    @SerializedName("max_players")
    val maximumPlayers: Int,
    @SerializedName("min_teams")
    val minimumTeams: Int? = null,
    @SerializedName("max_teams")
    val maximumTeams: Int? = null,
    val location: String,
    @SerializedName("start_date")
    val startDate: Long,
    @SerializedName("end_date")
    val endDate: Long? = null,
    val description: String? = null,
    @SerializedName("teams_participated")
    val teamsParticipated: Int? = null,
    @SerializedName("creator")
    val creator: String? = null
)
