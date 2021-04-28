package com.shahdarshil.playoff.network.datamodel

import com.google.gson.annotations.SerializedName

/**
 * Created by shah on 24 April, 2021.
 */
data class Player(
    @SerializedName("name")
    val playerName: String? = null,
    @SerializedName("team")
    val teamName: String? = null,
)
