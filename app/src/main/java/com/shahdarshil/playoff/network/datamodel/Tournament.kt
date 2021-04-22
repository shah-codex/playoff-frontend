package com.shahdarshil.playoff.network.datamodel

import com.google.gson.annotations.SerializedName

/**
 * Created by shah on 20 April, 2021.
 */
data class Tournament(
    @SerializedName("tournament_id")
    val _id: String,
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
    val startDate: Int,
    @SerializedName("end_date")
    val endDate: Int? = null,
    val description: String? = null,
)
