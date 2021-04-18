package com.shahdarshil.playoff.user

/**
 * Created by shah on 16 April, 2021.
 */
fun String.isEmail(): Boolean {
    val regex = Regex("""^[a-zA-Z0-9._-]+@[a-zA-Z]+\.[a-zA-z]+$""")
    return regex.matches(this)
}