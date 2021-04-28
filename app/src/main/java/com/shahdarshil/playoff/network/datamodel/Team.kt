package com.shahdarshil.playoff.network.datamodel

import com.google.gson.annotations.SerializedName

/**
 * Created by shah on 22 April, 2021.
 */
data class Team(
    val name: String,
    val captain: String? = null,
    @SerializedName("tournament")
    val tournamentId: String? = null,
    @SerializedName("joined_players")
    val joinedPlayers: Int? = null,
    val playing: Int? = null
)
