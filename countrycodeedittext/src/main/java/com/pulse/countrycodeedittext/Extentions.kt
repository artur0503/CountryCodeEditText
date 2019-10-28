package com.pulse.countrycodeedittext

fun Any?.isNotNull() = this != null
fun String.appendStart(string: String) = string + this
fun String.removeWhitespaces() = this.replace("\\s".toRegex(), "")

