package com.shahdarshil.playoff

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

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