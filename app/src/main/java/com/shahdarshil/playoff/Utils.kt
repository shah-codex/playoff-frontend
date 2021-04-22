package com.shahdarshil.playoff

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by shah on 14 April, 2021.
 */
fun String.createHash(): String {
    val digest = try {
        MessageDigest.getInstance("SHA-256")
    } catch(e: NoSuchAlgorithmException) {
        null
    }

    return digest?.digest(this.toByteArray())?.joinToString("") { "%02x".format(it) } ?: ""
}

fun Int.toDate(): String {
    val dateFormatter = SimpleDateFormat("dd-mm-yyyy hh:mm", Locale.US)
    val date = Date(this.toLong() * 1000)

    return dateFormatter.format(date)
}