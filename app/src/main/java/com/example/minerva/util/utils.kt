package com.example.minvera.util

fun isValidEmail(string: String): Boolean {
    var result = false
    if (string.isNotEmpty()) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches()) {
            result = true
        }
    }
    return result
}

fun isValidPassword(string: String): Boolean {
    var result = false
    if (string.isNotEmpty()) {
        if (string.length in 8..16) {
            result = true
        }
    }
    return result
}

fun isValidUserName(string: String): Boolean {
    var result = false

    if (string.isNotEmpty())
        if (string.onlyLetters())
            result = true

    return result
}

fun isValidMobileNumber(string: String): Boolean {
    var result = false

    if (string.length == 11)
        if (string.startsWith("010")
            || string.startsWith("011")
            || string.startsWith("012")
            || string.startsWith("015")
        )
            result = true

    return result
}


// extensions
fun String.onlyLetters() = all { it.isLetter() }