package com.shahdarshil.playoff

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
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

fun Long.toDate(): String {
    val dateFormatter = SimpleDateFormat("d-M-y H:m", Locale.US)
    val date = Date(this * 1000)

    return dateFormatter.format(date)
}

fun String.toUnixTimestamp(): Long {
    val formatter = SimpleDateFormat("d-M-y H:m", Locale.US)
    val date = try {
        formatter.parse(this)
    } catch(e: ParseException) {
        null
    }

    return date?.time?.div(1000) ?: 0
}