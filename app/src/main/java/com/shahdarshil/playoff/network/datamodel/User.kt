package com.shahdarshil.playoff.network.datamodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by shah on 14 April, 2021.
 */
@Parcelize
data class User(
    @SerializedName("name")
    val username: String?,
    val password: String?,
    val email: String,
    @SerializedName("oneTimePassword")
    val otp: String?
) : Parcelable
