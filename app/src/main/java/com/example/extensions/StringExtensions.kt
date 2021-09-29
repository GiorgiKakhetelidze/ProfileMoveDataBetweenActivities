package com.example.extensions

fun String.checkEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()